import java.util.ArrayList;

public class DiamondManager extends EnemyManager {


    private double spawnX = GameLib.WIDTH * 0.20;			// coordenada x do próximo inimigo tipo 2 a aparecer
    private int count = 0;									// contagem de inimigos tipo 2 (usada na "formação de voo")

    public DiamondManager(long nextEnemy) {
        super(nextEnemy);

        int numberOfEnemies = 10;
        double enemyRadius = 12.0;
        ArrayList<Enemy> enemies = new ArrayList<>();
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
            Enemy diamond = getEnemies().get(i);

            if(diamond.getState() == State.EXPLODING){
                // quando terminar a explosao, colocar State como INACTIVE
                if(currentTime > diamond.getExplosion_end()){
                    diamond.setState(State.INACTIVE);
                }
            }

            if(diamond.getState() == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(	diamond.getX() < -10 || diamond.getX() > GameLib.WIDTH + 10 ) {
                    diamond.setState(State.INACTIVE);
                }
                else {

                    boolean shootNow = false;
                    double previousY = diamond.getY();

                    diamond.setX(diamond.getX() + (diamond.getV() * Math.cos(diamond.getAngle()) * delta));
                    diamond.setY(diamond.getY() + (diamond.getV() * Math.sin(diamond.getAngle()) * delta * (-1.0)));
                    diamond.setAngle(diamond.getAngle() + (diamond.getRV() * delta));

                    double threshold = GameLib.HEIGHT * 0.30;

                    if(previousY < threshold && diamond.getY() >= threshold) {

                        if(diamond.getX() < GameLib.WIDTH / 2) {
                            diamond.setRV(0.003);
                        }
                        else{
                            diamond.setRV(-0.003);
                        };
                    }

                    if(diamond.getRV() > 0 && Math.abs(diamond.getAngle() - 3 * Math.PI) < 0.05){
                        diamond.setRV(0.0);
                        diamond.setAngle(3 * Math.PI);

                        shootNow = true;
                    }

                    if(diamond.getRV() < 0 && Math.abs(diamond.getAngle()) < 0.05){
                        diamond.setRV(0.0);
                        diamond.setAngle(0.0);

                        shootNow = true;
                    }

                    if(shootNow){

                        double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
                        int [] freeArray = Main.findFreeIndex(enemyProjectileManager.getStates(), angles.length);

                        for(int k = 0; k < freeArray.length; k++){
                            double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
                            double vx = Math.cos(a);
                            double vy = Math.sin(a);

                            Projectile projectile = new EnemyProjectile(enemyProjectileManager.getRadius());

                            projectile.setX(diamond.getX());
                            projectile.setY(diamond.getY());
                            projectile.setVX(vx * 0.30);
                            projectile.setVY(vy * 0.30);
                            projectile.setState(State.ACTIVE);

                            enemyProjectileManager.add(projectile);
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
