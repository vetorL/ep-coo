public class CollisionManager {

    public void verifyCollisions(long currentTime, Player player,
                                 CircleManager circleManager, DiamondManager diamondManager,
                                 EnemyProjectileManager enemyProjectileManager) {
        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();

        if(player.getState() == State.ACTIVE){

            /* colisões player - projeteis (inimigo) */

            player.checkCollisionWithEnemyProjectile(currentTime, enemyProjectileManager);

            /* colisões player - inimigos */

            player.checkCollisionWithEnemy(currentTime, circleManager);
            player.checkCollisionWithEnemy(currentTime, diamondManager);
        }

        /* colisões projeteis (player) - inimigos */

        for(int k = 0; k < playerProjectileManager.getStates().length; k++){
            circleManager.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);

            diamondManager.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
        }
    }

}
