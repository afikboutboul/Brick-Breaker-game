package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * CameraChangeStrategy represents a collision strategy for changing the camera mode
 * when a brick collides with the main ball.
 */
public class CameraChangeStrategy extends BasicCollisionStrategy {
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a CameraChangeStrategy with the specified game manager and counter.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     */
    public CameraChangeStrategy(BrickerGameManager brickerGameManager, Counter bricksCounter) {
        super(brickerGameManager, bricksCounter);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between a brick and another game object,
     * with additional functionality to change the camera mode.
     *
     * @param thisBrick The brick involved in the collision.
     * @param other     The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisBrick, GameObject other) {
        super.onCollision(thisBrick, other);
        if (other.getTag().equals(BrickerGameManager.MAIN_BALL_TAG)){
            brickerGameManager.changeCameraMode();
        }
    }
}
