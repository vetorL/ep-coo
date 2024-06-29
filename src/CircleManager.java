import java.util.ArrayList;

public class CircleManager extends EnemyManager {

    public CircleManager() {
        super(System.currentTimeMillis() + 2000);

        int numberOfEnemies = 10;
        ArrayList<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new Circle());
        }

        super.setEnemies(enemies);
    }

    public void tryLaunch(long currentTime) {
        if(currentTime > getNextEnemy()){
            Circle circle = new Circle();

            circle.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
            circle.setY(-10.0);
            circle.setV(0.20 + Math.random() * 0.15);
            circle.setAngle(3 * Math.PI / 2);
            circle.setRV(0.0);
            circle.setState(State.ACTIVE);
            circle.setNextShot(currentTime + 500);

            setNextEnemy(currentTime + 500);

            getEnemies().add(circle);
        }
    }

    public void updatePosition(long currentTime,
                               EnemyProjectileManager enemyProjectileManager,
                               long delta,
                               Player player) {
        for(int i = 0; i < getStates().length; i++){

            Circle circle = (Circle) getEnemies().get(i);

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

                    if(currentTime > circle.getNextShot() && circle.getY() < player.getY()){
                        Projectile projectile = new EnemyProjectile(enemyProjectileManager.getRadius());

                        projectile.setX(circle.getX());

                        projectile.setY(circle.getY());

                        projectile.setVX(Math.cos(circle.getAngle()) * 0.45);

                        projectile.setVY(Math.sin(circle.getAngle()) * 0.45 * (-1.0));

                        projectile.setState(State.ACTIVE);

                        long nextShot = (long) (currentTime + 200 + Math.random() * 500);
                        circle.setNextShot(nextShot);

                        enemyProjectileManager.add(projectile);
                    }
                }
            }
        }
    }

}
