public class CollisionManager {

    public void verifyCollisions(long currentTime, Player player, EnemyController enemyController) {
        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();
        EnemyProjectileManager enemyProjectileManager = enemyController.getEnemyProjectileManager();
        CircleManager circleManager = enemyController.getCircleManager();
        DiamondManager diamondManager = enemyController.getDiamondManager();

        if(player.getState() == State.ACTIVE){
            /* colisões player */
            player.checkForCollisions(currentTime, enemyProjectileManager, circleManager, diamondManager);
        }

        /* colisões projeteis (player) - inimigos */

        for(int k = 0; k < playerProjectileManager.getStates().length; k++){
            circleManager.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);

            diamondManager.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
        }
    }

}
