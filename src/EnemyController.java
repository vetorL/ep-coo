public class EnemyController {

    private final DiamondManager diamondManager;
    private final CircleManager circleManager;
    private final EnemyProjectileManager enemyProjectileManager;
    private final SlasherMcDasherManager slasherMcDasherManager;

    public EnemyController() {
        this.diamondManager = new DiamondManager();
        this.circleManager = new CircleManager();
        this.enemyProjectileManager = new EnemyProjectileManager();
        this.slasherMcDasherManager = new SlasherMcDasherManager();

        this.diamondManager.init();
        this.circleManager.init();
        this.enemyProjectileManager.init();
        this.slasherMcDasherManager.init();
    }

    public DiamondManager getDiamondManager() {
        return diamondManager;
    }

    public CircleManager getCircleManager() {
        return circleManager;
    }

    public EnemyProjectileManager getEnemyProjectileManager() {
        return enemyProjectileManager;
    }

    public SlasherMcDasherManager getSlasherMcDasherManager() {
        return slasherMcDasherManager;
    }

    public void updateStates(long currentTime, long delta, Player player) {
        /* projeteis (inimigos) */

        enemyProjectileManager.updatePosition(delta);

        /* inimigos tipo 1 */

        circleManager.updatePosition(currentTime, enemyProjectileManager, delta, player);

        /* inimigos tipo 2 */

        diamondManager.updatePosition(currentTime, enemyProjectileManager, delta);

        /* inimigos tipo 3 */

        slasherMcDasherManager.updatePosition(currentTime, delta);

        /* verificando se novos inimigos (tipo 1) devem ser "lançados" */

        circleManager.tryLaunch(currentTime);

        /* verificando se novos inimigos (tipo 2) devem ser "lançados" */

        diamondManager.tryLaunch(currentTime);

        /* verificando se novos inimigos (tipo 3) devem ser "lançados" */

        slasherMcDasherManager.tryLaunch(currentTime);
    }

    public void draw(long currentTime) {
        /* desenhando projeteis (inimigos) */

        enemyProjectileManager.drawProjectiles();

        /* desenhando inimigos (tipo 1) */

        circleManager.draw(currentTime);

        /* desenhando inimigos (tipo 2) */

        diamondManager.draw(currentTime);

        /* desenhando inimigos (tipo 3) */

        slasherMcDasherManager.draw(currentTime);
    }

    public void dump() {
        this.diamondManager.dump();
        this.circleManager.dump();
        this.enemyProjectileManager.dump();
        this.slasherMcDasherManager.dump();
    }
}
