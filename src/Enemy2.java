import java.awt.*;

public class Enemy2 extends Enemy {

    public Enemy2(double radius) {
        super(radius, Color.MAGENTA);
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
