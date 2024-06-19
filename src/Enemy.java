import java.awt.*;

public class Enemy {

    private State state;					// estados
    private double X;					// coordenadas x
    private double Y;					// coordenadas y
    private double V;					// velocidades
    private double angle;				// ângulos (indicam direção do movimento)
    private double RV;					// velocidades de rotação
    private double explosion_start;		// instantes dos inícios das explosões
    private double explosion_end;		// instantes do próximo tiro
    private double radius;					// raio (tamanho do inimigo)
//    private long nextEnemy;					// instante em que um novo inimigo deve aparecer

    public Enemy() {
    }

    public Enemy(State state, double x, double y,
                 double v, double angle, double RV,
                 double explosion_start, double explosion_end,
                 double radius, long nextEnemy) {
        this.state = state;
        X = x;
        Y = y;
        V = v;
        this.angle = angle;
        this.RV = RV;
        this.explosion_start = explosion_start;
        this.explosion_end = explosion_end;
        this.radius = radius;
    }

//    public void draw(long currentTime, Color color) {
//        if(getState() == State.EXPLODING){
//
//            double alpha = (currentTime - getExplosion_start()) / (getExplosion_end() - getExplosion_start());
//            GameLib.drawExplosion(getX(), getY(), alpha);
//        }
//
//        if(getState() == State.ACTIVE){
//
//            GameLib.setColor(color);
//            GameLib.drawDiamond(getX(), getY(), getRadius());
//        }
//    }

    public void explode(long currentTime) {
        state = State.EXPLODING;
        explosion_start = currentTime;
        explosion_end = currentTime + 500;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getV() {
        return V;
    }

    public void setV(double v) {
        V = v;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRV() {
        return RV;
    }

    public void setRV(double RV) {
        this.RV = RV;
    }

    public double getExplosion_start() {
        return explosion_start;
    }

    public void setExplosion_start(double explosion_start) {
        this.explosion_start = explosion_start;
    }

    public double getExplosion_end() {
        return explosion_end;
    }

    public void setExplosion_end(double explosion_end) {
        this.explosion_end = explosion_end;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
