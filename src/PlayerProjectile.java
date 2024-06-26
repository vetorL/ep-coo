import java.awt.*;

public class PlayerProjectile extends Projectile {

    public void updatePosition(long delta) {
        if(getState() == State.ACTIVE){
            /* verificando se projétil saiu da tela */
            if(getY() < 0) {
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
            GameLib.setColor(Color.GREEN);
            GameLib.drawLine(getX(), getY() - 5,
                    getX(), getY() + 5);
            GameLib.drawLine(getX() - 1, getY() - 3,
                    getX() - 1, getY() + 3);
            GameLib.drawLine(getX() + 1, getY() - 3,
                    getX() + 1, getY() + 3);
        }
    }

}
