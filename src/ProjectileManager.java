public abstract class ProjectileManager {

    private State [] states;                // estados
    private double [] X;				    // coordenadas x
    private double [] Y;				    // coordenadas y
    private double [] VX;				    // velocidades no eixo x
    private double [] VY;				    // velocidades no eixo y

    public abstract void init();

    public State[] getStates() {
        return states;
    }

    public void setStates(State[] states) {
        this.states = states;
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

    public double[] getVX() {
        return VX;
    }

    public void setVX(double[] VX) {
        this.VX = VX;
    }

    public double[] getVY() {
        return VY;
    }

    public void setVY(double[] VY) {
        this.VY = VY;
    }
}
