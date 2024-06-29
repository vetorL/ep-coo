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

        /* projeteis (inimigos) */

        enemyProjectileManager.updatePosition(delta);

        /* inimigos tipo 1 */

        circleManager.updatePosition(currentTime, enemyProjectileManager, delta, player);

        /* inimigos tipo 2 */

        diamondManager.updatePosition(currentTime, enemyProjectileManager, delta);

        /* verificando se novos inimigos (tipo 1) devem ser "lançados" */

        circleManager.tryLaunch(currentTime);

        /* verificando se novos inimigos (tipo 2) devem ser "lançados" */

        diamondManager.tryLaunch(currentTime);

        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if(player.getState() == State.EXPLODING){

            if(currentTime > player.getExplosion_end()){

                player.setState(State.ACTIVE);
            }
        }
    }

}
