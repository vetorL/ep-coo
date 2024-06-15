public class PlayerProjectileManager extends ProjectileManager {
    public PlayerProjectileManager() {
        super.setStates(new State[10]);
        super.setX(new double[10]);
        super.setY(new double[10]);
        super.setVX(new double[10]);
        super.setVY(new double[10]);
    }
}
