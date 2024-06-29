public class GarbageController {

    public void dump(EnemyProjectileManager enemyProjectileManager,
                     PlayerProjectileManager playerProjectileManager,
                     DiamondManager diamondManager) {
        enemyProjectileManager.dump();
        playerProjectileManager.dump();
        diamondManager.dump();
    }

}
