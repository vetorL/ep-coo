import java.util.ArrayList;

public class PowerUpManager extends EnemyManager {

    public PowerUpManager() {
        super(System.currentTimeMillis() + 1000);

        int numberOfEnemies = 1;
        ArrayList<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new PowerUp());
        }

        super.setEnemies(enemies);
    }

    public void tryLaunch(long currentTime) {
        if(currentTime > getNextEnemy()){
            PowerUp powerUp = new PowerUp();

            powerUp.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
            powerUp.setY(-10.0);
            powerUp.setV(0.50 + Math.random() * 0.15);
            powerUp.setAngle(3 * Math.PI / 2);
            powerUp.setRV(0.0);
            powerUp.setState(State.ACTIVE);

            setNextEnemy(currentTime + 1000);

            getEnemies().add(powerUp);
        }
    }

    public void updatePosition(long currentTime, long delta) {
        for(int i = 0; i < getStates().length; i++){

            Enemy powerUp = getEnemies().get(i);

            if(powerUp.getState() == State.EXPLODING){
                if(currentTime > powerUp.getExplosion_end()){
                    powerUp.setState(State.INACTIVE);
                }
            }

            if(powerUp.getState() == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(getY()[i] > GameLib.HEIGHT + 10) {
                    powerUp.setState(State.INACTIVE);
                }
                else {

                    powerUp.setX(powerUp.getX() + (powerUp.getV() * Math.cos(powerUp.getAngle()) * delta));
                    powerUp.setY(powerUp.getY() + (powerUp.getV() * Math.sin(powerUp.getAngle()) * delta * (-1.0)));
                    powerUp.setAngle(powerUp.getAngle() + (powerUp.getRV() * delta));

                }
            }
        }
    }

}
