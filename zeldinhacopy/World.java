package zeldinhacopy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;


public class World {
	
	
	
	public static List<Blocks> blocos = new ArrayList<Blocks>();
	
	public World() {   //construtor
		for(int xx=0;xx<32;xx++) {
			blocos.add(new Blocks(xx*32,0,"dungeonwall"));	  //instancia blocos na parede superior
			blocos.add(new Blocks(0,xx*32,"dungeonwall"));	  //instancia blocos na parede esquerda
			blocos.add(new Blocks((800)-32,xx*32,"dungeonwall"));;   //instancia blocos na parede direita
			blocos.add(new Blocks(xx*32,(600)-32,"dungeonwall"));;   //instancia blocos na parede inferior
		}
		blocos.add(new Blocks(396,400-96,"biggerblockwall"));
	}
	
	
	public static boolean isFree(int x, int y) {
		int wid=0;
		int heig=0; //vai gerenciar o tamanho dos blocos renderizados
		
		for(int i=0;i<blocos.size();i++) {
			Blocks blocoAtual= blocos.get(i);
			
			if(blocoAtual.blockname=="dungeonwall") {
				wid=32;
				heig=32;}							//cada bloco tem um tamanho próprio.
			if (blocoAtual.blockname=="biggerblockwall") {
				wid=64;
				heig=64;}
			
			if (blocoAtual.intersects(new Rectangle(x,y,wid,heig))){
				return false;
			}
		}
		return true;
	}

	
	public void render(Graphics g) {
		for(int i=0;i<blocos.size();i++) {
			blocos.get(i).render(g);
		}
	}
	
}
