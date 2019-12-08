package plague.nodes.links;

import java.util.List;

import org.newdawn.slick.Color;
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
		context.setLineWidth(1f+(float)this.weight*2);
		context.setColor(Color.decode("#007ed8"));
		for (Country c : countries) {
			context.drawLine(this.x, this.y, c.getX(), c.getY());
		}
	}

}
