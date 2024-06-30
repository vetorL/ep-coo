public class StateManager {

    public void updateStates(long currentTime, Player player,
                             EnemyController enemyController,
                             long delta) {
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
        } else if(player.getState() == State.DAMAGED){
            if(currentTime > player.getDamage_end()){

                player.setState(State.ACTIVE);
            }
        } else if(player.getState() == State.POWERED){
            if(currentTime > player.getPower_end()){

                player.setState(State.ACTIVE);
            }
        }
    }
}
