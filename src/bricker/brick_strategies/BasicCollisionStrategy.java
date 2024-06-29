package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * BasicCollisionStrategy represents a basic collision strategy
 * for handling collisions between bricks and other game objects.
 */
public class BasicCollisionStrategy implements CollisionStrategy{

    private final BrickerGameManager brickerGameManager;
    private final Counter briksCounter;

    /**
     * Constructs a BasicCollisionStrategy with the specified game manager and counter.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager, Counter bricksCounter){
        this.brickerGameManager = brickerGameManager;
        this.briksCounter = bricksCounter;
    }

    /**
     * Handles the collision between a brick and another game object.
     *
     * @param thisBrick The brick involved in the collision.
     * @param other     The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisBrick, GameObject other) {
        if (brickerGameManager.removeObject(thisBrick, Layer.STATIC_OBJECTS)){
            briksCounter.decrement();
        }
    }
}
