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
                     EnemyController enemyController) {
        PlayerProjectileManager playerProjectileManager = player.getProjectileManager();
        EnemyProjectileManager enemyProjectileManager = enemyController.getEnemyProjectileManager();
        CircleManager circleManager = enemyController.getCircleManager();
        DiamondManager diamondManager = enemyController.getDiamondManager();

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

        /* deenhando tudo associado a inimigos */

        enemyController.draw(currentTime);

        /* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */

        GameLib.display();
    }

}
