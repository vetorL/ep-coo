public class Player {

    private final Ponto2D ponto2D;
    private int state = 1;
    private double VX = 0.25;
    private double VY = 0.25;
    private double radius = 12.0;
    private double explosion_start = 0;
    private double explosion_end = 0;
    private long nextShot = System.currentTimeMillis();

    public Player(double x, double y) {
        this.ponto2D = new Ponto2D(x, y);
    }

    public double getX() {
        return ponto2D.getX();
    }

    public double getY() {
        return ponto2D.getY();
    }

    public void setState(int state) {
        this.state = state;
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

    public int getState() {
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
        ponto2D.setX(x);
    }

    public void setY(double y) {
        ponto2D.setY(y);
    }
}
