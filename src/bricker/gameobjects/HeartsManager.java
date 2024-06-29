package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * HeartsManager manages the hearts in the Bricker game.
 * It handles the initialization, display, and interaction of hearts.
 */
public class HeartsManager {

    /**
     * The file path for the heart image.
     */
    public static final String HEART_PATH = "assets/heart.png";

    /**
     * The speed of the heart object.
     */
    public static final int HEART_SPEED = 100;

    /**
     * The width of the heart object.
     */
    public static final float HEART_WIDTH = 25;



    private final int maxHeartsNum;
    private int heartsNum;
    private Heart[] heartsArray;
    private final BrickerGameManager gameManager;
    private GameObject heartsNumericDisplay;
    private TextRenderable numericRenderer;
    private Vector2 topLeftCorner;
    private Vector2 defaultDimensions;
    private ImageRenderable heartGrapic;

    /**
     * Constructs a HeartsManager with the specified initial and maximum number of hearts.
     *
     * @param initHeartsNum The initial number of hearts.
     * @param maxHeartsNum  The maximum number of hearts.
     * @param gameManager   The game manager instance.
     */
    public HeartsManager(int initHeartsNum, int maxHeartsNum, BrickerGameManager gameManager) {
        this.maxHeartsNum = maxHeartsNum;
        this.heartsNum = initHeartsNum;
        heartsArray = new Heart[maxHeartsNum];
        this.gameManager = gameManager;
    }

    /**
     * Initializes the hearts and their display.
     *
     * @param topLeftCorner The top-left corner position for the hearts display.
     * @param imageReader   The image reader for loading heart graphics.
     */
    public void initializedHearts(Vector2 topLeftCorner, ImageReader imageReader){
        this.topLeftCorner = topLeftCorner;
        defaultDimensions = new Vector2(HEART_WIDTH, HEART_WIDTH);
        initializeNumeric(imageReader);
        initializeGraphics(imageReader);
    }

    /**
     * Initializes the graphics for displaying hearts.
     *
     * @param imageReader The image reader for loading heart graphics.
     */
    private void initializeGraphics(ImageReader imageReader) {
        heartGrapic = imageReader.readImage(HEART_PATH, true);
        for (int i = 0; i < maxHeartsNum; i++) {
            Vector2 heartTopLeftCorner = new Vector2(topLeftCorner.x()+(i+1)*(HEART_WIDTH+3),
                    topLeftCorner.y());
            heartsArray[i] = new Heart(heartTopLeftCorner,defaultDimensions, heartGrapic);
        }
        for (int i = 0; i < heartsNum; i++) {
            gameManager.addObject(heartsArray[i], Layer.UI);
        }
    }

    /**
     * Initializes the numeric display for showing the number of hearts.
     *
     * @param imageReader The image reader for loading numeric graphics.
     */
    private void initializeNumeric(ImageReader imageReader){
        numericRenderer = new TextRenderable("", null, false, true);
        heartsNumericDisplay = new GameObject(topLeftCorner, defaultDimensions, numericRenderer);
        setHeartsNumColor();
        gameManager.addObject(heartsNumericDisplay, Layer.UI);
    }

    /**
     * Sets the color of the numeric display based on the number of hearts.
     */
    private void setHeartsNumColor() {
        switch (heartsNum){
            case 1:
                numericRenderer.setColor(Color.red);
                break;
            case 2:
                numericRenderer.setColor(Color.yellow);
                break;
            default:
                numericRenderer.setColor(Color.green);
        }
        numericRenderer.setString(String.valueOf(heartsNum));
    }

    /**
     * Decreases the number of hearts and updates the display.
     */
    public void decreaseHearts() {
        heartsNum--;
        gameManager.removeObject(heartsArray[heartsNum], Layer.UI);
        setHeartsNumColor();
    }

    /**
     * Checks if there are more hearts available.
     *
     * @return True if there are more hearts, otherwise false.
     */
    public boolean hasMoreHearts() {
        return heartsNum != 0;
    }

    /**
     * Creates a dropping heart at the specified location.
     *
     * @param extraLifeLocation The location to create the dropping heart.
     */
    public void heartDroping(Vector2 extraLifeLocation) {
        Heart heart = new Heart(Vector2.ZERO, defaultDimensions, heartGrapic);
        heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        heart.setCenter(extraLifeLocation);
        heart.setTag(BrickerGameManager.DROP_HEART_TAG);
        gameManager.addObject(heart, Layer.DEFAULT);
    }

    /**
     * Handles the event of catching a heart and increases the hearts count.
     *
     * @param heart The heart that was caught.
     */
    public void handleCatchHeart(Heart heart) {
        gameManager.removeObject(heart, Layer.DEFAULT);
        increaseHearts();
    }

    /**
     * Increases the number of hearts and updates the display.
     */
    private void increaseHearts() {
        if (heartsNum < maxHeartsNum){
            heartsNum++;
            gameManager.addObject(heartsArray[heartsNum-1], Layer.UI);
            setHeartsNumColor();
        }
    }
}
