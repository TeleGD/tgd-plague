package games.preachOrDie1000HolyPlague.nodes.links;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.preachOrDie1000HolyPlague.nodes.Country;
import games.preachOrDie1000HolyPlague.nodes.Link;

public class EarthLink extends Link {

	public EarthLink(double weight, List<Country> countries) {
		super(weight, countries);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics context)  {
		context.setLineWidth(1f+(float)this.weight*2);
		context.setColor(Color.decode("#8e6f67"));
		for (Country c : countries) {
			context.drawLine(this.x, this.y, c.getX(), c.getY());
		}
	}

}
