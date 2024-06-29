package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Ball is a game object that represents a bouncing ball in the Bricker game.
 * It reacts to collisions by changing its velocity and playing a collision sound.
 */
public class Ball extends GameObject {

    private final Sound collisionSound;
    private int collisionCounter = 0;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner  Position of the ball, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the ball. Can be null, in which case
     *                       the ball will not be rendered.
     * @param collisionSound The sound to play upon collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Called when the ball collides with another game object.
     * Flips the ball's velocity based on the collision normal and plays a sound.
     *
     * @param other      The other game object involved in the collision.
     * @param collision  Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
        collisionCounter++;
    }

    /**
     * Gets the collision counter.
     *
     * @return The counter tracking the number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
