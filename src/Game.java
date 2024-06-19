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
    private StateManager stateManager;
    private InputManager inputManager;
    private BackgroundManager backgroundManager;

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
        this.stateManager = new StateManager();
        this.inputManager = new InputManager();
        this.backgroundManager = new BackgroundManager();
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

        stateManager.updateStates(currentTime, player,
                playerProjectileManager, enemy1, enemy2, enemyProjectileManager, delta);

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/

        this.running = inputManager.verifyInput(player, currentTime, delta, playerProjectileManager);

        /*******************/
        /* Desenho da cena */
        /*******************/

        backgroundManager.drawScene(currentTime, delta, player, playerProjectileManager,
                enemy1, enemy2, enemyProjectileManager, firstBackground, secondBackground);

        /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

        Main.busyWait(currentTime + 5);
    }

}
