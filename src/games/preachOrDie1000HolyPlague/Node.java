package games.preachOrDie1000HolyPlague;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import games.preachOrDie1000HolyPlague.populations.Believer;
import games.preachOrDie1000HolyPlague.populations.Heretic;
import games.preachOrDie1000HolyPlague.populations.Normal;
import games.preachOrDie1000HolyPlague.populations.Recluse;

public abstract class Node {

	private Normal normal;
	private Believer believer;
	private Recluse recluse;
	private Heretic heretic;

	public Node(int n, int b, int r, int h) {
		this.normal = new Normal(n);
		this.believer = new Believer(b);
		this.recluse = new Recluse(r);
		this.heretic = new Heretic(h);
	}

	public Normal getNormal() {
		return this.normal;
	}

	public Believer getBeliever() {
		return this.believer;
	}

	public Recluse getRecluse() {
		return this.recluse;
	}

	public Heretic getHeretic() {
		return this.heretic;
	}

	public double getPopulation() {
		return this.normal.getCount() + this.believer.getCount() + this.recluse.getCount() + this.heretic.getCount();
	}

	public abstract void update(GameContainer container, StateBasedGame game, int delta);

	public abstract void render(GameContainer container, StateBasedGame game, Graphics context);

}
