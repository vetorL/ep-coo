import java.awt.*;

public class Enemy1 extends Enemy {

    public Enemy1(double radius) {
        super(radius, Color.CYAN);
    }

    public void draw(long currentTime) {
        if(getState() == State.EXPLODING){

            double alpha = (currentTime - getExplosion_start()) / (getExplosion_end() - getExplosion_start());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }

        if(getState() == State.ACTIVE){

            GameLib.setColor(getColor());
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }

}
