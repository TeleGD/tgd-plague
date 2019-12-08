package plague.nodes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import plague.Node;

import java.util.List;

public abstract class Link extends Node {

	protected double weight;
	protected List<Country> countries;
	protected float x, y;
	/**
	 * Somme des populations de tous les Country de countries pour chaque type de population
	 */
	private double[] flux;

	public Link(double weight, List<Country> countries) {
		this.weight = weight;
		this.countries = countries;
		for(Country c : countries){
			c.addLink(this);
		}
		flux = new double[]{
				0,
				0,
				0,
				0
		};
		this.updatePosition();	
		
	}

	public double getWeight() {
		return this.weight;
	}

	public List<Country> getCountries() {
		return this.countries;
	}

	public void updateFlux(){
		flux = new double[]{
				0,
				0,
				0,
				0
		};
		for (Country c : countries) {
			flux[0] += c.getNormal().getCount();
			flux[1] += c.getBeliever().getCount();
			flux[2] += c.getRecluse().getCount();
			flux[3] += c.getHeretic().getCount();
		}
	}

	public double[] getFlux() {
		return flux;
	}
	
	public void updatePosition() {
		float xSum = 0, ySum = 0;
		for (Country c : countries) {
			xSum += c.getX();
			ySum += c.getY();
		}
		this.x = xSum / countries.size();
		this.y = ySum / countries.size();
	}
	
	public abstract void render(GameContainer container, StateBasedGame game, Graphics context);
}
