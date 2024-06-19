import java.awt.*;

public class Player {

    private double X;
    private double Y;
    private State state = State.ACTIVE; // estado
    private double VX = 0.25;                           // velocidade no eixo x
    private double VY = 0.25;                           // velocidade no eixo y
    private double radius = 12.0;                       // raio (tamanho aproximado do 'player')
    private double explosion_start = 0;                 // instante do início da explosão
    private double explosion_end = 0;                   // instante do final da explosão
    private long nextShot = System.currentTimeMillis(); // instante a partir do qual pode haver um próximo tiro

    public Player(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public void draw(long currentTime) {
        if(getState() == State.EXPLODING){

            double alpha = (currentTime - getExplosion_start()) / (getExplosion_end() - getExplosion_start());
            GameLib.drawExplosion(getX(), getY(), alpha);
        }
        else{

            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(getX(), getY(), getRadius());
        }
    }

    public void explode(long currentTime) {
        setState(State.EXPLODING);
        setExplosion_start(currentTime);
        setExplosion_end(currentTime + 2000);
    }

    public void fire(long currentTime, PlayerProjectileManager playerProjectileManager) {
        if(currentTime > getNextShot()){

            int free = Main.findFreeIndex(playerProjectileManager.getStates());

            if(free < playerProjectileManager.getStates().length){

                double [] projectile_X = playerProjectileManager.getX();
                projectile_X[free] = getX();
                playerProjectileManager.setX(projectile_X);

                double [] projectile_Y = playerProjectileManager.getY();
                projectile_Y[free] = getY() - 2 * getRadius();
                playerProjectileManager.setY(projectile_Y);

                double [] projectile_VX = playerProjectileManager.getVX();
                projectile_VX[free] = 0.0;
                playerProjectileManager.setVX(projectile_VX);

                double [] projectile_VY = playerProjectileManager.getVY();
                projectile_VY[free] = -1.0;
                playerProjectileManager.setVY(projectile_VY);

                State [] projectile_states = playerProjectileManager.getStates();
                projectile_states[free] = State.ACTIVE;
                playerProjectileManager.setStates(projectile_states);

                setNextShot(currentTime + 100);
            }
        }
    }

    public void moveUp(long delta) {
        setY(getY() - delta * getVY());
    }

    public void moveDown(long delta) {
        setY(getY() + delta * getVY());
    }

    public void moveLeft(long delta) {
        setX(getX() - delta * getVX());
    }

    public void moveRight(long delta) {
        setX(getX() + delta * getVY());
    }

    public void checkCollisionWithEnemyProjectile(long currentTime,
                                                  EnemyProjectileManager enemyProjectileManager) {
        for(int i = 0; i < enemyProjectileManager.getStates().length; i++){

            double dx = enemyProjectileManager.getX()[i] - getX();
            double dy = enemyProjectileManager.getY()[i] - getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if(dist < (getRadius() + enemyProjectileManager.getRadius()) * 0.8){
                explode(currentTime);
            }
        }
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void isWithinBounds() {
        if(getX() < 0.0) setX(0.0);
        if(getX() >= GameLib.WIDTH) setX(GameLib.WIDTH - 1);
        if(getY() < 25.0) setY(25.0);
        if(getY() >= GameLib.HEIGHT) setY(GameLib.HEIGHT - 1);
    }

    public void setVX(double VX) {
        this.VX = VX;
    }

    public void setVY(double VY) {
        this.VY = VY;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setExplosion_start(double explosion_start) {
        this.explosion_start = explosion_start;
    }

    public void setExplosion_end(double explosion_end) {
        this.explosion_end = explosion_end;
    }

    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public State getState() {
        return state;
    }

    public double getVX() {
        return VX;
    }

    public double getVY() {
        return VY;
    }

    public double getRadius() {
        return radius;
    }

    public double getExplosion_start() {
        return explosion_start;
    }

    public double getExplosion_end() {
        return explosion_end;
    }

    public long getNextShot() {
        return nextShot;
    }

    public void setX(double x) {
        this.X = x;
    }

    public void setY(double y) {
        this.Y = y;
    }
}
