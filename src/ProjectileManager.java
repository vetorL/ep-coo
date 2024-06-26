import java.util.List;

public abstract class ProjectileManager {

    private List<Projectile> projectiles;

    public void init() {
        for (Projectile projectile : projectiles) {
            projectile.init();
        }
    }

    public void updatePosition(long delta) {
        for (Projectile projectile : projectiles) {
            projectile.updatePosition(delta);
        }
    }

    public void drawProjectiles() {
        for (Projectile projectile : projectiles) {
            projectile.drawProjectile();
        }
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
