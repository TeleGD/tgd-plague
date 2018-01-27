package smash.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class Player extends Entity{
	//les variables
	private Weapon arme;
	private int dommage;
	private int direction; //0 pour gauche, 1 pour droite
	
	public Player(int x, int y) {
		
	}
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}
}
