package games.preachOrDie1000HolyPlague;

public abstract class Population {

	private double count;

	public Population(double count) {
		this.setCount(count);
	}

	public void setCount(double count) {
		this.count = count;
	}

	public double getCount() {
		return this.count;
	}

}
