package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

import java.util.Random;

/**
 * DoubleCollisionStrategies class represents a collision strategy
 * that combines multiple collision strategies
 * for bricks in the game. It extends the BasicCollisionStrategy class.
 */
public class DoubleCollisionStrategies extends BasicCollisionStrategy {

    private final Random random;
    private CollisionStrategy[] strategiesArray;
    private CollisionStrategyFactory collisionStrategyFactory;

    /**
     * Constructor for DoubleCollisionStrategies.
     *
     * @param brickerGameManager the game manager instance.
     * @param bricksCounter       the counter for the remaining bricks.
     */
    public DoubleCollisionStrategies(BrickerGameManager brickerGameManager, Counter bricksCounter) {
        super(brickerGameManager, bricksCounter);
        this.collisionStrategyFactory = new CollisionStrategyFactory();
        this.random = new Random();
        strategiesArray = createDuobleStrategyBrick(brickerGameManager, bricksCounter);
    }

    /**
     * Creates an array of collision strategies for a brick with a chance of having double strategies.
     *
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     * @return An array of collision strategies.
     */
    private CollisionStrategy[] createDuobleStrategyBrick(BrickerGameManager brickerGameManager,
                                                         Counter bricksCounter){
        int strategiesNum = 2;
        int[] results = {random.nextInt(5), random.nextInt(5), -1};
        if (results[0] == collisionStrategyFactory.DOUBLE_STRATEGIES ||
                results[1] == collisionStrategyFactory.DOUBLE_STRATEGIES){
            strategiesNum++;
        }
        for (int i = 0; i < strategiesNum; i++) {
            if (results[i] == collisionStrategyFactory.DOUBLE_STRATEGIES || results[i] < 0) {
                results[i] = random.nextInt(4);
            }
        }
        CollisionStrategy[] collisionStrategies = new CollisionStrategy[strategiesNum];
        for (int i = 0; i < collisionStrategies.length; i++) {
            collisionStrategies[i] = collisionStrategyFactory.chosenStrategy(results[i],
                    brickerGameManager, bricksCounter);
        }
        return collisionStrategies;
    }

    /**
     * Handles the collision event between this brick and another game object.
     *
     * @param thisBrick the current brick involved in the collision.
     * @param other     the other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisBrick, GameObject other) {
        for (CollisionStrategy strategy:strategiesArray){
            strategy.onCollision(thisBrick, other);
        }
    }
}
