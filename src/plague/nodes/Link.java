package plague.nodes;

import java.util.List;

import plague.Node;

public abstract class Link extends Node {

	private double weight;
	private List<Country> countries;

	public Link(double weight, List<Country> countries) {
		this.weight = weight;
		this.countries = countries;
	}

	public double getWeight() {
		return this.weight;
	}

	public List<Country> getCountries() {
		return this.countries;
	}

}
