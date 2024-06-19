import java.awt.*;

public class BackgroundManager {

    public void drawScene(long currentTime, long delta, Player player,
                     PlayerProjectileManager playerProjectileManager,
                     EnemyManager1 enemy1,
                     EnemyManager2 enemy2,
                     EnemyProjectileManager enemyProjectileManager,
                     Background firstBackground,
                     Background secondBackground) {
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
