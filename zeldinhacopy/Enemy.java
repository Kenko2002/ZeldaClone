package zeldinhacopy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Enemy extends Rectangle{
	
	public int spd=2;
	public boolean atirar=false;
	public int right=0,up=0,down=0,left=0;
	public char last_direction_pressed='s';
	public int curAnimation=0;
	public int curFrames=0;		   //a cada 15 frames curAnimation é incrementado.
	public int targetFrames=15;    //desacelera a animação.
	public List<Swordhit> espadadas= new ArrayList<Swordhit>();
	public int espadadas_tamanho=0;
	public int index;
	public int cooldown_ataque=0;
	public boolean morto=false;
	
	public Enemy(int x,int y,int index) {
		super(x,y,32,32);
		this.index=index;
	}
	
	
	
	
	public void tick() {
		if(morto==false) {
			this.perseguirPlayer();
			
			boolean moved = false;
			
			if(right==1 && World.isFree(x+spd,y)) {				//colisão com cenário
				if(new Random().nextInt(100)>50) {
				last_direction_pressed='d';
				x+=spd;	
				moved=true;
				}
			}else if(left==1 && World.isFree(x-spd,y)) {
				if(new Random().nextInt(100)>50) {
				last_direction_pressed='a';
				x-=spd;
				moved=true;
				}
			}
			if(up==1 && World.isFree(x, y-spd)){
				if(new Random().nextInt(100)>50) {
				last_direction_pressed='w';
				y-=spd;
				moved=true;
				}
			}else if(down==1 && World.isFree(x, y+spd)){
				if(new Random().nextInt(100)>50) {
				last_direction_pressed='s';
				y+=spd;
				moved=true;
				}
			}
			
			
		
			
			if(moved) {
				curFrames++;
				if(curFrames==targetFrames) {
					curFrames=0;
					
					if (down==1) {						//gerencia a animação do player andando pra baixo.
						curAnimation++;
						if (curAnimation==Spritesheet.enemy_front.length) {
							curAnimation=0;}
					}
					if(up==1) {						//gerencia a animação do player andando pra cima.
						curAnimation++;
						if (curAnimation==Spritesheet.enemy_back.length) {
							curAnimation=0;}
					}
					if(right==1) {						//gerencia a animação do player andando pra cima.
						curAnimation++;
						if (curAnimation==Spritesheet.enemy_right.length) {
							curAnimation=0;}
					}
					if(left==1) {						//gerencia a animação do player andando pra cima.
						curAnimation++;
						if (curAnimation==Spritesheet.enemy_left.length) {
							curAnimation=0;}
					}
				}
			}
			
			//IA golpeando o jogador.
			cooldown_ataque++;
			if(cooldown_ataque>15) {
				cooldown_ataque=0;
				if(this.last_direction_pressed=='d') {
					if(Game.player.getCenterX()-this.getCenterX()<64&&Game.player.y==this.y) {atirar=true;}
					}
				if(this.last_direction_pressed=='a') {
					if(Game.player.getCenterX()-this.getCenterX()<0 && Game.player.getCenterX()-this.getCenterX()>-64 && Game.player.y==this.y) {atirar=true;}
					}
				if(this.last_direction_pressed=='s') {
					if(this.getCenterY()-Game.player.getCenterY()<0 && this.getCenterY()-Game.player.getCenterY()>-64 && Game.player.x==this.x) {atirar=true;}
					}
				if(this.last_direction_pressed=='w') {
					if(Game.player.getCenterY()-this.getCenterY()<0 && Game.player.getCenterY()-this.getCenterY()>-64 && Game.player.x==this.x) {atirar=true;}
					}
			}
			
			if(atirar) {					//movimenta as Bullets
				atirar=false; int i=0;
				if (espadadas_tamanho==0) {
					espadadas.add(new Swordhit(x,y,last_direction_pressed,'e',this.index));
					espadadas_tamanho+=1;}
				
			}
			for(int i=0;i<espadadas_tamanho;i++) {
				espadadas.get(i).tick();
			}
			
		}
	}
	
	
	public void render(Graphics g) {
		if(morto==false) {
			if(!(last_direction_pressed=='s')) {
			for(int i=0;i<espadadas_tamanho;i++) {espadadas.get(i).render(g);} }
			
			if(down==1) {g.drawImage(Spritesheet.enemy_front[curAnimation],x,y,32,32,null);}else {
			if(up==1) {g.drawImage(Spritesheet.enemy_back[curAnimation],x,y,32,32,null);}else {
			if(right==1) {g.drawImage(Spritesheet.enemy_right[curAnimation],x,y,32,32,null);}else {
			if(left==1) {g.drawImage(Spritesheet.enemy_left[curAnimation],x,y,32,32,null);}else {
				if(last_direction_pressed=='s') {
					g.drawImage(Spritesheet.enemy_front[0],x,y,32,32,null);}
				if(last_direction_pressed=='w') {
					g.drawImage(Spritesheet.enemy_back[0],x,y,32,32,null);}
				if(last_direction_pressed=='d') {
					g.drawImage(Spritesheet.enemy_right[0],x,y,32,32,null);}
				if(last_direction_pressed=='a') {
					g.drawImage(Spritesheet.enemy_left[0],x,y,32,32,null);}
			//g.drawImage(Spritesheet.player_front[curAnimation],x,y,32,32,null);
			
			}    //fecha chaves dos elses
			}
			}
			}	
			if(last_direction_pressed=='s') {
				for(int i=0;i<espadadas_tamanho;i++) {espadadas.get(i).render(g);} }
		}
	}
	public void limparEspadadas() {
		espadadas.clear();
		espadadas_tamanho=0;
	}
	
	
	public void perseguirPlayer() {
		Player jogador= Game.player;      //field view
		if((jogador.x-this.x>=0&&jogador.x-this.x<=60)||(this.x-jogador.x>=0&&this.x-jogador.x<=60)||(jogador.y-this.y>=0&&jogador.y-this.y<=60)||(this.y-jogador.y>=0&&this.y-jogador.y<=60)) {
			//Movimentação
			if (this.x>jogador.x) {left=1;right=0;}else {left=0;}
			if (this.x<jogador.x) {right=1;left=0;}else {right=0;}
			if (this.y>jogador.y) {up=1;down=0;}else {up=0;}
			if (this.y<jogador.y) {down=1;up=0;}else {down=0;}
		}else {up=0;down=0;left=0;right=0;} //para o movimento quando sai do field view.
	}
	
		
}
	


