import java.awt.*;
import java.util.ArrayList;

public abstract class EnemyManager {

    private long nextEnemy;					// instante em que um novo inimigo deve aparecer

    private ArrayList<Enemy> enemies;

    public EnemyManager(long nextEnemy) {
        this.nextEnemy = nextEnemy;
    }

    public void init() {
        for (Enemy enemy : enemies) {
            enemy.init();
        }
    };

    public abstract void tryLaunch(long currentTime);

    public void draw(long currentTime) {
        for (Enemy enemy : enemies) {
            enemy.draw(currentTime);
        }
    };

    public void checkCollisionWithPlayerProjectile(long currentTime,
                                                   PlayerProjectileManager playerProjectileManager,
                                                   int k) {
        for(Enemy enemy : enemies) {
            enemy.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
        }
    }

    public void explode(long currentTime, int i) {
        enemies.get(i).explode(currentTime);
    }

    public State[] getStates() {
        State[] states = new State[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            states[i] = enemies.get(i).getState();
        }
        return states;
    }

    public void setStates(State[] states) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setState(states[i]);
        }
    }

    public double[] getX() {
        double[] X = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            X[i] = enemies.get(i).getX();
        }
        return X;
    }

    public void setX(double[] x) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setX(x[i]);
        }
    }

    public double[] getY() {
        double [] Y = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            Y[i] = enemies.get(i).getY();
        }
        return Y;
    }

    public void setY(double[] y) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setY(y[i]);
        }
    }

    public double[] getV() {
        double[] V = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            V[i] = enemies.get(i).getV();
        }
        return V;
    }

    public void setV(double[] v) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setV(v[i]);
        }
    }

    public double[] getAngle() {
        double[] angle = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            angle[i] = enemies.get(i).getAngle();
        }
        return angle;
    }

    public void setAngle(double[] angle) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setAngle(angle[i]);
        }
    }

    public double[] getRV() {
        double[] RV = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            RV[i] = enemies.get(i).getRV();
        }
        return RV;
    }

    public void setRV(double[] RV) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setRV(RV[i]);
        }
    }

    public double[] getExplosion_start() {
        double [] explosion_start = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            explosion_start[i] = enemies.get(i).getExplosion_start();
        }
        return explosion_start;
    }

    public void setExplosion_start(double[] explosion_start) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setExplosion_start(explosion_start[i]);
        }
    }

    public double[] getExplosion_end() {
        double [] explosion_end = new double[enemies.size()];
        for(int i = 0; i < enemies.size(); i++) {
            explosion_end[i] = enemies.get(i).getExplosion_end();
        }
        return explosion_end;
    }

    public void setExplosion_end(double[] explosion_end) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).setExplosion_end(explosion_end[i]);
        }
    }

    public double getRadius() {
        return enemies.get(0).getRadius();
    }

    public void setRadius(double radius) {
        for (Enemy enemy : enemies) {
            enemy.setRadius(radius);
        }
    }

    public long getNextEnemy() {
        return nextEnemy;
    }

    public void setNextEnemy(long nextEnemy) {
        this.nextEnemy = nextEnemy;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
