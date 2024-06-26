import java.awt.*;

public class Circle extends Enemy {

    private long nextShot;

    public Circle() {
        super(9.0, Color.CYAN);
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

    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public long getNextShot() {
        return nextShot;
    }
}
