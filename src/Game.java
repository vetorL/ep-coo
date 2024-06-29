public class Game {

    private Player player;
    private long currentTime;
    private long delta;
    private boolean running = true;
    private CollisionManager collisionManager;
    private StateManager stateManager;
    private InputManager inputManager;
    private BackgroundManager backgroundManager;
    private GarbageController garbageController;
    private final EnemyController enemyController;

    public Game(Player player, long currentTime) {
        this.player = player;
        this.currentTime = currentTime;
        this.enemyController = new EnemyController();
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

        collisionManager.verifyCollisions(currentTime, player, enemyController);

        /***************************/
        /* Atualizações de estados */
        /***************************/

        stateManager.updateStates(currentTime, player, enemyController, delta);

        /********************************************/
        /* Verificando entrada do usuário (teclado) */
        /********************************************/

        this.running = inputManager.verifyInput(player, currentTime, delta);

        /*******************/
        /* Desenho da cena */
        /*******************/

        backgroundManager.drawScene(currentTime, delta, player, enemyController);

        /*********************************/
        /* Limpa entidades inutilizadas */
        /********************************/
        garbageController.dump(player.getProjectileManager(), enemyController);

        /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */

        Main.busyWait(currentTime + 5);
    }

}
