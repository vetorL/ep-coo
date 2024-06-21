import java.awt.*;

public interface Hostile {

    void checkCollisionWithPlayer(long currentTime, Player player);

    void checkCollisionWithPlayerProjectile(long currentTime,
                                            PlayerProjectileManager playerProjectileManager,
                                            int k);

    void explode(long currentTime);

    void draw(long currentTime);

    void init();

    State getState();
    double getX();
    double getY();
    double getV();
    double getAngle();
    double getRV();
    double getExplosion_start();
    double getExplosion_end();
    double getRadius();

    void setState(State state);
    void setX(double x);
    void setY(double y);
    void setV(double v);
    void setAngle(double angle);
    void setRV(double RV);
    void setExplosion_start(double explosion_start);
    void setExplosion_end(double explosion_end);
    void setRadius(double radius);

}
