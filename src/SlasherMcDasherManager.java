import java.util.ArrayList;

public class SlasherMcDasherManager extends EnemyManager {

    public SlasherMcDasherManager() {
        super(System.currentTimeMillis() + 1000);

        int numberOfEnemies = 10;
        ArrayList<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < numberOfEnemies; i++) {
            enemies.add(new SlasherMcDasher());
        }

        super.setEnemies(enemies);
    }

    public void tryLaunch(long currentTime) {
        if(currentTime > getNextEnemy()){
            SlasherMcDasher slasherMcDasher = new SlasherMcDasher();

            slasherMcDasher.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
            slasherMcDasher.setY(-10.0);
            slasherMcDasher.setV(0.20 + Math.random() * 0.15);
            slasherMcDasher.setAngle(3 * Math.PI / 2);
            slasherMcDasher.setRV(0.0);
            slasherMcDasher.setState(State.ACTIVE);

            setNextEnemy(currentTime + 1000);

            getEnemies().add(slasherMcDasher);
        }
    }

    public void updatePosition(long currentTime, long delta) {
        for(int i = 0; i < getStates().length; i++){

            Enemy slasherMcDasher = getEnemies().get(i);

            if(slasherMcDasher.getState() == State.EXPLODING){
                if(currentTime > slasherMcDasher.getExplosion_end()){
                    slasherMcDasher.setState(State.INACTIVE);
                }
            }

            if(slasherMcDasher.getState() == State.ACTIVE){

                /* verificando se inimigo saiu da tela */
                if(getY()[i] > GameLib.HEIGHT + 10) {
                    slasherMcDasher.setState(State.INACTIVE);
                }
                else {

                    slasherMcDasher.setX(slasherMcDasher.getX() + (slasherMcDasher.getV() * Math.cos(slasherMcDasher.getAngle()) * delta));
                    slasherMcDasher.setY(slasherMcDasher.getY() + (slasherMcDasher.getV() * Math.sin(slasherMcDasher.getAngle()) * delta * (-1.0)));
                    slasherMcDasher.setAngle(slasherMcDasher.getAngle() + (slasherMcDasher.getRV() * delta));

                }
            }
        }
    }

}
