public class Game {

    private Player player;
    private CircleManager circleManager;
    private DiamondManager diamondManager;
    private EnemyProjectileManager enemyProjectileManager;
    private long currentTime;
    private long delta;
    private boolean running = true;
    private CollisionManager collisionManager;
    private StateManager stateManager;
    private InputManager inputManager;
    private BackgroundManager backgroundManager;
    private GarbageController garbageController;

    public Game(Player player,
                CircleManager circleManager, DiamondManager diamondManager, EnemyProjectileManager enemyProjectileManager,
                long currentTime) {
        this.player = player;
        this.circleManager = circleManager;
        this.diamondManager = diamondManager;
        this.enemyProjectileManager = enemyProjectileManager;
        this.currentTime = currentTime;
        this.collisionManager = new CollisionManager();
        this.stateManager = new StateManager();
        this.inputManager = new InputManager();
        this.backgroundManager = new BackgroundManager();
        this.garbageController = new GarbageController();
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

        collisionManager.verifyCollisions(currentTime, player, circleManager, diamondManager, enemyProjectileManager);

        /***************************/
        /* Atualizações de estados */
        /***************************/

        stateManager.updateStates(currentTime, player, circleManager, diamondManager, enemyProjectileManager, delta);

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/

        this.running = inputManager.verifyInput(player, currentTime, delta);

        /*******************/
        /* Desenho da cena */
        /*******************/

        backgroundManager.drawScene(currentTime, delta, player,
                circleManager, diamondManager, enemyProjectileManager);

        /*********************************/
        /* Limpa entidades inutilizadas */
        /********************************/
        garbageController.dump(enemyProjectileManager, player.getProjectileManager(), diamondManager);

        /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

        Main.busyWait(currentTime + 5);
    }

}
