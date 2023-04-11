package zeldinhacopy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blocks extends Rectangle{
	
	public String blockname;
	
	public Blocks(int x, int y,String blockname) {
		super(x,y,32,32);
		this.blockname=blockname;
	}
	

	
	public void render(Graphics g) {
		if(this.blockname=="dungeonwall") {
		g.drawImage(Spritesheet.dungeonwall,x,y,32,32,null);}   //renderiza meu dungeonwall
		if (this.blockname=="biggerblockwall") {
			g.drawImage(Spritesheet.biggerblockwall,x-32,y-32,64,64,null);}
	}
	
}
