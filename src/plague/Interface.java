package plague;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Interface {

	private CountrySelector cs;
	private Player player;
	
	public Interface(CountrySelector cs, Player p) {
		this.cs = cs;
		this.player = p;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		float largeur = container.getWidth();
		float hauteur = container.getHeight();
		context.setColor(Color.black);
		//Affichage box de droite
		context.drawString("Ici la population mondiale.",largeur-270,hauteur-105);
		context.drawString("Ici le temps qui passe.",largeur-270,hauteur-55);
		//Affichage box de gauche
		context.drawString("Ici l'expérience",40,hauteur-105);
		context.drawString("Ici le bouton et accès aux compétences", 40, hauteur-55);
		//Affichage box centrale
		context.drawString("Ici la barre de propag",largeur/2-50,hauteur-55);
	}
	
	public void leftBox() {
		// Affichage des informations liees au joueur (experiences, competences,...)
		// TO DO
	}
	
	public void centralBox() {
		/* 
		 * Affichage des informations liee a un pays (clic pour selectionner ou deselectionner)
		 * N'affiche rien par defaut
		 */
		// TO DO
	}
	
	public void rightBox() {
		/*
		 * Affichage des informations liee a la religion et l'avancement global de la propagation
		 */
		// TO DO
	}
	
}
