public class Background {

    private double [] X;
    private double [] Y;
    private double speed;
    private double count;

    public Background(int size, double speed, double count) {
        this.X = new double[size];
        this.Y = new double[size];
        this.speed = speed;
        this.count = count;
    }

    public void draw(long delta, int x) {
        setCount(getCount() + getSpeed() * delta);

        for(int i = 0; i < getX().length; i++){

            GameLib.fillRect(getX()[i], (getY()[i] + getCount()) % GameLib.HEIGHT, x, x);
        }
    }

    public double[] getX() {
        return X;
    }

    public void setX(double[] x) {
        X = x;
    }

    public double[] getY() {
        return Y;
    }

    public void setY(double[] y) {
        Y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
