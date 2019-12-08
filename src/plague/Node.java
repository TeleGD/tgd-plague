package plague;

import plague.populations.Believer;
import plague.populations.Heretic;
import plague.populations.Normal;
import plague.populations.Recluse;

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

}
