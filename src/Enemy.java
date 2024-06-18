public abstract class Enemy {

    private State [] states = new State[10];					// estados
    private double [] X = new double[10];					// coordenadas x
    private double [] Y = new double[10];					// coordenadas y
    private double [] V = new double[10];					// velocidades
    private double [] angle = new double[10];				// ângulos (indicam direção do movimento)
    private double [] RV = new double[10];					// velocidades de rotação
    private double [] explosion_start = new double[10];		// instantes dos inícios das explosões
    private double [] explosion_end = new double[10];		// instantes do próximo tiro
    private double radius;					// raio (tamanho do inimigo)
    private long nextEnemy;					// instante em que um novo inimigo deve aparecer

    public Enemy(long nextEnemy, double radius) {
        this.nextEnemy = nextEnemy;
        this.radius = radius;
    }

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

    public double[] getV() {
        return V;
    }

    public void setV(double[] v) {
        V = v;
    }

    public double[] getAngle() {
        return angle;
    }

    public void setAngle(double[] angle) {
        this.angle = angle;
    }

    public double[] getRV() {
        return RV;
    }

    public void setRV(double[] RV) {
        this.RV = RV;
    }

    public double[] getExplosion_start() {
        return explosion_start;
    }

    public void setExplosion_start(double[] explosion_start) {
        this.explosion_start = explosion_start;
    }

    public double[] getExplosion_end() {
        return explosion_end;
    }

    public void setExplosion_end(double[] explosion_end) {
        this.explosion_end = explosion_end;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public long getNextEnemy() {
        return nextEnemy;
    }

    public void setNextEnemy(long nextEnemy) {
        this.nextEnemy = nextEnemy;
    }
}
