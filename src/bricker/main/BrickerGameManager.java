package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * BrickerGameManager is the main class responsible for managing the Bricker game.
 * It initializes game objects, handles user input, and controls the game state.
 */
public class BrickerGameManager extends GameManager {

    /**
     * The file path for the paddle image.
     */
    public static final String PADDLE_PATH = "assets/paddle.png";

    /**
     * The file path for the ball image.
     */
    public static final String BALL_PATH = "assets/ball.png";

    /**
     * The file path for the puck image.
     */
    public static final String PUCK_PATH = "assets/mockBall.png";

    /**
     * The file path for the background image.
     */
    public static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * The file path for the sound of ball collision.
     */
    public static final String BLOP_PATH = "assets/blop.wav";

    /**
     * The file path for the brick image.
     */
    public static final String BRICK_PATH = "assets/brick.png";

    /**
     * The tag for identifying puck objects.
     */
    public static final String PUCK_TAG = "puck";

    /**
     * The tag for identifying the main paddle object.
     */
    public static final String MAIN_PADDLE_TAG = "mainPuddle";

    /**
     * The tag for identifying dropping heart objects.
     */
    public static final String DROP_HEART_TAG = "droppingHeart";

    /**
     * The tag for identifying the main ball object.
     */
    public static final String MAIN_BALL_TAG = "mainBall";

    /**
     * The tag for identifying wall objects.
     */
    public static final String WALL_TAG = "wall";
    /**
     * The name of the game window.
     */
    private static final String WINDOW_NAME = "bricker";

    /**
     * The height of a brick.
     */
    private static final float BRICK_HEIGHT = 15;

    /**
     * The width of a wall.
     */
    private static final float WALL_WIDTH = 10;

    /**
     * The width of the paddle.
     */
    private static final float PADDLE_WIDTH = 100;

    /**
     * The height of the paddle.
     */
    private static final float PADDLE_HEIGHT = 15;

    /**
     * The Y-coordinate of the paddle.
     */
    private static final float PADDLE_Y = 50;

    /**
     * The height of the game window.
     */
    private static final float WINDOW_HEIGHT = 700;

    /**
     * The width of the game window.
     */
    private static final float WINDOW_WIDTH = 800;

    /**
     * The default maximum number of hearts.
     */
    private static final int DEAFAULT_MAX_HEARTS = 4;

    /**
     * The default initial number of hearts.
     */
    private static final int DEAFAULT_INIT_HEARTS = 3;

    /**
     * The speed of the ball.
     */
    private static final float BALL_SPEED = 200;

    /**
     * The radius of the ball.
     */
    private static final int BALL_RADIUS = 20;

    /**
     * The number of new pucks created.
     */
    private static final int NUM_OF_NEW_PUCKS = 2;

    /**
     * The number of collisions needed for extra paddle mode.
     */
    private static final int EXTRA_PADDLE_MODE_COLLISIONS = 4;

    /**
     * The number of collisions needed for camera mode.
     */
    private static final int CAMERA_MODE_COLLISIONS = 5;

    /**
     * The default number of bricks in a row.
     */
    private static final int DEAFAULT_BRICKS_IN_ROW = 7;

    /**
     * The default number of bricks in a column.
     */
    private static final int DEAFAULT_BRICKS_IN_COL = 8;

    /**
     * Losing massage
     */
    private static final String LOSE_PROMPT = "You lose! Play again?";

    /**
     * Wining massage.
     */
    private static final String WIN_PROMPT = "You win! Play again?";

    //general
    private Counter extraPaddleHitCounter;
    private int turnOffCameraBallCounterVal = 0;
    private final Vector2 windowDimensions;
    private int rowsNumOfBricks = DEAFAULT_BRICKS_IN_ROW;
    private int colsNumsOfBricks = DEAFAULT_BRICKS_IN_COL;
    private Random random;
    //game manager tools
    private WindowController windowController;
    private HeartsManager heartsManager;
    private Counter bricksCounter;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    //game objects
    private Ball ball;
    private Paddle mainPaddle;
    private ExtraPaddle extraPaddle;

