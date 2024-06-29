import java.awt.*;

public class BackgroundManager {

    /* estrelas que formam o fundo de primeiro plano */
    private final Background firstBackground;

    /* estrelas que formam o fundo de segundo plano */
    private final Background secondBackground;

    public BackgroundManager() {
        firstBackground = new Background(20, 0.070, 0.0);
        secondBackground = new Background(50, 0.045, 0.0);

        firstBackground.init();
        secondBackground.init();
    }

    public void drawScene(long currentTime, long delta, Player player,
                     CircleManager enemy1,
                     DiamondManager enemy2,
                     EnemyProjectileManager enemyProjectileManager) {
        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();

        /* desenhando plano fundo distante */

        GameLib.setColor(Color.DARK_GRAY);
        secondBackground.draw(delta, 2);

        /* desenhando plano de fundo próximo */

        GameLib.setColor(Color.GRAY);
        firstBackground.draw(delta, 3);

        /* desenhando player */

        player.draw(currentTime);


        /* deenhando projeteis (player) */

        playerProjectileManager.drawProjectiles();

        /* desenhando projeteis (inimigos) */

        enemyProjectileManager.drawProjectiles();

        /* desenhando inimigos (tipo 1) */

        enemy1.draw(currentTime);

        /* desenhando inimigos (tipo 2) */

        enemy2.draw(currentTime);

        /* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */

        GameLib.display();
    }

}
