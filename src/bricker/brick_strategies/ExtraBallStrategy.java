package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * ExtraBallStrategy represents a collision strategy for creating extra balls when
 * a brick collides with another game object.
 */
public class ExtraBallStrategy extends BasicCollisionStrategy{

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs an ExtraBallStrategy with the specified game manager and counter.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     */
    public ExtraBallStrategy(BrickerGameManager brickerGameManager, Counter bricksCounter) {
        super(brickerGameManager, bricksCounter);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects, with additional functionality to create extra balls.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        super.onCollision(gameObject1, gameObject2);
        brickerGameManager.createPuckBalls(gameObject1.getCenter());
    }
}
