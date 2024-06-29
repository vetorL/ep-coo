import java.util.ArrayList;

public class CircleManager extends EnemyManager {

    private long [] enemy1_nextShoot = new long[10];

    public CircleManager(long nextEnemy) {
        super(nextEnemy);

        int numberOfEnemies = 10;
        double enemyRadius = 9.0;
        ArrayList<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new Circle(enemyRadius));
        }

        super.setEnemies(enemies);
    }

    public void tryLaunch(long currentTime) {
        if(currentTime > getNextEnemy()){

            int free = Main.findFreeIndex(getStates());

            if(free < getStates().length){

                double [] enemy1_X = getX();
                enemy1_X[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
                setX(enemy1_X);

                double [] enemy1_Y = getY();
                enemy1_Y[free] = -10.0;
                setY(enemy1_Y);

                double [] enemy1_V = getV();
                enemy1_V[free] = 0.20 + Math.random() * 0.15;
                setV(enemy1_V);

                double [] enemy1_angle = getAngle();
                enemy1_angle[free] = 3 * Math.PI / 2;
                setAngle(enemy1_angle);

                double [] enemy1_RV = getRV();
                enemy1_RV[free] = 0.0;
                setRV(enemy1_RV);

                State [] enemy1_states = getStates();
                enemy1_states[free] = State.ACTIVE;
                setStates(enemy1_states);

                this.enemy1_nextShoot[free] = currentTime + 500;

                long nextEnemy1 = currentTime + 500;
                setNextEnemy(nextEnemy1);

            }
        }
    }

    public void updatePosition(long currentTime,
                               EnemyProjectileManager enemyProjectileManager,
                               long delta,
                               Player player) {
        for(int i = 0; i < getStates().length; i++){

            Enemy circle = getEnemies().get(i);

            if(circle.getState() == State.EXPLODING){
                if(currentTime > circle.getExplosion_end()){
                    circle.setState(State.INACTIVE);
                }
            }

            if(circle.getState() == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(getY()[i] > GameLib.HEIGHT + 10) {
                    circle.setState(State.INACTIVE);
                }
                else {

                    circle.setX(circle.getX() + (circle.getV() * Math.cos(circle.getAngle()) * delta));
                    circle.setY(circle.getY() + (circle.getV() * Math.sin(circle.getAngle()) * delta * (-1.0)));
                    circle.setAngle(circle.getAngle() + (circle.getRV() * delta));

                    if(currentTime > enemy1_nextShoot[i] && circle.getY() < player.getY()){

                        int free = Main.findFreeIndex(enemyProjectileManager.getStates());

                        if(free < enemyProjectileManager.getStates().length){

                            double [] e_projectile_X = enemyProjectileManager.getX();
                            e_projectile_X[free] = getX()[i];
                            enemyProjectileManager.setX(e_projectile_X);

                            double [] e_projectile_Y = enemyProjectileManager.getY();
                            e_projectile_Y[free] = getY()[i];
                            enemyProjectileManager.setY(e_projectile_Y);

                            double [] e_projectile_VX = enemyProjectileManager.getVX();
                            e_projectile_VX[free] = Math.cos(getAngle()[i]) * 0.45;
                            enemyProjectileManager.setVX(e_projectile_VX);

                            double [] e_projectile_VY = enemyProjectileManager.getVY();
                            e_projectile_VY[free] = Math.sin(getAngle()[i]) * 0.45 * (-1.0);
                            enemyProjectileManager.setVY(e_projectile_VY);

                            State [] e_projectile_states = enemyProjectileManager.getStates();
                            e_projectile_states[free] = State.ACTIVE;
                            enemyProjectileManager.setStates(e_projectile_states);

                            enemy1_nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
                        }
                    }
                }
            }
        }
    }

}
