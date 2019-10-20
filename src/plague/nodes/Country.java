package plague.nodes;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import plague.Node;
import plague.World;
import plague.populations.*;

public class Country extends Node {
	private String name;
	private double latitude, longitude;
	private int x, y;
	private Normal normal;
	private Believer believer;
	private Recluse recluse;
	private Heretic heretic;
	private float size;
	
	private ArrayList<Country> earthLinks;
	private ArrayList<Double> earthWeights;
	
	public Country(int population, double latitude, double longitude, World world) {
		this.normal = new Normal(population);
		this.size = (float) Math.pow(Math.log(population), 3);
		this.latitude = latitude;
		this.longitude = longitude;
		this.x = (int)(world.getWidth()*(longitude+180)/360);
		this.y = (int)(world.getHeight()*(-latitude+90)/180);
		System.out.println("Width: "+world.getWidth()+" ; Height: "+world.getHeight());
	}
	
	public void update (GameContainer container, StateBasedGame game, int delta) {
		/* TODO: des trucs */
		//this.size = (float) Math.log(this.normal.getCount() + this.believer.getCount() + this.recluse.getCount() + this.heretic.getCount());
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(Color.decode("#6E2368"));
		context.fillOval(x-size/64, y-size/64, size/32, size/32);
	}
	
}
