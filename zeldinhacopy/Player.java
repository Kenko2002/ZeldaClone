package zeldinhacopy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Player extends Rectangle{
	
	public static int spd=2;
	public boolean atirar=false;
	public static boolean right,up,down,left;
	public static char last_direction_pressed='s';
	public int curAnimation=0;
	public int curFrames=0;		   //a cada 15 frames curAnimation é incrementado.
	public int targetFrames=15;    //desacelera a animação.
	public static List<Swordhit> espadadas= new ArrayList<Swordhit>();
	public static int espadadas_tamanho=0;
	public boolean morto=false;
	public boolean flagmorto=true;
	
	
	public Player(int x,int y) {
		super(x,y,32,32);
		
	}
	
	public void tick() {
		boolean moved=false;			//movimenta o jogador
		
		if(right && World.isFree(x+spd,y)) {				//quando ele apertar a tecla.
			last_direction_pressed='d';
			x+=spd;	
			moved=true;
		}else if(left && World.isFree(x-spd,y)) {
			last_direction_pressed='a';
			x-=spd;
			moved=true;
		}
		
		if(up && World.isFree(x, y-spd)){
			last_direction_pressed='w';
			y-=spd;
			moved=true;
		}else if(down && World.isFree(x, y+spd)){
			last_direction_pressed='s';
			y+=spd;
			moved=true;
		}
		
		if(moved) {
			curFrames++;
			if(curFrames==targetFrames) {
				curFrames=0;
				
				if (down) {						//gerencia a animação do player andando pra baixo.
					curAnimation++;
					if (curAnimation==Spritesheet.player_front.length) {
						curAnimation=0;}
				}
				if(up) {						//gerencia a animação do player andando pra cima.
					curAnimation++;
					if (curAnimation==Spritesheet.player_back.length) {
						curAnimation=0;}
				}
				if(right) {						//gerencia a animação do player andando pra cima.
					curAnimation++;
					if (curAnimation==Spritesheet.player_right.length) {
						curAnimation=0;}
				}
				if(left) {						//gerencia a animação do player andando pra cima.
					curAnimation++;
					if (curAnimation==Spritesheet.player_left.length) {
						curAnimation=0;}
				}
			}
		}
		if(atirar&&flagmorto==true) {					//movimenta as Bullets
			atirar=false;
			if (espadadas_tamanho==0) {
				espadadas.add(new Swordhit(x,y,last_direction_pressed,'p'));
				espadadas_tamanho+=1;}
		}
		for(int i=0;i<espadadas_tamanho;i++) {
			espadadas.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		if(morto==false) {
			if(!(last_direction_pressed=='s')) {
			for(int i=0;i<espadadas_tamanho;i++) {espadadas.get(i).render(g);} }
			
			if(down) {g.drawImage(Spritesheet.player_front[curAnimation],x,y,32,32,null);}else {
			if(up) {g.drawImage(Spritesheet.player_back[curAnimation],x,y,32,32,null);}else {
			if(right) {g.drawImage(Spritesheet.player_right[curAnimation],x,y,32,32,null);}else {
			if(left) {g.drawImage(Spritesheet.player_left[curAnimation],x,y,32,32,null);}else {
				if(last_direction_pressed=='s') {
					g.drawImage(Spritesheet.player_front[0],x,y,32,32,null);}
				if(last_direction_pressed=='w') {
					g.drawImage(Spritesheet.player_back[0],x,y,32,32,null);}
				if(last_direction_pressed=='d') {
					g.drawImage(Spritesheet.player_right[0],x,y,32,32,null);}
				if(last_direction_pressed=='a') {
					g.drawImage(Spritesheet.player_left[0],x,y,32,32,null);}
			//g.drawImage(Spritesheet.player_front[curAnimation],x,y,32,32,null);
			
			}    //fecha chaves dos elses
			}
			}
			}	
			if(last_direction_pressed=='s') {
				for(int i=0;i<espadadas_tamanho;i++) {espadadas.get(i).render(g);} }
		}
		else{
			if(flagmorto==true) {
				flagmorto=false;
				Game.flagpintar=true;
			}
		}
	}
	
	public void kill() {
		morto=true;
		this.spd=0;
	}
}
