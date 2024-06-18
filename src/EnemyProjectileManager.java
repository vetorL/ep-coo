import java.awt.*;

public class EnemyProjectileManager extends ProjectileManager {

    private double radius;         // raio (tamanho dos projéteis inimigos)

    public EnemyProjectileManager() {
        super.setStates(new State[200]);
        super.setX(new double[200]);
        super.setY(new double[200]);
        super.setVX(new double[200]);
        super.setVY(new double[200]);
        this.radius = 2.0;
    }

    public void init() {
        for(int i = 0; i < getStates().length; i++) getStates()[i] = State.INACTIVE;
    }

    public void updatePosition(long delta) {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.ACTIVE){

                /* verificando se projétil saiu da tela */
                if(getY()[i] > GameLib.HEIGHT) {

                    State [] states = getStates();
                    states[i] = State.INACTIVE;
                    setStates(states);

                }
                else {

                    double [] x = getX();
                    x[i] += getVX()[i] * delta;
                    setX(x);

                    double [] y = getY();
                    y[i] += getVY()[i] * delta;
                    setY(y);
                }
            }
        }
    }

    public void drawProjectiles() {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.ACTIVE){

                GameLib.setColor(Color.RED);
                GameLib.drawCircle(getX()[i], getY()[i], getRadius());
            }
        }
    }

    public double getRadius() {
        return radius;
    }

}
