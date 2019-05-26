package smash.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class Player extends Entity{
	//les variables
	private Weapon arme;
	private int dommage;
	private boolean droite,gauche,droitegauche;

	public Player(int x, int y) {

	}


	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

	}

	public void keyPressed(int key, char c) {
		switch (key) {

		case Input.KEY_Q :
			gauche=true;
			droitegauche=true;
			break;

		case Input.KEY_D :
			droite=true;
			droitegauche=false;
			break;
		}
	}

	public void keyReleased(int key, char c) {
		switch (key) {

		case Input.KEY_Q :
			gauche=false;
			break;

		case Input.KEY_D :
			droite=false;
			break;
		}
	}

	public void move(int delta) {
		speedX=0;
		speedY=0;
		if ((gauche && !droite) || (gauche && droite && droitegauche)) {
			speedX=-speed;
		}
		if ((!gauche && droite) || (gauche && droite && !droitegauche)) {
			speedX=speed;
		}
		x+=delta*speedX;
	}

	public void checkForCollision() {

	}

	public void die() {

	}

}
