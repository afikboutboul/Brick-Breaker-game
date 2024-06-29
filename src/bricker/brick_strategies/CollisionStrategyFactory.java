package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.util.Counter;

/**
 * CollisionStrategyFactory is responsible for creating collision strategies for bricks in the Bricker game.
 */
public class CollisionStrategyFactory {

    /**
     * Represents the strategy for adding an extra ball.
     */
    public static final int EXTRA_BALL_STRATEGY = 0;

    /**
     * Represents the strategy for adding an extra paddle.
     */
    public static final int EXTRA_PADDLE_STRATEGY = 1;

    /**
     * Represents the strategy for changing the camera mode.
     */
    public static final int CAMERA_CHANGE_STRATEGY = 2;

    /**
     * Represents the strategy for adding an extra life.
     */
    public static final int EXTRA_LIFE_STRATEGY = 3;

    /**
     * Represents the strategy for combining multiple strategies.
     */
    public static final int DOUBLE_STRATEGIES = 4;


    /**
     * Constructs a CollisionStrategyFactory object.
     */
    public CollisionStrategyFactory() {
    }


    /**
     * Chooses a specific collision strategy based on a given result.
     *
     * @param strategyConstant   constant that represent the desire strategy.
     * @param brickerGameManager The game manager responsible for managing the game.
     * @param bricksCounter      The counter for tracking the number of bricks.
     * @return A collision strategy.
     */
    public CollisionStrategy chosenStrategy(int strategyConstant, BrickerGameManager brickerGameManager,
                                             Counter bricksCounter) {
        if (strategyConstant == DOUBLE_STRATEGIES) {
            return new DoubleCollisionStrategies(brickerGameManager, bricksCounter);
        }
        if (strategyConstant == EXTRA_LIFE_STRATEGY) {
            return new ExtraLifeStrategy(brickerGameManager, bricksCounter);
        }
        if (strategyConstant == CAMERA_CHANGE_STRATEGY) {
            return new CameraChangeStrategy(brickerGameManager, bricksCounter);
        }
        if (strategyConstant == EXTRA_PADDLE_STRATEGY) {
            return new ExtraPaddleStrategy(brickerGameManager, bricksCounter);
        }
        if (strategyConstant == EXTRA_BALL_STRATEGY) {
            return new ExtraBallStrategy(brickerGameManager, bricksCounter);
        }
        return new BasicCollisionStrategy(brickerGameManager, bricksCounter);
    }
}
