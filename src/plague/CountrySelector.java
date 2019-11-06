package plague;

import app.ui.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import plague.nodes.Country;

public class CountrySelector{
	private Country selectedCountry;

	private World world;
	private Button OKbutton;

	public CountrySelector(World world, GameContainer container){
		this.selectedCountry = null;
		this.world =world;
		this.OKbutton = new Button("Place patient 0 in this Country",container, 500,500, 50,50 );//TODO : changer les coordonnées du bouton
		this.OKbutton.setBackgroundColor(Color.blue);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		try {
			OKbutton.update(container, game, delta);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}



	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		try {
			OKbutton.render(container,game, context);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public void mousePressed(int arg0, int x, int y) {
		if (arg0 == 0) { // Clic gauche
			if (OKbutton.hasFocus() && selectedCountry != null){   // Gère l'action au clic du bouton
				selectedCountry.persuade(0.01);
				System.out.println("Croyant 0 placé !");
			}
			else {  // Change selection de Country
				selectedCountry = world.whichCountryPressed(x, y);  // met à jour le nouveau Country sélectionné (NULL s'il le curseur n'est pas sur un Country)
			}
		}
	}

}
