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
}
