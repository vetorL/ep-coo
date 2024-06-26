import java.util.ArrayList;
import java.util.List;

public class EnemyProjectileManager extends ProjectileManager {

    private double radius = 2.0;        // raio (tamanho dos projéteis inimigos)

    public EnemyProjectileManager() {
        int numberOfProjectiles = 200;

        List<Projectile> projectiles = new ArrayList<>();

        for(int i = 0; i < numberOfProjectiles; i++) {
            projectiles.add(new EnemyProjectile(radius));
        }

        super.setProjectiles(projectiles);
    }

    public double getRadius() {
        return radius;
    }

}
