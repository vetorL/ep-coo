public class EnemyController {

    private final DiamondManager diamondManager;
    private final CircleManager circleManager;
    private final EnemyProjectileManager enemyProjectileManager;

    public EnemyController() {
        this.diamondManager = new DiamondManager();
        this.circleManager = new CircleManager();
        this.enemyProjectileManager = new EnemyProjectileManager();

        this.diamondManager.init();
        this.circleManager.init();
        this.enemyProjectileManager.init();
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

    public void draw(long currentTime) {
        /* desenhando projeteis (inimigos) */

        enemyProjectileManager.drawProjectiles();

        /* desenhando inimigos (tipo 1) */

        circleManager.draw(currentTime);

        /* desenhando inimigos (tipo 2) */

        diamondManager.draw(currentTime);
    }

    public void dump() {
        this.diamondManager.dump();
        this.circleManager.dump();
        this.enemyProjectileManager.dump();
    }
}
