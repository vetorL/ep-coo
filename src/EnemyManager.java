import java.awt.*;

public abstract class EnemyManager {

//    private State [] states = new State[10];					// estados
//    private double [] X = new double[10];					// coordenadas x
//    private double [] Y = new double[10];					// coordenadas y
//    private double [] V = new double[10];					// velocidades
//    private double [] angle = new double[10];				// ângulos (indicam direção do movimento)
//    private double [] RV = new double[10];					// velocidades de rotação
//    private double [] explosion_start = new double[10];		// instantes dos inícios das explosões
//    private double [] explosion_end = new double[10];		// instantes do próximo tiro
//    private double radius;					// raio (tamanho do inimigo)
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
        for(int i = 0; i < getStates().length; i++){

            double dx = getX()[i] - player.getX();
            double dy = getY()[i] - player.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if(dist < (player.getRadius() + getRadius()) * 0.8){
                player.explode(currentTime);
            }
        }
    }

    public void checkCollisionWithPlayerProjectile(long currentTime,
                                                   PlayerProjectileManager playerProjectileManager,
                                                   int k) {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.ACTIVE){

                double dx = getX()[i] - playerProjectileManager.getX()[k];
                double dy = getY()[i] - playerProjectileManager.getY()[k];
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist < getRadius()){

                    explode(currentTime, i);
                }
            }
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
