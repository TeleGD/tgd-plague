package games.preachOrDie1000HolyPlague;

import app.ui.Button;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import games.preachOrDie1000HolyPlague.nodes.Country;

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
		OKbutton.update(container, game, delta);
	}



	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		OKbutton.render(container,game, context);
	}

	public void mousePressed(int arg0, int x, int y) {
		selectedCountry = world.whichCountryPressed(x, y);
		if (selectedCountry != null) {
			switch (arg0) {
				case 0: { // Clic gauche
					double n = selectedCountry.getNormal().getCount();
					if (n >= 1) {
						System.out.println(n);
						double b = selectedCountry.getBeliever().getCount();
						selectedCountry.getNormal().setCount(n - 1);
						selectedCountry.getBeliever().setCount(b + 1);
						System.out.println("Croyant 0 placé !");
					}
					break;
				}
				case 1: { // Clic droit
					double b = selectedCountry.getBeliever().getCount();
					if (b >= 1) {
						System.out.println(b);
						double r = selectedCountry.getRecluse().getCount();
						selectedCountry.getBeliever().setCount(b - 1);
						selectedCountry.getRecluse().setCount(r + 1);
						System.out.println("Reclus 0 placé !");
					}
					break;
				}
			}
		}
	}

}
