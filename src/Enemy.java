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

    public abstract void init();

    public abstract void tryLaunch(long currentTime);

    public abstract void draw(long currentTime);

    public void checkCollisionWithPlayer(long currentTime, Player player) {
        for(int i = 0; i < getStates().length; i++){

            double dx = getX()[i] - player.getX();
            double dy = getY()[i] - player.getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if(dist < (player.getRadius() + getRadius()) * 0.8){
                player.explode(currentTime);
            }
        }
    }

    public void checkCollisionWithPlayerProjectile(long currentTime,
                                                   PlayerProjectileManager playerProjectileManager,
                                                   int k) {
        for(int i = 0; i < getStates().length; i++){

            if(getStates()[i] == State.ACTIVE){

                double dx = getX()[i] - playerProjectileManager.getX()[k];
                double dy = getY()[i] - playerProjectileManager.getY()[k];
                double dist = Math.sqrt(dx * dx + dy * dy);

                if(dist < getRadius()){

                    explode(currentTime, i);
                }
            }
        }
    }

    public void explode(long currentTime, int i) {
        State [] enemy_states = getStates();
        enemy_states[i] = State.EXPLODING;
        setStates(enemy_states);

        double [] enemy_explosion_start = getExplosion_start();
        enemy_explosion_start[i] = currentTime;
        setExplosion_start(enemy_explosion_start);

        double [] enemy_explosion_end = getExplosion_end();
        enemy_explosion_end[i] = currentTime + 500;
        setExplosion_end(enemy_explosion_end);
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
