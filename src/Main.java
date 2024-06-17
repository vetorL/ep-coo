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
		
		State [] enemy1_states = new State[10];						// estados
		double [] enemy1_X = new double[10];					// coordenadas x
		double [] enemy1_Y = new double[10];					// coordenadas y
		double [] enemy1_V = new double[10];					// velocidades
		double [] enemy1_angle = new double[10];				// ângulos (indicam direção do movimento)
		double [] enemy1_RV = new double[10];					// velocidades de rotação
		double [] enemy1_explosion_start = new double[10];		// instantes dos inícios das explosões
		double [] enemy1_explosion_end = new double[10];		// instantes dos finais da explosões
		long [] enemy1_nextShoot = new long[10];				// instantes do próximo tiro
		double enemy1_radius = 9.0;								// raio (tamanho do inimigo 1)
		long nextEnemy1 = currentTime + 2000;					// instante em que um novo inimigo 1 deve aparecer
		
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

		State [] e_projectile_states = new State[200];				// estados
		double [] e_projectile_X = new double[200];				// coordenadas x
		double [] e_projectile_Y = new double[200];				// coordenadas y
		double [] e_projectile_VX = new double[200];			// velocidade no eixo x
		double [] e_projectile_VY = new double[200];			// velocidade no eixo y
		double e_projectile_radius = 2.0;						// raio (tamanho dos projéteis inimigos)
		
		/* estrelas que formam o fundo de primeiro plano */

		Background firstBackground = new Background(20, 0.070, 0.0);
		
		/* estrelas que formam o fundo de segundo plano */
		
		double [] background2_X = new double[50];
		double [] background2_Y = new double[50];
		double background2_speed = 0.045;
		double background2_count = 0.0;
		
		/* inicializações */
		
		for(int i = 0; i < playerProjectileManager.getStates().length; i++) {

			State [] projectile_states = playerProjectileManager.getStates();
			projectile_states[i] = State.INACTIVE;
			playerProjectileManager.setStates(projectile_states);

		};
		for(int i = 0; i < e_projectile_states.length; i++) e_projectile_states[i] = State.INACTIVE;
		for(int i = 0; i < enemy1_states.length; i++) enemy1_states[i] = State.INACTIVE;
		for(int i = 0; i < enemy2_states.length; i++) enemy2_states[i] = State.INACTIVE;

		for(int i = 0; i < firstBackground.getX().length; i++){

			double [] x = firstBackground.getX();
			x[i] = Math.random() * GameLib.WIDTH;
			firstBackground.setX(x);

			double [] y = firstBackground.getY();
			y[i] = Math.random() * GameLib.HEIGHT;
			firstBackground.setY(y);
		}
		
		for(int i = 0; i < background2_X.length; i++){
			
			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
						
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
				
				for(int i = 0; i < e_projectile_states.length; i++){
					
					double dx = e_projectile_X[i] - player.getX();
					double dy = e_projectile_Y[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + e_projectile_radius) * 0.8){
						
						player.setState(State.EXPLODING);
						player.setExplosion_start(currentTime);
						player.setExplosion_end(currentTime + 2000);
					}
				}
			
				/* colisões player - inimigos */
							
				for(int i = 0; i < enemy1_states.length; i++){
					
					double dx = enemy1_X[i] - player.getX();
					double dy = enemy1_Y[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy1_radius) * 0.8){

						player.setState(State.EXPLODING);
						player.setExplosion_start(currentTime);
						player.setExplosion_end(currentTime + 2000);
					}
				}
				
				for(int i = 0; i < enemy2_states.length; i++){
					
					double dx = enemy2_X[i] - player.getX();
					double dy = enemy2_Y[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemy2_radius) * 0.8){

						player.setState(State.EXPLODING);
						player.setExplosion_start(currentTime);
						player.setExplosion_end(currentTime + 2000);
					}
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
			for(int k = 0; k < playerProjectileManager.getStates().length; k++){
				
				for(int i = 0; i < enemy1_states.length; i++){
										
					if(enemy1_states[i] == State.ACTIVE){
					
						double dx = enemy1_X[i] - playerProjectileManager.getX()[k];
						double dy = enemy1_Y[i] - playerProjectileManager.getY()[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemy1_radius){
							
							enemy1_states[i] = State.EXPLODING;
							enemy1_explosion_start[i] = currentTime;
							enemy1_explosion_end[i] = currentTime + 500;
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
			
			for(int i = 0; i < playerProjectileManager.getStates().length; i++){
				
				if(playerProjectileManager.getStates()[i] == State.ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(playerProjectileManager.getY()[i] < 0) {

						State [] projectile_states = playerProjectileManager.getStates();
						projectile_states[i] = State.INACTIVE;
						playerProjectileManager.setStates(projectile_states);

					}
					else {

						double [] projectile_X = playerProjectileManager.getX();
						projectile_X[i] += playerProjectileManager.getVX()[i] * delta;
						playerProjectileManager.setX(projectile_X);

						double [] projectile_Y = playerProjectileManager.getY();
						projectile_Y[i] += playerProjectileManager.getVY()[i] * delta;
						playerProjectileManager.setY(projectile_Y);

					}
				}
			}
			
			/* projeteis (inimigos) */
			
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == State.ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(e_projectile_Y[i] > GameLib.HEIGHT) {
						
						e_projectile_states[i] = State.INACTIVE;
					}
					else {
					
						e_projectile_X[i] += e_projectile_VX[i] * delta;
						e_projectile_Y[i] += e_projectile_VY[i] * delta;
					}
				}
			}
			
			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemy1_states.length; i++){
				
				if(enemy1_states[i] == State.EXPLODING){
					
					if(currentTime > enemy1_explosion_end[i]){
						
						enemy1_states[i] = State.INACTIVE;
					}
				}
				
				if(enemy1_states[i] == State.ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemy1_Y[i] > GameLib.HEIGHT + 10) {
						
						enemy1_states[i] = State.INACTIVE;
					}
					else {
					
						enemy1_X[i] += enemy1_V[i] * Math.cos(enemy1_angle[i]) * delta;
						enemy1_Y[i] += enemy1_V[i] * Math.sin(enemy1_angle[i]) * delta * (-1.0);
						enemy1_angle[i] += enemy1_RV[i] * delta;
						
						if(currentTime > enemy1_nextShoot[i] && enemy1_Y[i] < player.getY()){
																							
							int free = findFreeIndex(e_projectile_states);
							
							if(free < e_projectile_states.length){
								
								e_projectile_X[free] = enemy1_X[i];
								e_projectile_Y[free] = enemy1_Y[i];
								e_projectile_VX[free] = Math.cos(enemy1_angle[i]) * 0.45;
								e_projectile_VY[free] = Math.sin(enemy1_angle[i]) * 0.45 * (-1.0);
								e_projectile_states[free] = State.ACTIVE;
								
								enemy1_nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
							}
						}
					}
				}
			}
			
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
							int [] freeArray = findFreeIndex(e_projectile_states, angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < e_projectile_states.length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);
										
									e_projectile_X[free] = enemy2_X[i];
									e_projectile_Y[free] = enemy2_Y[i];
									e_projectile_VX[free] = vx * 0.30;
									e_projectile_VY[free] = vy * 0.30;
									e_projectile_states[free] = State.ACTIVE;
								}
							}
						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
			if(currentTime > nextEnemy1){
				
				int free = findFreeIndex(enemy1_states);
								
				if(free < enemy1_states.length){
					
					enemy1_X[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
					enemy1_Y[free] = -10.0;
					enemy1_V[free] = 0.20 + Math.random() * 0.15;
					enemy1_angle[free] = 3 * Math.PI / 2;
					enemy1_RV[free] = 0.0;
					enemy1_states[free] = State.ACTIVE;
					enemy1_nextShoot[free] = currentTime + 500;
					nextEnemy1 = currentTime + 500;
				}
			}
			
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
					player.setY(player.getY() - delta * player.getVY());
				}
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
					player.setY(player.getY() + delta * player.getVY());
				}
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
					player.setX(player.getX() - delta * player.getVX());
				}
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
					player.setX(player.getX() + delta * player.getVY());
				}
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					
					if(currentTime > player.getNextShot()){
						
						int free = findFreeIndex(playerProjectileManager.getStates());
												
						if(free < playerProjectileManager.getStates().length){

							double [] projectile_X = playerProjectileManager.getX();
							projectile_X[free] = player.getX();
							playerProjectileManager.setX(projectile_X);

							double [] projectile_Y = playerProjectileManager.getY();
							projectile_Y[free] = player.getY() - 2 * player.getRadius();
							playerProjectileManager.setY(projectile_Y);

							double [] projectile_VX = playerProjectileManager.getVX();
							projectile_VX[free] = 0.0;
							playerProjectileManager.setVX(projectile_VX);

							double [] projectile_VY = playerProjectileManager.getVY();
							projectile_VY[free] = -1.0;
							playerProjectileManager.setVY(projectile_VY);

							State [] projectile_states = playerProjectileManager.getStates();
							projectile_states[free] = State.ACTIVE;
							playerProjectileManager.setStates(projectile_states);

							player.setNextShot(currentTime + 100);
						}
					}	
				}
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */
			
			if(player.getX() < 0.0) player.setX(0.0);
			if(player.getX() >= GameLib.WIDTH) player.setX(GameLib.WIDTH - 1);
			if(player.getY() < 25.0) player.setY(25.0);
			if(player.getY() >= GameLib.HEIGHT) player.setY(GameLib.HEIGHT - 1);

			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo distante */
			
			GameLib.setColor(Color.DARK_GRAY);
			background2_count += background2_speed * delta;
			
			for(int i = 0; i < background2_X.length; i++){
				
				GameLib.fillRect(background2_X[i], (background2_Y[i] + background2_count) % GameLib.HEIGHT, 2, 2);
			}
			
			/* desenhando plano de fundo próximo */
			
			GameLib.setColor(Color.GRAY);
			firstBackground.setCount(firstBackground.getCount() + firstBackground.getSpeed() * delta);
			
			for(int i = 0; i < firstBackground.getX().length; i++){
				
				GameLib.fillRect(firstBackground.getX()[i], (firstBackground.getY()[i] + firstBackground.getCount()) % GameLib.HEIGHT, 3, 3);
			}
						
			/* desenhando player */
			
			if(player.getState() == State.EXPLODING){
				
				double alpha = (currentTime - player.getExplosion_start()) / (player.getExplosion_end() - player.getExplosion_start());
				GameLib.drawExplosion(player.getX(), player.getY(), alpha);
			}
			else{
				
				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(player.getX(), player.getY(), player.getRadius());
			}
				
			
			/* deenhando projeteis (player) */
			
			for(int i = 0; i < playerProjectileManager.getStates().length; i++){
				
				if(playerProjectileManager.getStates()[i] == State.ACTIVE){
					
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(playerProjectileManager.getX()[i], playerProjectileManager.getY()[i] - 5,
							playerProjectileManager.getX()[i], playerProjectileManager.getY()[i] + 5);
					GameLib.drawLine(playerProjectileManager.getX()[i] - 1, playerProjectileManager.getY()[i] - 3,
							playerProjectileManager.getX()[i] - 1, playerProjectileManager.getY()[i] + 3);
					GameLib.drawLine(playerProjectileManager.getX()[i] + 1, playerProjectileManager.getY()[i] - 3,
							playerProjectileManager.getX()[i] + 1, playerProjectileManager.getY()[i] + 3);
				}
			}
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == State.ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e_projectile_X[i], e_projectile_Y[i], e_projectile_radius);
				}
			}
			
			/* desenhando inimigos (tipo 1) */
			
			for(int i = 0; i < enemy1_states.length; i++){
				
				if(enemy1_states[i] == State.EXPLODING){
					
					double alpha = (currentTime - enemy1_explosion_start[i]) / (enemy1_explosion_end[i] - enemy1_explosion_start[i]);
					GameLib.drawExplosion(enemy1_X[i], enemy1_Y[i], alpha);
				}
				
				if(enemy1_states[i] == State.ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1_X[i], enemy1_Y[i], enemy1_radius);
				}
			}
			
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