    /**
     * Constructs a new BrickerGameManager with the specified window title, dimensions, and number of bricks.
     *
     * @param windowTitle the title of the game window
     * @param windowDimensions the dimensions of the game window
     * @param rowsNumsBricks the number of rows of bricks
     * @param colsNumBricks the number of columns of bricks
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int rowsNumsBricks, int colsNumBricks) {
        this(windowTitle, windowDimensions);
        this.rowsNumOfBricks = rowsNumsBricks;
        this.colsNumsOfBricks = colsNumBricks;
    }

    /**
     * Constructs a new BrickerGameManager with the specified window title and dimensions.
     *
     * @param windowTitle the title of the game window
     * @param windowDimensions the dimensions of the game window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
        random = new Random();
    }

    /**
     * Initializes the game with the provided readers and controllers.
     *
     * @param imageReader the image reader for loading images
     * @param soundReader the sound reader for loading sounds
     * @param inputListener the listener for user input
     * @param windowController the controller for managing the game window
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        heartsManager = new HeartsManager(DEAFAULT_INIT_HEARTS, DEAFAULT_MAX_HEARTS, this);
        Vector2 heartDisplayTopLeftCorner = new Vector2(WALL_WIDTH,
                windowDimensions.y()-(heartsManager.HEART_WIDTH+WALL_WIDTH));
        heartsManager.initializedHearts(heartDisplayTopLeftCorner, imageReader);
        initializeScreen();
        createBricks();
        createBall();
        createPaddles();
    }

    /**
     * Creates and initializes the main and extra paddles.
     */
    private void createPaddles() {
        extraPaddleHitCounter = new Counter();
        Renderable paddleImage = imageReader.readImage(PADDLE_PATH, false);
        mainPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
                inputListener, windowDimensions.x());
        extraPaddle = new ExtraPaddle(new Vector2(-1,-1), new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions.x(), extraPaddleHitCounter);
        mainPaddle.setCenter(new Vector2(windowDimensions.x()*0.5f, (int)windowDimensions.y()-PADDLE_Y));
        mainPaddle.setTag(MAIN_PADDLE_TAG);
        gameObjects().addGameObject(mainPaddle);
    }

    /**
     * Creates and initializes the ball.
     */
    private void createBall() {
        Renderable ballImage = imageReader.readImage(BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(BLOP_PATH);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        ball.setTag(MAIN_BALL_TAG);
        setBallVelocityAndCenter();
        gameObjects().addGameObject(ball);
    }

    /**
     * Sets the ball's velocity and centers it in the window.
     */
    private void setBallVelocityAndCenter() {
        float ballVelX = BALL_SPEED;
        float ballVeY = BALL_SPEED;
        if(random.nextBoolean()) {
            ballVelX *= -1;
        }
        if(random.nextBoolean()) {
            ballVeY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVeY));
        ball.setCenter(windowDimensions.mult(0.5f));
    }


    /**
     * Initializes the screen by creating walls and background.
     */
    private void initializeScreen(){
        //creating invisible walls
        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(WALL_WIDTH, windowDimensions.y()), null);
        GameObject rightWall = new GameObject(new Vector2((int)windowDimensions.x()-WALL_WIDTH, 0),
                new Vector2(WALL_WIDTH, windowDimensions.y()), null);
        GameObject upperWall = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), WALL_WIDTH), null);
        gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(upperWall, Layer.STATIC_OBJECTS);
        leftWall.setTag(WALL_TAG);
        rightWall.setTag(WALL_TAG);
        upperWall.setTag(WALL_TAG);

        //creating background
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Creates and initializes the bricks.
     */
    private void createBricks(){
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        bricksCounter = new Counter(rowsNumOfBricks*colsNumsOfBricks);
        Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
        float brickWidth = (windowDimensions.x()-2*WALL_WIDTH)/ colsNumsOfBricks;
        Vector2 brickDim = new Vector2(brickWidth-0.2f, BRICK_HEIGHT);
        for (int i = 0; i < rowsNumOfBricks; i++) {
            for (int j = 0; j < colsNumsOfBricks; j++) {
                CollisionStrategy collisionStrategy =
                        collisionStrategyFactory.chosenStrategy(random.nextInt(10),this, bricksCounter);
                Brick brick = new Brick(Vector2.ZERO, brickDim, brickImage, collisionStrategy);
                brick.setTopLeftCorner(new Vector2(WALL_WIDTH+j*brickWidth, WALL_WIDTH+i*(BRICK_HEIGHT+2)));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Removes a game object from the specified layer.
     *
     * @param gameObject the game object to remove
     * @param layer the layer from which to remove the game object
     * @return true if the object was successfully removed, false otherwise
     */
    public boolean removeObject(GameObject gameObject, int layer) {
        return gameObjects().removeGameObject(gameObject, layer);
    }

    /**
     * Adds a game object to the specified layer.
     *
     * @param gameObject the game object to add
     * @param layer the layer to which to add the game object
     */
    public void addObject(GameObject gameObject, int layer){
        gameObjects().addGameObject(gameObject, layer);
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkIfGameOver();
        clearObjects();
        upadateExtraPaddle();
        updateLife();
        updateCameraMode();
    }

    /**
     * Updates the player's lives based on game events.
     */
    private void updateLife() {
        for (GameObject gameObject:gameObjects()){
            if (gameObject.getTag().equals(DROP_HEART_TAG) && ((Heart) gameObject).getIsHeartTaken()){
                heartsManager.handleCatchHeart((Heart) gameObject);
            }
        }
    }

    /**
     * Updates the camera mode based on game events.
     */
    private void updateCameraMode() {
        if (ball.getCollisionCounter() == turnOffCameraBallCounterVal){
            setCamera(null);
        }
    }


    /**
     * Updates the position of the extra paddle based on game events.
     */
    private void upadateExtraPaddle() {
        if(extraPaddleHitCounter.value()==0){
            gameObjects().removeGameObject(extraPaddle);
        }
    }

    /**
     * Clears objects that are no longer needed.
     */
    private void clearObjects() {
        for (GameObject object:gameObjects()){
            if (object != null && object.getTopLeftCorner().y() > windowDimensions.y()) {
                gameObjects().removeGameObject(object);
            }
        }
    }


    /**
     * Checks if the game is over and handles the end game state.
     */
    private void checkIfGameOver() {
        if (bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            gameOver(true);
        }
        double ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            heartsManager.decreaseHearts();
            if (heartsManager.hasMoreHearts()) {
                setBallVelocityAndCenter();
            }
            else {
                gameOver(false);
            }
        }
    }

    /**
     * Ends the game and prompts the player to play again.
     *
     * @param isVictory true if the player won, false if he lost
     */
    private void gameOver(boolean isVictory) {
        String prompt = LOSE_PROMPT;
        if (isVictory)
            prompt = WIN_PROMPT;
        if(windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        }
        else
            windowController.closeWindow();
    }

    /**
     * Creates multiple puck balls at the specified location.
     *
     * @param puckCenterLocation the center location for the new puck balls
     */
    public void createPuckBalls(Vector2 puckCenterLocation){
        Renderable puckImage = imageReader.readImage(PUCK_PATH, true);
        Sound collisionSound = soundReader.readSound(BLOP_PATH);
        Vector2 puckDimensions = new Vector2(BALL_RADIUS*0.75f, BALL_RADIUS*0.75f);
        for (int i = 0; i < NUM_OF_NEW_PUCKS; i++) {
            Ball puck = new Ball(Vector2.ZERO, puckDimensions, puckImage, collisionSound);
            puck.setVelocity(randomizePuckVelocity());
            puck.setCenter(puckCenterLocation);
            puck.setTag(PUCK_TAG);
            gameObjects().addGameObject(puck);
        }
    }

    /**
     * Generates a random velocity for a puck ball.
     *
     * @return a vector representing the random velocity
     */
    private Vector2 randomizePuckVelocity() {
        double angle = random.nextDouble() * Math.PI;
        float ballVelX = (float) Math.cos(angle) * BALL_SPEED;
        float ballVeY = (float) Math.sin(angle) * BALL_SPEED;
        return new Vector2(ballVelX, ballVeY);
    }

    /**
     * Locates and initializes the extra paddle.
     */
    public void locateExtraPaddle(){
        if (extraPaddleHitCounter.value()<=0){
            extraPaddle.setCenter(windowDimensions.mult(0.5f));
            gameObjects().addGameObject(extraPaddle);
            extraPaddleHitCounter.increaseBy(EXTRA_PADDLE_MODE_COLLISIONS);
        }
    }

    /**
     * Changes the camera mode to follow the ball.
     */
    public void changeCameraMode() {
        if (camera() != null) {
            return;
        }
        setCamera(new Camera(ball, Vector2.ZERO, windowDimensions.mult(1.2f), windowDimensions));
        turnOffCameraBallCounterVal = ball.getCollisionCounter() + CAMERA_MODE_COLLISIONS;
    }

    /**
     * Creates an extra life at the specified location.
     *
     * @param extraLifeLocation the location to create the extra life
     */
    public void createExtraLife(Vector2 extraLifeLocation) {
        heartsManager.heartDroping(extraLifeLocation);
    }

    /**
     * The main method to run the game.
     *
     * @param args command-line arguments for the game
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager;
        if (args.length ==2) {
            brickerGameManager = new BrickerGameManager(WINDOW_NAME, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT),
                    Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        else{
            brickerGameManager = new BrickerGameManager(WINDOW_NAME,
                    new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
        }
        brickerGameManager.run();
    }
}
