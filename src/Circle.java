import java.awt.*;

public class Circle extends Enemy {

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

    public static Enemy launch(long currentTime) {
        Enemy circle = new Circle();

        circle.setX(Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
        circle.setY(-10.0);
        circle.setV(0.20 + Math.random() * 0.15);
        circle.setAngle(3 * Math.PI / 2);
        circle.setRV(0.0);
        circle.setState(State.ACTIVE);

        return circle;
    }

}
