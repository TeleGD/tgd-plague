package plague.nodes;

import java.util.ArrayList;
//import java.lang.Math.cos;
import java.lang.*;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;


import app.AppLoader;
import plague.Node;
import plague.World;
import plague.populations.Believer;
import plague.populations.Heretic;
import plague.populations.Normal;
import plague.populations.Recluse;

import java.util.ArrayList;

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
	private float dashesStart;
	private double rate;
	private Color color;

	private ArrayList<Country> earthLinks;
	private ArrayList<Double> earthWeights;

	public Country(String name, int population, double latitude, double longitude, World world) {
		this.normal = new Normal(population);
		this.believer = new Believer(0);
		this.recluse = new Recluse(0);
		this.heretic = new Heretic(0);
		this.matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
		this.size = (float) Math.sqrt(population)/60;
		//this.size = (float) Math.pow(Math.log(population), 3);
		this.latitude = latitude;
		this.longitude = longitude;
		this.x = (int)(world.getWidth()*(longitude+180)/360);
		this.y = (int)(world.getHeight()*(-latitude+90)/180);
	}

	public Normal getNormal() {
		return this.normal;
	}

	public void setNormal(Normal normal) {
		this.normal = normal;
	}

	public Believer getBeliever() {
		return this.believer;
	}

	public void setBeliever(Believer believer) {
		this.believer = believer;
	}

	public Recluse getRecluse() {
		return this.recluse;
	}

	public void setRecluse(Recluse recluse) {
		this.recluse = recluse;
	}

	public Heretic getHeretic() {
		return this.heretic;
	}

	public void setHeretic(Heretic heretic) {
		this.heretic = heretic;
	}

	private double getPopulation() {
		return this.normal.getCount() + this.believer.getCount() + this.recluse.getCount() + this.heretic.getCount();
	}

	private double getRate() {
		return this.rate;
	}

	private double getCredulity() {
		return this.matrix[2][2] - 1;
	}

	public Color getColor() {
		int red = (int)(110 + (240-110)*this.rate);
		int green = (int)(35 + (136-35)*this.rate);
		int blue = (int)(104 + (47-104)*this.rate);
		return new Color(65536*red + 256*green + blue);
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

		this.rate = this.believer.getCount()/this.getPopulation();
		this.color = this.getColor();

		// DashesStart = point de départ du traçage des traits (entre 0 et 1).
		// Il varie selon le carré de la crédulité pour une meilleure distinction des populations très crédules
		this.dashesStart = (float) ((this.dashesStart-(delta*this.getCredulity()*this.getCredulity()/5000))%1);
	}

	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		context.setColor(this.color);
		context.fillOval(x-size/2, y-size/2, size, size);
		context.setColor(Color.white);
		context.setLineWidth(2);
		int n = 64;
		double theta1, theta2;
		for (int i = 0; i < n; i+=2) {
			theta1 = 2*Math.PI*(this.dashesStart+(double)i/n);
			theta2 = 2*Math.PI*(this.dashesStart+(double)(i+1)/n);
			context.drawLine((float)(x+size/2.25*Math.cos(theta1)), (float)(y-size/2.25*Math.sin(theta1)), (float)(x+size/2.25*Math.cos(theta2)), (float)(y-size/2.25*Math.sin(theta2)));;
		}
		this.drawing(context);
		context.drawString(""+(int)(this.rate*100)+"%", x-12, y-8);
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

	public void drawing(Graphics context) {
		//Image img = AppLoader.loadPicture("/res/images/icons/country_bg_untouched.png").copy();
		double x = 50;
		double y = 50;
		context.setColor(Color.red);
		double tot = normal.getCount()+believer.getCount()+recluse.getCount()+heretic.getCount();
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				double x2 = (2*i/x-1);
				double y2 = (2*j/y-1);
				if (x2*x2 + y2*y2 < 1) {
					if (Math.atan2(y2, x2) > 0 && Math.atan2(y2, x2) < 1 /*Math.atan(normal.getCount()/tot)*/) {
						context.fillRect(i,j,1,1);
					}
				}
			}
			//context.fillRect((float)200,(float)200,(float)40,(float)40);
		//for ( int i=0 ; i<4 ; i++) {
			
			
		}
	}

	/**
	 * Indique si le curseur de la souris est dans le cercle de ce Country
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isCursorOnCountry(int x, int y) {
		return (Math.abs(x - this.x) <= size / 2);
	}


}
