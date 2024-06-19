public class CollisionManager {

    public void verifyCollisions(long currentTime, Player player,
                                 PlayerProjectileManager playerProjectileManager,
                                 Enemy1 enemy1, Enemy2 enemy2,
                                 EnemyProjectileManager enemyProjectileManager) {
        if(player.getState() == State.ACTIVE){

            /* colisões player - projeteis (inimigo) */

            player.checkCollisionWithEnemyProjectile(currentTime, enemyProjectileManager);

            /* colisões player - inimigos */

            enemy1.checkCollisionWithPlayer(currentTime, player);

            enemy2.checkCollisionWithPlayer(currentTime, player);
        }

        /* colisões projeteis (player) - inimigos */

        for(int k = 0; k < playerProjectileManager.getStates().length; k++){
            enemy1.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);

            enemy2.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
        }
    }

}
