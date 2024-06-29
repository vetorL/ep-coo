public class GarbageController {

    public void dump(PlayerProjectileManager playerProjectileManager,
                     EnemyController enemyController) {
        EnemyProjectileManager enemyProjectileManager = enemyController.getEnemyProjectileManager();
        CircleManager circleManager = enemyController.getCircleManager();
        DiamondManager diamondManager = enemyController.getDiamondManager();

        enemyProjectileManager.dump();
        playerProjectileManager.dump();
        diamondManager.dump();
        circleManager.dump();
    }

}
