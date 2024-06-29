public class GarbageController {

    public void dump(EnemyProjectileManager enemyProjectileManager,
                     PlayerProjectileManager playerProjectileManager,
                     DiamondManager diamondManager, CircleManager circleManager) {
        enemyProjectileManager.dump();
        playerProjectileManager.dump();
        diamondManager.dump();
        circleManager.dump();
    }

}
