import java.awt.*;

public class Enemy1 extends Enemy{

    private long [] enemy1_nextShoot = new long[10];

    public Enemy1(long nextEnemy) {
        super(nextEnemy, 9.0);
    }

    public void init() {
        for(int i = 0; i < getStates().length; i++) {
            State [] enemy1_states = getStates();
            enemy1_states[i] = State.INACTIVE;
            setStates(enemy1_states);
        };
    }

    public void draw(long currentTime) {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.EXPLODING){

                double alpha = (currentTime - getExplosion_start()[i]) / (getExplosion_end()[i] - getExplosion_start()[i]);
                GameLib.drawExplosion(getX()[i], getY()[i], alpha);
            }

            if(getStates()[i] == State.ACTIVE){

                GameLib.setColor(Color.CYAN);
                GameLib.drawCircle(getX()[i], getY()[i], getRadius());
            }
        }
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

            if(getStates()[i] == State.EXPLODING){

                if(currentTime > getExplosion_end()[i]){

                    State [] enemy1_states = getStates();
                    enemy1_states[i] = State.INACTIVE;
                    setStates(enemy1_states);

                }
            }

            if(getStates()[i] == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(getY()[i] > GameLib.HEIGHT + 10) {
                    State [] enemy1_states = getStates();
                    enemy1_states[i] = State.INACTIVE;
                    setStates(enemy1_states);
                }
                else {

                    double [] enemy1_X = getX();
                    enemy1_X[i] += getV()[i] * Math.cos(getAngle()[i]) * delta;
                    setX(enemy1_X);

                    double [] enemy1_Y = getY();
                    enemy1_Y[i] += getV()[i] * Math.sin(getAngle()[i]) * delta * (-1.0);
                    setY(enemy1_Y);

                    double [] enemy1_angle = getAngle();
                    enemy1_angle[i] += getRV()[i] * delta;
                    setAngle(enemy1_angle);

                    if(currentTime > enemy1_nextShoot[i] && getY()[i] < player.getY()){

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
