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
		
		State [] enemy2_states = new State[10];						// estados
		double [] enemy2_X = new double[10];					// coordenadas x
		double [] enemy2_Y = new double[10];					// coordenadas y
		double [] enemy2_V = new double[10];					// velocidades
		double [] enemy2_angle = new double[10];				// ângulos (indicam direção do movimento)
		double [] enemy2_RV = new double[10];					// velocidades de rotação
		double [] enemy2_explosion_start = new double[10];		// instantes dos inícios das explosões
		double [] enemy2_explosion_end = new double[10];		// instantes dos finais das explosões
		double enemy2_spawnX = GameLib.WIDTH * 0.20;			// coordenada x do próximo inimigo tipo 2 a aparecer
		int enemy2_count = 0;									// contagem de inimigos tipo 2 (usada na "formação de voo")
		double enemy2_radius = 12.0;							// raio (tamanho aproximado do inimigo 2)
		long nextEnemy2 = currentTime + 7000;					// instante em que um novo inimigo 2 deve aparecer
		
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
		for(int i = 0; i < enemy2_states.length; i++) enemy2_states[i] = State.INACTIVE;

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
				
				for(int i = 0; i < enemy2_states.length; i++){
					
					double dx = enemy2_X[i] - player.getX();
					double dy = enemy2_Y[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy2_radius) * 0.8){
						player.explode(currentTime);
					}
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for(int k = 0; k < playerProjectileManager.getStates().length; k++){
				
				for(int i = 0; i < enemy1.getStates().length; i++){
										
					if(enemy1.getStates()[i] == State.ACTIVE){
					
						double dx = enemy1.getX()[i] - playerProjectileManager.getX()[k];
						double dy = enemy1.getY()[i] - playerProjectileManager.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemy1.getRadius()){
							
							enemy1.explode(currentTime, i);
						}
					}
				}
				
				for(int i = 0; i < enemy2_states.length; i++){
					
					if(enemy2_states[i] == State.ACTIVE){
						
						double dx = enemy2_X[i] - playerProjectileManager.getX()[k];
						double dy = enemy2_Y[i] - playerProjectileManager.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemy2_radius){
							
							enemy2_states[i] = State.EXPLODING;
							enemy2_explosion_start[i] = currentTime;
							enemy2_explosion_end[i] = currentTime + 500;
						}
					}
				}
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
			
			for(int i = 0; i < enemy2_states.length; i++){
				
				if(enemy2_states[i] == State.EXPLODING){
					
					if(currentTime > enemy2_explosion_end[i]){
						
						enemy2_states[i] = State.INACTIVE;
					}
				}
				
				if(enemy2_states[i] == State.ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(	enemy2_X[i] < -10 || enemy2_X[i] > GameLib.WIDTH + 10 ) {
						
						enemy2_states[i] = State.INACTIVE;
					}
					else {
						
						boolean shootNow = false;
						double previousY = enemy2_Y[i];
												
						enemy2_X[i] += enemy2_V[i] * Math.cos(enemy2_angle[i]) * delta;
						enemy2_Y[i] += enemy2_V[i] * Math.sin(enemy2_angle[i]) * delta * (-1.0);
						enemy2_angle[i] += enemy2_RV[i] * delta;
						
						double threshold = GameLib.HEIGHT * 0.30;
						
						if(previousY < threshold && enemy2_Y[i] >= threshold) {
							
							if(enemy2_X[i] < GameLib.WIDTH / 2) enemy2_RV[i] = 0.003;
							else enemy2_RV[i] = -0.003;
						}
						
						if(enemy2_RV[i] > 0 && Math.abs(enemy2_angle[i] - 3 * Math.PI) < 0.05){
							
							enemy2_RV[i] = 0.0;
							enemy2_angle[i] = 3 * Math.PI;
							shootNow = true;
						}
						
						if(enemy2_RV[i] < 0 && Math.abs(enemy2_angle[i]) < 0.05){
							
							enemy2_RV[i] = 0.0;
							enemy2_angle[i] = 0.0;
							shootNow = true;
						}
																		
						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							int [] freeArray = findFreeIndex(enemyProjectileManager.getStates(), angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < enemyProjectileManager.getStates().length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);

									double [] e_projectile_X = enemyProjectileManager.getX();
									e_projectile_X[free] = enemy2_X[i];
									enemyProjectileManager.setX(e_projectile_X);

									double [] e_projectile_Y = enemyProjectileManager.getY();
									e_projectile_Y[free] = enemy2_Y[i];
									enemyProjectileManager.setY(e_projectile_Y);

									double [] e_projectile_VX = enemyProjectileManager.getVX();
									e_projectile_VX[free] = vx * 0.30;
									enemyProjectileManager.setVX(e_projectile_VX);

									double [] e_projectile_VY = enemyProjectileManager.getVY();
									e_projectile_VY[free] = vy * 0.30;
									enemyProjectileManager.setVY(e_projectile_VY);

									State [] e_projectile_states = enemyProjectileManager.getStates();
									e_projectile_states[free] = State.ACTIVE;
									enemyProjectileManager.setStates(e_projectile_states);
								}
							}
						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
			enemy1.tryLaunch(currentTime);
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			
			if(currentTime > nextEnemy2){
				
				int free = findFreeIndex(enemy2_states);
								
				if(free < enemy2_states.length){
					
					enemy2_X[free] = enemy2_spawnX;
					enemy2_Y[free] = -10.0;
					enemy2_V[free] = 0.42;
					enemy2_angle[free] = (3 * Math.PI) / 2;
					enemy2_RV[free] = 0.0;
					enemy2_states[free] = State.ACTIVE;

					enemy2_count++;
					
					if(enemy2_count < 10){
						
						nextEnemy2 = currentTime + 120;
					}
					else {
						
						enemy2_count = 0;
						enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
						nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
					}
				}
			}
			
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
			
			for(int i = 0; i < enemy2_states.length; i++){
				
				if(enemy2_states[i] == State.EXPLODING){
					
					double alpha = (currentTime - enemy2_explosion_start[i]) / (enemy2_explosion_end[i] - enemy2_explosion_start[i]);
					GameLib.drawExplosion(enemy2_X[i], enemy2_Y[i], alpha);
				}
				
				if(enemy2_states[i] == State.ACTIVE){
			
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2_X[i], enemy2_Y[i], enemy2_radius);
				}
			}
			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
}
