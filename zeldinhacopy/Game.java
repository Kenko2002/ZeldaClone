package zeldinhacopy;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	
	public static int Width=800;							//Largura da janela
	public static int Height=600;							//Altura da Janela
	public static int Scale=1; 								//multiplica o tamanho da janela
	public static Player player;							//instancia o jogador.
	public World world;
	public static ArrayList<Enemy> enemies = new ArrayList<>();
	public int contador_inimigos=0;
	public int timer;
	public int i=0;
	public int aux_contador_inimigos;
	public int spawnerx;
	public int spawnery;
	static int score=0;
	public static boolean flagpintar=false;
	
	public Game(){
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(Width*Scale,Height*Scale));	//Cria o tamanho da window
		
		new Spritesheet();
		player= new Player(240-32,240-32); //pixel de spawn do personagem.
		enemies.add(new Enemy(240+32,240+32,contador_inimigos));
		contador_inimigos++;
		enemies.add(new Enemy(240+192,240+32,contador_inimigos));
		contador_inimigos++;
		world= new World();
	}
	
	
	public void tick() {			//logica do jogo, movimentação, etc.
		timer++;
		if(contador_inimigos==0) {contador_inimigos=contador_inimigos+1;}
		aux_contador_inimigos=contador_inimigos;
		if(timer==200 && player.morto==false) {
			for(i=0;i<aux_contador_inimigos;i++){			//adicionando inimigos gradualmente no mundo.
				spawnerx=new Random().nextInt(800);
				spawnery=new Random().nextInt(600);
				if(World.isFree(spawnerx, spawnery)){
					enemies.add(new Enemy(spawnerx,spawnery,contador_inimigos));contador_inimigos++;
				}
			}
			timer=0;
		}
		player.tick();
		for(int i=0;i<enemies.size();i++) {enemies.get(i).tick();}	//ticka os inimigos da lista.
	}
	
	
	
	
	
	
	public void render() {								//renderizador de graficos
		BufferStrategy bs = this.getBufferStrategy(); 	// otimiza renderização.
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics(); 				// um monte de receita de bolo, apenas copie sem questionar.
		
		g.setColor(new Color(0,135,13)); //seta o background pra verde(0,135,13)
		g.fillRect(0,0,Width*Scale,Height*Scale);  //cria um fundo do tamanho da tela.
		
		player.render(g);
		for(int i=0;i<enemies.size();i++) {enemies.get(i).render(g);}
		world.render(g);
		
		if (flagpintar==true) {
			Font fonte = new Font("Arial", Font.BOLD, 20);
			g.setFont(fonte);
			g.setColor(Color.BLACK);
			g.drawString("Você Morreu! Sua pontuação foi de: "+score, 100, 100);
		}
		
		bs.show();
	}
	
	
	
	
	
	public static void main(String[] args){
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.add(game);									//coloca o game no frame
		frame.setTitle("Zeldinha");							//Muda o Título da aplicação
		frame.pack();										//Empacota tudo
		frame.setLocationRelativeTo(null);					//centraliza na tela a janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //fecha o programa qd a janela fechar
		frame.setVisible(true);								//Deixa a janela visível
		new Thread(game).start();
	}
	
	
	
	@Override
	public void run(){
		while(true) {			//game loop
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			
		}
	}

	/*@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}			*/

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()  ==  KeyEvent.VK_D) {         //verifica se a tecla foi pressionada
			player.right=true;								// e se foi, aciona o sistema de movimento.
		}else if(e.getKeyCode()  ==  KeyEvent.VK_A){
			player.left=true;
		}else if(e.getKeyCode()  ==  KeyEvent.VK_W) {         //verifica se a tecla foi pressionada
			player.up=true;								// e se foi, aciona o sistema de movimento.
		}else if(e.getKeyCode()  ==  KeyEvent.VK_S){
			player.down=true;
		}
		if(e.getKeyCode()  ==  KeyEvent.VK_P){
			player.atirar=true;
		}
		if(e.getKeyCode()  ==  KeyEvent.VK_L){ //botao reset
			Game game = new Game();
			player.morto=false;
			enemies = new ArrayList<>();
			player.flagmorto=true;
			contador_inimigos=2;
			score=0;
			flagpintar=false;
			player.spd=2;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {	
		// TODO Auto-generated method stub				//Essa lógica é para detectar press de
		if(e.getKeyCode()  ==  KeyEvent.VK_D) {         //teclado para movimentar o 
			player.right=false;							//Rect do player na tela.
		}else if(e.getKeyCode()  ==  KeyEvent.VK_A){
			player.left=false;
		}
		if(e.getKeyCode()  ==  KeyEvent.VK_W) {         //verifica se a tecla foi solta
			player.up=false;								// e se foi, desliga o sistema de movimento.
		}else if(e.getKeyCode()  ==  KeyEvent.VK_S){
			player.down=false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}







	
	
	
}
