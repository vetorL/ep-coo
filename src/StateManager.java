public class StateManager {

    public void updateStates(long currentTime, Player player,
                             PlayerProjectileManager playerProjectileManager,
                             Enemy1 enemy1, Enemy2 enemy2,
                             EnemyProjectileManager enemyProjectileManager,
                             long delta) {
        /* projeteis (player) */

        playerProjectileManager.updatePosition(delta);

        /* projeteis (inimigos) */

        enemyProjectileManager.updatePosition(delta);

        /* inimigos tipo 1 */

        enemy1.updatePosition(currentTime, enemyProjectileManager, delta, player);

        /* inimigos tipo 2 */

        enemy2.updatePosition(currentTime, enemyProjectileManager, delta);

        /* verificando se novos inimigos (tipo 1) devem ser "lançados" */

        enemy1.tryLaunch(currentTime);

        /* verificando se novos inimigos (tipo 2) devem ser "lançados" */

        enemy2.tryLaunch(currentTime);

        /* Verificando se a explosão do player já acabou.         */
        /* Ao final da explosão, o player volta a ser controlável */
        if(player.getState() == State.EXPLODING){

            if(currentTime > player.getExplosion_end()){

                player.setState(State.ACTIVE);
            }
        }
    }

}
