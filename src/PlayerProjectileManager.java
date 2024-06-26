import java.util.ArrayList;
import java.util.List;

public class PlayerProjectileManager extends ProjectileManager {
    public PlayerProjectileManager() {
        List<Projectile> projectiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            projectiles.add(new PlayerProjectile());
        }
        super.setProjectiles(projectiles);
    }

}
