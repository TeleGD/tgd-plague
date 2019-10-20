package plague.nodes;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import plague.Node;
import plague.World;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.GameContainer;

import plague.Node;
import plague.populations.*;

public class Country extends Node {
	private String name;
	private double latitude, longitude;
	private int x, y;
	private Normal normal;
	private Believer believer;
	private Recluse recluse;
	private Heretic heretic;
	private double[][] matrix;
	private float size;
	
	private ArrayList<Country> earthLinks;
	private ArrayList<Double> earthWeights;
	
	public Country(int population, double latitude, double longitude, World world) {
		this.normal = new Normal(population);
		this.believer = new Believer(0);
		this.heretic = new Heretic(0);
		this.recluse = new Recluse(0);
		this.matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
		this.size = (float) Math.pow(Math.log(population), 3);
		this.latitude = latitude;
		this.longitude = longitude;
		this.x = (int)(world.getWidth()*(longitude+180)/360);
		this.y = (int)(world.getHeight()*(-latitude+90)/180);
		System.out.println("Width: "+world.getWidth()+" ; Height: "+world.getHeight());
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
				outputVector[i] += matrix[i][j] * inputVector[j];
			}
		}
		this.believer.setCount(outputVector[0]);
		this.heretic.setCount(outputVector[1]);
		this.normal.setCount(outputVector[2]);
		this.recluse.setCount(outputVector[3]);
	}
	
	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.decode("#6E2368"));
		context.fillOval(x-size/64, y-size/64, size/32, size/32);
	}
}
