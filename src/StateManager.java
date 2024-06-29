public class StateManager {

    public void updateStates(long currentTime, Player player,
                             EnemyController enemyController,
                             long delta) {
        EnemyProjectileManager enemyProjectileManager = enemyController.getEnemyProjectileManager();
        CircleManager circleManager = enemyController.getCircleManager();
        DiamondManager diamondManager = enemyController.getDiamondManager();

        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();

        /* projeteis (player) */

        playerProjectileManager.updatePosition(delta);

        /* enemies */
        enemyController.updateStates(currentTime, delta, player);

        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if(player.getState() == State.EXPLODING){

            if(currentTime > player.getExplosion_end()){

                player.setState(State.ACTIVE);
            }
        }
    }

}
