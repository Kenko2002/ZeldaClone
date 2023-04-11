package zeldinhacopy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Swordhit extends Rectangle {
	
	public boolean flag_up=true;
	public boolean flag_down=true;
	public boolean flag_right=true;
	public boolean flag_left=true;
	Player player = Game.player;
	char dir='s';
	int spd=2;
	public int frames=0;
	public char father; //p = player, e=enemy
	public int i=0;
	int father_index;
	
	public Swordhit(int x,int y,char dir,char father,int father_index) {
		super(x+12,y+12,10,10);
		this.dir=dir;
		this.father=father;
		this.father_index=father_index;
	}
	
	public Swordhit(int x,int y,char dir,char father) {
		super(x+12,y+12,10,10);
		this.dir=dir;
		this.father=father;
	}
	
	public void tick() {
		
		frames+=1;
		if(frames==12 || (!(World.isFree(x,y)))) {		//mata a bullet se ela colidir em algo 
			this.kill();								//ou passar tempo demais.
		}
		
		
		if(dir=='s') {y+=spd*1;}
		if(dir=='w') {y+=spd*-1;}
		if(dir=='d') {x+=spd*1;}						//faz a espada andar em 4 direções.
		if(dir=='a') {x+=spd*-1;}
		
		
		
		if(this.intersects(player)&&father=='e'){
			player.kill();
		}
		
		for(i=0;i<Game.enemies.size();i++) {
			if(this.intersects(Game.enemies.get(i))&&father=='p'){
				Game.enemies.get(i).limparEspadadas();
				if(Game.enemies.get(i).morto==false) {Game.score++;}
				Game.enemies.get(i).morto=true;
				
			}
		}
		
		
	}
	
	public void kill() {
		if(father=='p') {
			Player.espadadas.remove(this);
			Player.espadadas_tamanho+=-1;
			return;}
		if(father=='e') {
			Game.enemies.get(father_index).espadadas.remove(this);
			Game.enemies.get(father_index).espadadas_tamanho+=-1;		
			return;}
	}
	
	public void render(Graphics g) {
		if(!Player.up&&!flag_up){spd+=-Player.spd; flag_up=true;} 
		if(Player.up && flag_up){spd+=Player.spd; flag_up=false;} 
		if(!Player.down&&!flag_down){spd+=-Player.spd; flag_down=true;} 
		if(Player.down && flag_down){spd+=Player.spd; flag_down=false;} 
		if(!Player.right&&!flag_right){spd+=-Player.spd; flag_right=true;} 
		if(Player.right && flag_right){spd+=Player.spd; flag_right=false;} 
		if(!Player.left&&!flag_left){spd+=-Player.spd; flag_left=true;} 
		if(Player.left && flag_left){spd+=Player.spd; flag_left=false;} 
		
		g.fillRect(x,y,width,height);
		
		if(dir=='w') 
		{g.drawImage(Spritesheet.sword_up[0],x-4,y-4,16,22,null);}
		if(dir=='s')
		{g.drawImage(Spritesheet.sword_down[0],x-1,y,16,26,null);}
		if(dir=='d')
		{g.drawImage(Spritesheet.sword_right[0],x-4,y,26,16,null);}
		if(dir=='a')
		{g.drawImage(Spritesheet.sword_left[0],x-10,y,26,16,null);}
	}
	
}
