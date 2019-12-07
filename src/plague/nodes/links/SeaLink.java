package plague.nodes.links;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import plague.nodes.Country;
import plague.nodes.Link;

public class SeaLink extends Link {

	public SeaLink(double weight, List<Country> countries) {
		super(weight, countries);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context)  {
		// On n'affiche rien, il y a trop de liens maritimes !
	}

}
