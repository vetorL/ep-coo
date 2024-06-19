import java.awt.Color;

public class Main {

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(State [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == State.INACTIVE) break;
		}
		
		return i;
	}
	
	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */ 

	public static int [] findFreeIndex(State [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == State.INACTIVE) {
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		long currentTime = System.currentTimeMillis();

		/* declaração de 'player' */
		Player player = new Player(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);

		/* variáveis dos projéteis disparados pelo player */

		PlayerProjectileManager playerProjectileManager = new PlayerProjectileManager();

		/* variáveis dos inimigos tipo 1 */

		Enemy1 enemy1 = new Enemy1(currentTime + 2000);
		
		/* variáveis dos inimigos tipo 2 */

		Enemy2 enemy2 = new Enemy2(currentTime + 7000);

		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */

		EnemyProjectileManager enemyProjectileManager = new EnemyProjectileManager();

		/* estrelas que formam o fundo de primeiro plano */

		Background firstBackground = new Background(20, 0.070, 0.0);
		
		/* estrelas que formam o fundo de segundo plano */

		Background secondBackground = new Background(50, 0.045, 0.0);
		
		/* inicializações */
		
		playerProjectileManager.init();
		enemyProjectileManager.init();
		enemy1.init();
		enemy2.init();
		firstBackground.init();
		secondBackground.init();
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			if(player.getState() == State.ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				player.checkCollisionWithEnemyProjectile(currentTime, enemyProjectileManager);
			
				/* colisões player - inimigos */
							
				enemy1.checkCollisionWithPlayer(currentTime, player);
				
				enemy2.checkCollisionWithPlayer(currentTime, player);
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for(int k = 0; k < playerProjectileManager.getStates().length; k++){
				enemy1.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
				
				enemy2.checkCollisionWithPlayerProjectile(currentTime, playerProjectileManager, k);
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			
			playerProjectileManager.updatePosition(delta);
			
			/* projeteis (inimigos) */
			
			enemyProjectileManager.updatePosition(delta);
			
			/* inimigos tipo 1 */
			
			enemy1.updatePosition(currentTime, enemyProjectileManager, delta, player);
			
			/* inimigos tipo 2 */

			enemy2.updatePosition(currentTime, enemyProjectileManager, delta);
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
			enemy1.tryLaunch(currentTime);
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			
			enemy2.tryLaunch(currentTime);
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			if(player.getState() == State.EXPLODING){
				
				if(currentTime > player.getExplosion_end()){

					player.setState(State.ACTIVE);
				}
			}
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
			if(player.getState() == State.ACTIVE){
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) {
					player.moveUp(delta);
				}
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
					player.moveDown(delta);
				}
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
					player.moveLeft(delta);
				}
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
					player.moveRight(delta);
				}
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					player.fire(currentTime, playerProjectileManager);
				}
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */

			player.isWithinBounds();

			/*******************/
			/* Desenho da cena */
			/*******************/
			
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
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
