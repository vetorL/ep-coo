import java.awt.*;

public class SlasherMcDasher extends Enemy {

    public SlasherMcDasher() {
        super(50.0, Color.YELLOW);
    }

    public void draw(long currentTime) {
        if(getState() == State.EXPLODING){

            double alpha = (currentTime - getExplosion_start()) / (getExplosion_end() - getExplosion_start());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }

        if(getState() == State.ACTIVE){

            GameLib.setColor(getColor());
            GameLib.drawCircle(getX(), getY(), getRadius());

            GameLib.drawLine(getX() - getRadius(), getY(), getX() + getRadius(), getY());
            GameLib.drawLine(getX(), getY() - getRadius(), getX(), getY() + getRadius());
        }
    }

}
