package bricker.brick_strategies;

import danogl.GameObject;

/**
 * CollisionStrategy represents the strategy for handling collisions between game objects.
 */
public interface CollisionStrategy {

    /**
     * Handles the collision between two game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    public void onCollision(GameObject gameObject1, GameObject gameObject2);
}
