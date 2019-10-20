package plague.nodes;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;

import plague.Node;
import plague.populations.Believer;
import plague.populations.Heretic;
import plague.populations.Normal;
import plague.populations.Recluse;

public class Country extends Node {

	private Believer believer;
	private Heretic heretic;
	private Normal normal;
	private Recluse recluse;
	private double[][] matrix;

	public Country(int believerCount, int hereticCount, int normalCount, int recluseCount) {
		this.believer = new Believer(believerCount);
		this.heretic = new Heretic(hereticCount);
		this.normal = new Normal(normalCount);
		this.recluse = new Recluse(recluseCount);
		this.matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		double[] inputVector = new double[]{
			this.believer.getCount(),
			this.heretic.getCount(),
			this.normal.getCount(),
			this.recluse.getCount()
		};
		double[] outputVector = new double[]{
			0,
			0,
			0,
			0
		};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				outputVector[i] += this.matrix[i][j] * inputVector[j];
			}
		}
		this.believer.setCount(outputVector[0]);
		this.heretic.setCount(outputVector[1]);
		this.normal.setCount(outputVector[2]);
		this.recluse.setCount(outputVector[3]);
	}
	
	private void change(int i1, int j1, int i2, int j2, double x) {
		this.matrix[i1][j1] -= x;
		this.matrix[i2][j2] += x;
	}
	
	public void persuade(double x) {
		this.change(2, 2, 0, 2, x);
	}
	
	public void isolate(double x) {
		this.change(0, 0, 3, 0, x);
	}
	
	public void split(double x) {
		this.change(0, 0, 1, 0, x);
	}

}
