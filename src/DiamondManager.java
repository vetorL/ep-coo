import java.util.ArrayList;

public class DiamondManager extends EnemyManager {


    private double spawnX = GameLib.WIDTH * 0.20;			// coordenada x do próximo inimigo tipo 2 a aparecer
    private int count = 0;									// contagem de inimigos tipo 2 (usada na "formação de voo")

    public DiamondManager(long nextEnemy) {
        super(nextEnemy);

        int numberOfEnemies = 10;
        double enemyRadius = 12.0;
        ArrayList<Hostile> enemies = new ArrayList<>();
        for(int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new Diamond(enemyRadius));
        }

        super.setEnemies(enemies);
    }

    public void tryLaunch(long currentTime) {
        if(currentTime > getNextEnemy()){

            int free = Main.findFreeIndex(getStates());

            if(free < getStates().length){

                double [] enemy2_X = getX();
                enemy2_X[free] = getSpawnX();
                setX(enemy2_X);

                double [] enemy2_Y = getY();
                enemy2_Y[free] = -10.0;
                setY(enemy2_Y);

                double [] enemy2_V = getV();
                enemy2_V[free] = 0.42;
                setV(enemy2_V);

                double [] enemy2_angle = getAngle();
                enemy2_angle[free] = (3 * Math.PI) / 2;
                setAngle(enemy2_angle);

                double [] enemy2_RV = getRV();
                enemy2_RV[free] = 0.0;
                setRV(enemy2_RV);

                State [] enemy2_states = getStates();
                enemy2_states[free] = State.ACTIVE;
                setStates(enemy2_states);

                this.count++;

                if(getCount() < 10){

                    setNextEnemy(currentTime + 120);
                }
                else {

                    setCount(0);
                    setSpawnX(Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
                    setNextEnemy((long) (currentTime + 3000 + Math.random() * 3000));

                }
            }
        }
    }

    public void updatePosition(long currentTime,
                               EnemyProjectileManager enemyProjectileManager,
                               long delta) {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.EXPLODING){

                if(currentTime > getExplosion_end()[i]){
                    State [] enemy2_states = getStates();
                    enemy2_states[i] = State.INACTIVE;
                    setStates(enemy2_states);
                }
            }

            if(getStates()[i] == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(	getX()[i] < -10 || getX()[i] > GameLib.WIDTH + 10 ) {
                    State [] enemy2_states = getStates();
                    enemy2_states[i] = State.INACTIVE;
                    setStates(enemy2_states);
                }
                else {

                    boolean shootNow = false;
                    double previousY = getY()[i];

                    double [] enemy2_X = getX();
                    enemy2_X[i] += getV()[i] * Math.cos(getAngle()[i]) * delta;
                    setX(enemy2_X);

                    double [] enemy2_Y = getY();
                    enemy2_Y[i] += getV()[i] * Math.sin(getAngle()[i]) * delta * (-1.0);
                    setY(enemy2_Y);

                    double [] enemy2_angle = getAngle();
                    enemy2_angle[i] += getRV()[i] * delta;
                    setAngle(enemy2_angle);

                    double threshold = GameLib.HEIGHT * 0.30;

                    if(previousY < threshold && getY()[i] >= threshold) {

                        if(getX()[i] < GameLib.WIDTH / 2) {
                            double [] enemy2_RV = getRV();
                            enemy2_RV[i] = 0.003;
                            setRV(enemy2_RV);
                        }
                        else{
                            double [] enemy2_RV = getRV();
                            enemy2_RV[i] = -0.003;
                            setRV(enemy2_RV);
                        };
                    }

                    if(getRV()[i] > 0 && Math.abs(getAngle()[i] - 3 * Math.PI) < 0.05){
                        double [] enemy2_RV = getRV();
                        enemy2_RV[i] = 0.0;
                        setRV(enemy2_RV);

//                        double [] enemy2_angle = getAngle();
                        enemy2_angle[i] = 3 * Math.PI;
                        setAngle(enemy2_angle);

                        shootNow = true;
                    }

                    if(getRV()[i] < 0 && Math.abs(getAngle()[i]) < 0.05){

                        double [] enemy2_RV = getRV();
                        enemy2_RV[i] = 0.0;
                        setRV(enemy2_RV);

//                        double [] enemy2_angle = getAngle();
                        enemy2_angle[i] = 0.0;
                        setAngle(enemy2_angle);

                        shootNow = true;
                    }

                    if(shootNow){

                        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
                        int [] freeArray = Main.findFreeIndex(enemyProjectileManager.getStates(), angles.length);

                        for(int k = 0; k < freeArray.length; k++){

                            int free = freeArray[k];

                            if(free < enemyProjectileManager.getStates().length){

                                double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
                                double vx = Math.cos(a);
                                double vy = Math.sin(a);

                                double [] e_projectile_X = enemyProjectileManager.getX();
                                e_projectile_X[free] = getX()[i];
                                enemyProjectileManager.setX(e_projectile_X);

                                double [] e_projectile_Y = enemyProjectileManager.getY();
                                e_projectile_Y[free] = getY()[i];
                                enemyProjectileManager.setY(e_projectile_Y);

                                double [] e_projectile_VX = enemyProjectileManager.getVX();
                                e_projectile_VX[free] = vx * 0.30;
                                enemyProjectileManager.setVX(e_projectile_VX);

                                double [] e_projectile_VY = enemyProjectileManager.getVY();
                                e_projectile_VY[free] = vy * 0.30;
                                enemyProjectileManager.setVY(e_projectile_VY);

                                State [] e_projectile_states = enemyProjectileManager.getStates();
                                e_projectile_states[free] = State.ACTIVE;
                                enemyProjectileManager.setStates(e_projectile_states);
                            }
                        }
                    }
                }
            }
        }
    }

    public double getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
