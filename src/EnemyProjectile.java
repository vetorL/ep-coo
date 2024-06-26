import java.awt.*;

public class EnemyProjectile extends Projectile {
    private double radius;      // raio (tamanho dos projéteis inimigos)

    public EnemyProjectile(double radius) {
        this.radius = radius;
    }

    public void updatePosition(long delta) {
        if(getState() == State.ACTIVE){

            /* verificando se projétil saiu da tela */
            if(getY() > GameLib.HEIGHT) {
                setState(State.INACTIVE);
            }
            else {
                setX(getX() + (getVX() * delta));
                setY(getY() + (getVY() * delta));
            }
        }
    }

    public void drawProjectile() {
        if(getState() == State.ACTIVE){

            GameLib.setColor(Color.RED);
            GameLib.drawCircle(getX(), getY(), getRadius());
        }
    }

    public double getRadius() {
        return radius;
    }
}
