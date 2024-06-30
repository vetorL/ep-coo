import java.awt.*;

public class PowerUp extends Enemy{

    public PowerUp() {
        super(4.0, Color.WHITE);
    }

    public void draw(long currentTime) {
        if(getState() == State.EXPLODING){

            double alpha = (currentTime - getExplosion_start()) / (getExplosion_end() - getExplosion_start());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }

        if(getState() == State.ACTIVE){

            GameLib.setColor(getColor());
            GameLib.drawDiamond(getX(), getY(), getRadius());
        }
    }

}
