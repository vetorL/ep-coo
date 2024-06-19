import java.awt.*;

public class Game {

    private Player player;
    private PlayerProjectileManager playerProjectileManager;
    private Enemy1 enemy1;
    private Enemy2 enemy2;
    private EnemyProjectileManager enemyProjectileManager;
    private Background firstBackground;
    private Background secondBackground;
    private long currentTime;
    private long delta;
    private boolean running = true;
    private CollisionManager collisionManager;

    public Game(Player player, PlayerProjectileManager playerProjectileManager,
                Enemy1 enemy1, Enemy2 enemy2, EnemyProjectileManager enemyProjectileManager,
                Background firstBackground, Background secondBackground, long currentTime) {
        this.player = player;
        this.playerProjectileManager = playerProjectileManager;
        this.enemy1 = enemy1;
        this.enemy2 = enemy2;
        this.enemyProjectileManager = enemyProjectileManager;
        this.firstBackground = firstBackground;
        this.secondBackground = secondBackground;
        this.currentTime = currentTime;
        this.collisionManager = new CollisionManager();
    }

    public boolean isRunning() {
        return running;
    }

    public void on() {
        /* Usada para atualizar o estado dos elementos do jogo    */
        /* (player, projéteis e inimigos) "delta" indica quantos  */
        /* ms se passaram desde a última atualização.             */

        delta = System.currentTimeMillis() - currentTime;

        /* Já a variável "currentTime" nos dá o timestamp atual.  */

        currentTime = System.currentTimeMillis();

        /***************************/
        /* Verificação de colisões */
        /***************************/

        collisionManager.verifyCollisions(currentTime, player,
                playerProjectileManager, enemy1, enemy2, enemyProjectileManager);

        /***************************/
        /* Atualizações de estados */
        /***************************/

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

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/

        if(player.getState() == State.ACTIVE){

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
                player.fire(currentTime, playerProjectileManager);
            }
        }

        if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

        /* Verificando se coordenadas do player ainda estão dentro	*/
        /* da tela de jogo após processar entrada do usuário.       */

        player.isWithinBounds();

        /*******************/
        /* Desenho da cena */
        /*******************/

        /* desenhando plano fundo distante */

        GameLib.setColor(Color.DARK_GRAY);
        secondBackground.draw(delta, 2);

        /* desenhando plano de fundo próximo */

        GameLib.setColor(Color.GRAY);
        firstBackground.draw(delta, 3);

        /* desenhando player */

        player.draw(currentTime);


        /* deenhando projeteis (player) */

        playerProjectileManager.drawProjectiles();

        /* desenhando projeteis (inimigos) */

        enemyProjectileManager.drawProjectiles();

        /* desenhando inimigos (tipo 1) */

        enemy1.draw(currentTime);

        /* desenhando inimigos (tipo 2) */

        enemy2.draw(currentTime);

        /* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */

        GameLib.display();

        /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

        Main.busyWait(currentTime + 5);
    }

}
