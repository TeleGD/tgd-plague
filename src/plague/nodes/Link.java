package plague.nodes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import plague.Node;

import java.util.List;

public abstract class Link extends Node {

	protected double weight;
	protected List<Country> countries;
	protected float x, y;

	public Link(double weight, List<Country> countries) {
		super(0, 0, 0, 0);
		this.weight = weight;
		this.countries = countries;
		for (Country country: this.countries) {
			country.addLink(this);
		}
		float x = 0, y = 0;
		for (Country country: this.countries) {
			x += country.getX();
			y += country.getY();
		}
		this.x = x / countries.size();
		this.y = y / countries.size();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		double n = 0;
		double b = 0;
		double r = 0;
		double h = 0;
		for (Country country: this.countries) {
			n += country.getNormal().getCount();
			b += country.getBeliever().getCount();
			r += country.getRecluse().getCount();
			h += country.getHeretic().getCount();
		}
		this.getNormal().setCount(n);
		this.getBeliever().setCount(b);
		this.getRecluse().setCount(r);
		this.getHeretic().setCount(h);
	}

	public double getWeight() {
		return this.weight;
	}

	public List<Country> getCountries() {
		return this.countries;
	}

}
