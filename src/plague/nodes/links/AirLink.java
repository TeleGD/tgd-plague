package plague.nodes.links;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;
import plague.nodes.Country;
import plague.nodes.Link;

public class AirLink extends Link {
	
	private Image avion = AppLoader.loadPicture("/res/images/airplane-mode.png");

	public AirLink(double weight, List<Country> countries) {
		super(weight, countries);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context)  {
		for (Country c : countries) {
			avion.draw(c.getX(),c.getY());
		}
		// On n'affiche rien, il y a trop de liens aériens !
	}

}
