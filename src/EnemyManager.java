import java.awt.*;

public abstract class EnemyManager {

    private long nextEnemy;					// instante em que um novo inimigo deve aparecer

    private Enemy [] enemies = new Enemy[10];

    public EnemyManager(long nextEnemy, double radius) {
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy();
        }
        this.nextEnemy = nextEnemy;
        setRadius(radius);
    }

    public abstract void init();

    public abstract void tryLaunch(long currentTime);

//    public void draw(long currentTime, Color color) {
//        for (Enemy enemy : enemies) {
//            enemy.draw(currentTime, color);
//        }
//    };

    public void checkCollisionWithPlayer(long currentTime, Player player) {
        for(Enemy enemy : enemies) {
            enemy.checkCollisionWithPlayer(currentTime, player);
        }
    }

    public void checkCollisionWithPlayerProjectile(long currentTime,
                                                   PlayerProjectileManager playerProjectileManager,
                                                   int k) {
        for(Enemy enemy : enemies) {
            enemy.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
        }
    }

    public void explode(long currentTime, int i) {
        enemies[i].explode(currentTime);
    }

    public State[] getStates() {
        State[] states = new State[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            states[i] = enemies[i].getState();
        }
        return states;
    }

    public void setStates(State[] states) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setState(states[i]);
        }
    }

    public double[] getX() {
        double[] X = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            X[i] = enemies[i].getX();
        }
        return X;
    }

    public void setX(double[] x) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setX(x[i]);
        }
    }

    public double[] getY() {
        double [] Y = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            Y[i] = enemies[i].getY();
        }
        return Y;
    }

    public void setY(double[] y) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setY(y[i]);
        }
    }

    public double[] getV() {
        double[] V = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            V[i] = enemies[i].getV();
        }
        return V;
    }

    public void setV(double[] v) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setV(v[i]);
        }
    }

    public double[] getAngle() {
        double[] angle = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            angle[i] = enemies[i].getAngle();
        }
        return angle;
    }

    public void setAngle(double[] angle) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setAngle(angle[i]);
        }
    }

    public double[] getRV() {
        double[] RV = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            RV[i] = enemies[i].getRV();
        }
        return RV;
    }

    public void setRV(double[] RV) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setRV(RV[i]);
        }
    }

    public double[] getExplosion_start() {
        double [] explosion_start = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            explosion_start[i] = enemies[i].getExplosion_start();
        }
        return explosion_start;
    }

    public void setExplosion_start(double[] explosion_start) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setExplosion_start(explosion_start[i]);
        }
    }

    public double[] getExplosion_end() {
        double [] explosion_end = new double[enemies.length];
        for(int i = 0; i < enemies.length; i++) {
            explosion_end[i] = enemies[i].getExplosion_end();
        }
        return explosion_end;
    }

    public void setExplosion_end(double[] explosion_end) {
        for(int i = 0; i < enemies.length; i++) {
            enemies[i].setExplosion_end(explosion_end[i]);
        }
    }

    public double getRadius() {
        return enemies[0].getRadius();
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
}
