package zeldinhacopy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet {
	
	public static BufferedImage spritesheet;
	public static BufferedImage spritesheetblocks;
	
	public static BufferedImage[] player_front;
	public static BufferedImage[] player_back;
	public static BufferedImage[] player_right;
	public static BufferedImage[] player_left;
	
	public static BufferedImage[] sword_down;
	public static BufferedImage[] sword_up;
	public static BufferedImage[] sword_right;
	public static BufferedImage[] sword_left;
	
	public static BufferedImage[] enemy_front;
	public static BufferedImage[] enemy_back;
	public static BufferedImage[] enemy_right;
	public static BufferedImage[] enemy_left;

	public static BufferedImage dungeonwall;
	public static BufferedImage biggerblockwall;
	
	public Spritesheet() { //carrega o spritesheet.
		try {
			spritesheet=ImageIO.read(getClass().getResource("/zeldinhacopy/spritesheet.png"));
			spritesheetblocks=ImageIO.read(getClass().getResource("/zeldinhacopy/dungeonsheet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dungeonwall= spritesheetblocks.getSubimage(352,176,32,32);
		biggerblockwall=spritesheetblocks.getSubimage(336,0,48,48);
		player_front= new BufferedImage[2];
		player_front[0]=Spritesheet.getSprite(0,11,16,16);
		player_front[1]=Spritesheet.getSprite(16,11,16,16);
		player_back= new BufferedImage[2];
		player_back[0]=Spritesheet.getSprite(69, 11, 16, 16);
		player_back[1]=Spritesheet.getSprite(85, 11, 16, 16);
		player_right= new BufferedImage[2];
		player_right[0]=Spritesheet.getSprite(36, 11, 16, 16);
		player_right[1]=Spritesheet.getSprite(52, 11, 16, 16);
		player_left= new BufferedImage[2];
		player_left[0]=Spritesheet.getSprite(35, 28, 16, 16);
		player_left[1]=Spritesheet.getSprite(50, 28, 16, 16);
		sword_up= new BufferedImage[1];
		sword_up[0]=Spritesheet.getSprite(2, 154, 5, 12);
		sword_down= new BufferedImage[1];
		sword_down[0]=Spritesheet.getSprite(15, 147, 5, 12);
		sword_right= new BufferedImage[1];
		sword_right[0]=Spritesheet.getSprite(13, 160, 13, 5);
		sword_left= new BufferedImage[1];
		sword_left[0]=Spritesheet.getSprite(22, 149, 13, 5);
		
		enemy_front= new BufferedImage[2];
		enemy_front[0]=Spritesheet.getSprite(265, 290, 16, 16);
		enemy_front[1]=Spritesheet.getSprite(282, 290, 16, 16);
		enemy_right= new BufferedImage[2];
		enemy_right[0]=Spritesheet.getSprite(299, 290, 16, 16);
		enemy_right[1]=Spritesheet.getSprite(316, 290, 16, 16);
		enemy_left= new BufferedImage[2];
		enemy_left[0]=Spritesheet.getSprite(334, 272, 16, 16);
		enemy_left[1]=Spritesheet.getSprite(351, 272, 16, 16);
		enemy_back= new BufferedImage[2];
		enemy_back[0]=Spritesheet.getSprite(334, 290, 16, 16);
		enemy_back[1]=Spritesheet.getSprite(351, 290, 16, 16);
	}
	
	
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	
}
