package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * ExtraPaddleStrategy represents a collision strategy for locating an extra paddle when
 * a brick collides with another game object.
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs an ExtraPaddleStrategy with the specified game manager and counter.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     */
    public ExtraPaddleStrategy(BrickerGameManager brickerGameManager, Counter bricksCounter) {
    super(brickerGameManager, bricksCounter);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects,
     * with additional functionality to locate an extra paddle.
     *
     * @param thisBrick The first game object involved in the collision.
     * @param other     The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisBrick, GameObject other) {
        super.onCollision(thisBrick, other);
        brickerGameManager.locateExtraPaddle();
    }
}
