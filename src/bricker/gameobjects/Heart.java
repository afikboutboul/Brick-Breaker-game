package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Heart is a game object representing a heart item in the Bricker game.
 */
public class Heart extends GameObject {

    private boolean isHeartTaken;

    /**
     * Constructs a new Heart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * Determines if the heart should collide with another game object.
     * Hearts only collide with the main paddle.
     *
     * @param other The other GameObject involved in the collision.
     * @return true if the heart should collide with the other object, false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && other.getTag().equals(BrickerGameManager.MAIN_PADDLE_TAG);
    }

    /**
     * Handles collision events when the heart collides with another game object.
     * If the collision involves the main paddle, the heart is marked as "taken".
     *
     * @param other      The GameObject with which a collision occurred.
     * @param collision  Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        isHeartTaken = true;
    }

    /**
     * Checks if the heart has been taken by a player.
     *
     * @return true if the heart has been taken, false otherwise
     */
    public boolean getIsHeartTaken() {
        return isHeartTaken;
    }
}
