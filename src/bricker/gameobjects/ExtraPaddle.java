package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * ExtraPaddle is a specialized type of paddle in the Bricker game.
 * It appears on the board according to certain game events.
 */
public class ExtraPaddle extends Paddle{
    private final Counter collisionCounter;

    /**
     * Construct a new ExtraPaddle instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param inputListener  Listener for user input.
     * @param rightLimit     The right limit for the paddle's movement.
     * @param collisionCounter     Counter for the number of hits the paddle can take.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, float rightLimit, Counter collisionCounter) {
        super(topLeftCorner, dimensions, renderable, inputListener, rightLimit);
        this.collisionCounter = collisionCounter;
    }

    /**
     * Called on the first frame of a collision.
     *
     * @param other      The GameObject with which a collision occurred.
     * @param collision  Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(BrickerGameManager.MAIN_BALL_TAG) ||
                other.getTag().equals(BrickerGameManager.PUCK_TAG)){
            collisionCounter.decrement();
        }
    }
}


