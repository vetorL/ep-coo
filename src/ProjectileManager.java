import java.util.ArrayList;
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

    public State[] getStates() {
        State [] states = new State[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            states[i] = projectiles.get(i).getState();
        }
        return states;
    }

    public void setStates(State[] states) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).setState(states[i]);
        }
    }

    public double[] getY() {
        double [] y = new double[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            y[i] = projectiles.get(i).getY();
        }
        return y;
    }

    public void setY(double[] y) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).setY(y[i]);
        }
    }

    public double[] getX() {
        double [] x = new double[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            x[i] = projectiles.get(i).getX();
        }
        return x;
    }

    public void setX(double[] x) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).setX(x[i]);
        }
    }

    public double[] getVX() {
        double [] vx = new double[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            vx[i] = projectiles.get(i).getVX();
        }
        return vx;
    }

    public void setVX(double[] vx) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).setVX(vx[i]);
        }
    }

    public double[] getVY() {
        double [] vy = new double[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            vy[i] = projectiles.get(i).getVY();
        }
        return vy;
    }

    public void setVY(double[] vy) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).setVY(vy[i]);
        }
    }
}
