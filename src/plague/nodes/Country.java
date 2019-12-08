package plague.nodes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import plague.Node;
import plague.World;

import java.util.ArrayList;
import java.util.List;

public class Country extends Node {
	private String name;
	private double latitude, longitude;
	private int x, y;

	private double normalToBelieverRate;
	private double normalToHereticRate;
	private double believerToRecluseRate;
	private double believerToHereticRate;

	private float size;
	private float dashesStart;

	private List<Link> links;

	public Country(String name, int population, double latitude, double longitude, World world) {
		super(population, 0, 0, 0);

		// Initialisation test //TODO : virer

		

		this.normalToBelieverRate = 0;
		this.normalToHereticRate = 0;
		this.believerToRecluseRate = 0;
		this.believerToHereticRate = 0;

		this.size = (float) Math.sqrt(Math.sqrt(population))/2;
		//this.size = (float) Math.pow(Math.log(population), 3);
		this.latitude = latitude;
		this.longitude = longitude;
		this.x = (int)(world.getWidth()*(longitude+180)/360);
		this.y = (int)(world.getHeight()*(-latitude+90)/180);
		this.links = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		this.isolatedPropagation();
		this.connectedPropagation();

		// DashesStart = point de départ du traçage des traitsetNewCount 0 et 1).
		// Il varie selon le carré de la crédulité pour une meilleure distinction des populations très crédules
		this.dashesStart = (float) ((this.dashesStart-(delta*this.getNormalToBelieverRate()*this.getNormalToBelieverRate()/5000))%1);
	}

	/**
	 * Calcule et applique la propagation de la croyance en interne à ce Country (sans considérer les Link)
	 */
	private void isolatedPropagation() {
		double population = this.getPopulation();

		double n0 = this.getNormal().getCount();
		double b0 = this.getBeliever().getCount();
		double r0 = this.getRecluse().getCount();
		double h0 = this.getHeretic().getCount();

		double ratioN = n0 / population;
		double ratioB = b0 / population;
		double ratioR = r0 / population;
		double ratioH = h0 / population;

		double n1 = n0 * (1 - this.normalToBelieverRate * ratioB - this.normalToHereticRate * ratioH);
		double b1 = b0 * (1 + this.normalToBelieverRate * ratioN - this.believerToRecluseRate * ratioR - this.believerToHereticRate * ratioH);
		double r1 = r0 * (1 + this.believerToRecluseRate * ratioB);
		double h1 = h0 * (1 + this.normalToHereticRate * ratioN + this.believerToHereticRate * ratioB);

		this.getNormal().setCount(n1);
		this.getBeliever().setCount(b1);
		this.getRecluse().setCount(r1);
		this.getHeretic().setCount(h1);
	}

	/**
	 * Calcule et applique la propagation de la croyance provoquée par les autres Country connectés via des Link
	 */
	private void connectedPropagation() {
		double n0 = this.getNormal().getCount();
		double b0 = this.getBeliever().getCount();
		double r0 = this.getRecluse().getCount();
		double h0 = this.getHeretic().getCount();

		// double numeratorN = 0;
		double numeratorB = 0;
		double numeratorR = 0;
		double numeratorH = 0;
		// double denominatorN = 0;
		double denominatorB = 0;
		double denominatorR = 0;
		double denominatorH = 0;

		for (Link link: links) { // Somme des flux de tous les links de ce Country
			// numeratorN += n0 * link.getWeight();
			numeratorB += b0 * link.getWeight();
			numeratorR += r0 * link.getWeight();
			numeratorH += h0 * link.getWeight();
			// denominatorN += link.getNormal().getCount() * link.getWeight();
			denominatorB += link.getBeliever().getCount() * link.getWeight();
			denominatorR += link.getRecluse().getCount() * link.getWeight();
			denominatorH += link.getHeretic().getCount() * link.getWeight();
		}

		// double ratioN = denominatorN != 0 ? numeratorN / denominatorN : 0;
		double ratioB = denominatorB != 0 ? numeratorB / denominatorB : 0;
		double ratioR = denominatorR != 0 ? numeratorR / denominatorR : 0;
		double ratioH = denominatorH != 0 ? numeratorH / denominatorH : 0;

		double n1 = n0 - this.normalToBelieverRate * ratioB * n0 - this.normalToHereticRate * ratioH * n0;
		double b1 = b0 + this.normalToBelieverRate * ratioB * n0 - this.believerToRecluseRate * ratioR * b0 - this.believerToHereticRate * ratioH * b0;
		double r1 = r0 + this.believerToRecluseRate * ratioB * b0;
		double h1 = h0 + this.normalToHereticRate * ratioH * n0 + this.believerToHereticRate * ratioH * b0;

		this.getNormal().setCount(n1);
		this.getBeliever().setCount(b1);
		this.getRecluse().setCount(r1);
		this.getHeretic().setCount(h1);
	}


	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		//Calcul des proportions de chaque partie de la population
		double population = this.getPopulation();
		double t0=0;
		double t1=360*this.getNormal().getCount()/population;
		double t2=360*(this.getNormal().getCount()+this.getBeliever().getCount())/population;
		double t3 = 360*(this.getNormal().getCount()+this.getBeliever().getCount()+this.getRecluse().getCount())/population;

		context.setColor(Color.decode("#EF8A26"));// Population normale
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t0, (float)t1);
		context.setColor(Color.decode("#AD5746"));// Population croyante
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t1, (float)t2);
		context.setColor(Color.decode("#6C2466"));// Population recluse
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t2, (float)t3);
		context.setColor(Color.decode("#8E6F67"));// Population hérétique
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t3, (float)t0);

		context.setColor(Color.white);
		context.setLineWidth(2);
		int n = 64;
		double theta1, theta2;
		for (int i = 0; i < n; i+=2) {
			theta1 = 2*Math.PI*(this.dashesStart+(double)i/n);
			theta2 = 2*Math.PI*(this.dashesStart+(double)(i+1)/n);
			context.drawLine((float)(x+size/2.25*Math.cos(theta1)), (float)(y-size/2.25*Math.sin(theta1)), (float)(x+size/2.25*Math.cos(theta2)), (float)(y-size/2.25*Math.sin(theta2)));;
		}
		double rate = this.getBeliever().getCount()/this.getPopulation();
		context.drawString(""+(int)(rate*100)+"%", x-12, y-8);
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

	public void addLink(Link link){
		links.add(link);
	}

	public double getNormalToBelieverRate() {
		return normalToBelieverRate;
	}

	public void setNormalToBelieverRate(double normalToBelieverRate) {
		this.normalToBelieverRate = normalToBelieverRate;
	}

	public double getNormalToHereticRate() {
		return normalToHereticRate;
	}

	public void setNormalToHereticRate(double normalToHereticRate) {
		this.normalToHereticRate = normalToHereticRate;
	}

	public double getBelieverToRecluseRate() {
		return believerToRecluseRate;
	}

	public void setBelieverToRecluseRate(double believerToRecluseRate) {
		this.believerToRecluseRate = believerToRecluseRate;
	}

	public double getBelieverToHereticRate() {
		return believerToHereticRate;
	}

	public void setBelieverToHereticRate(double believerToHereticRate) {
		this.believerToHereticRate = believerToHereticRate;
	}

}
