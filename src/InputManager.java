public class InputManager {

    public boolean verifyInput(Player player, long currentTime, long delta) {
        if(player.getState() == State.ACTIVE || player.getState() == State.POWERED){

            if(GameLib.iskeyPressed(GameLib.KEY_UP)) {
                player.moveUp(delta);
            }
            if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
                player.moveDown(delta);
            }
            if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
                player.moveLeft(delta);
            }
            if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
                player.moveRight(delta);
            }
            if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                player.fire(currentTime);
            }
        }

        if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) return false;

        /* Verificando se coordenadas do player ainda estão dentro	*/
        /* da tela de jogo após processar entrada do usuário.       */

        player.isWithinBounds();

        return true;
    }

}
