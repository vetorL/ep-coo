public class CollisionManager {

    public void verifyCollisions(long currentTime, Player player, EnemyController enemyController) {
        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();

        if(player.getState() == State.ACTIVE){
            /* colisões player */
            player.checkForCollisions(currentTime, enemyController);
        }

        /* colisões projeteis (player) - inimigos */

        enemyController.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager);
    }

}
