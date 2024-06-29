package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

/**
 * ExtraLifeStrategy represents a collision strategy for creating extra life when
 * a brick collides with another game object.
 */
public class ExtraLifeStrategy extends BasicCollisionStrategy {

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs an ExtraLifeStrategy with the specified game manager and counter.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     */
    public ExtraLifeStrategy(BrickerGameManager brickerGameManager, Counter bricksCounter) {
        super(brickerGameManager, bricksCounter);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects, with additional functionality to create an extra life.
     *
     * @param thisBrick The first game object involved in the collision.
     * @param other     The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisBrick, GameObject other) {
        super.onCollision(thisBrick, other);
        brickerGameManager.createExtraLife(thisBrick.getCenter());
    }
}

