import java.awt.*;

public class PlayerProjectileManager extends ProjectileManager {
    public PlayerProjectileManager() {
        super.setStates(new State[10]);
        super.setX(new double[10]);
        super.setY(new double[10]);
        super.setVX(new double[10]);
        super.setVY(new double[10]);
    }

    public void drawProjectiles() {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.ACTIVE){

                GameLib.setColor(Color.GREEN);
                GameLib.drawLine(getX()[i], getY()[i] - 5,
                        getX()[i], getY()[i] + 5);
                GameLib.drawLine(getX()[i] - 1, getY()[i] - 3,
                        getX()[i] - 1, getY()[i] + 3);
                GameLib.drawLine(getX()[i] + 1, getY()[i] - 3,
                        getX()[i] + 1, getY()[i] + 3);
            }
        }
    }

}
