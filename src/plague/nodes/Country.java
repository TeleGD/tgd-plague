package plague.nodes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import plague.Node;
import plague.World;
import plague.populations.Believer;
import plague.populations.Heretic;
import plague.populations.Normal;
import plague.populations.Recluse;

import java.util.ArrayList;

//import java.lang.Math.cos;

public class Country extends Node {
	private String name;
	private double latitude, longitude;
	private int x, y;
	private Normal normal;
	private Believer believer;
	private Recluse recluse;
	private Heretic heretic;
	private double[][] internal_evolution_matrix;
	private double[][] external_evolution_matrix;
	private float size;
	private float dashesStart;
	private double rate;
	private Color color;

	private ArrayList<Link> links;

	public Country(String name, int population, double latitude, double longitude, World world) {
		this.normal = new Normal(population);
		this.believer = new Believer(0);
		this.recluse = new Recluse(0);
		this.heretic = new Heretic(0);
		this.internal_evolution_matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
		this.external_evolution_matrix = new double[][]{
			new double[]{1, 0, 0, 0},
			new double[]{0, 1, 0, 0},
			new double[]{0, 0, 1, 0},
			new double[]{0, 0, 0, 1}
		};
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
		return this.internal_evolution_matrix[1][1] - 1;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public Color getColor() {
		int red = (int)(110 + (240-110)*this.rate);
		int green = (int)(35 + (136-35)*this.rate);
		int blue = (int)(104 + (47-104)*this.rate);
		return new Color(65536*red + 256*green + blue);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		isolatedPropagation();
//		connectedPropagation();

		this.rate = this.believer.getCount()/this.getPopulation();
		this.color = this.getColor();

		// DashesStart = point de départ du traçage des traitsetNewCount 0 et 1).
		// Il varie selon le carré de la crédulité pour une meilleure distinction des populations très crédules
		this.dashesStart = (float) ((this.dashesStart-(delta*this.getCredulity()*this.getCredulity()/5000))%1);
	}

	/**
	 * Calcule et applique la propagation de la croyance en interne à ce Country (sans considérer les Link)
	 */
	public void isolatedPropagation(){
		double[] inputVector = new double[]{
				this.normal.getCount(),
				this.believer.getCount(),
				this.recluse.getCount(),
				this.heretic.getCount()
		};
		double[] outputVector = new double[]{
				0,
				0,
				0,
				0
		};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				outputVector[i] += this.internal_evolution_matrix[i][j] * inputVector[j];
			}
		}
		this.normal.setNewCount(outputVector[0]);
		this.believer.setNewCount(outputVector[1]);
		this.recluse.setNewCount(outputVector[2]);
		this.heretic.setNewCount(outputVector[3]);
	}

	/**
	 * Calcule et applique la propagation de la croyance provoquée par les autres Country connectés via des Link
	 */
	public void connectedPropagation(){
		double[] inputVector = new double[]{
				this.normal.getCount(),
				this.believer.getCount(),
				this.recluse.getCount(),
				this.heretic.getCount()
		};
		double[] outputVector = new double[]{
				0,
				0,
				0,
				0
		};

		double[] fluxLinks = new double[]{
				0,
				0,
				0,
				0
		};

		for (Link link : links){    // Somme des flux de tous les links de ce Country
			double[] fluxOfLink = link.getFlux();
			for (int i = 0; i < 4 ; i++) {
				fluxLinks[i] += fluxOfLink[i] - inputVector[i] * link.getWeight(); // On retire du flux les populations qui venaient de ce Country
			}
		}

		this.external_evolution_matrix = new double[][]{
				new double[]{1, 0, 0, 0},
				new double[]{0, 1, 0, 0},
				new double[]{0, 0, 1, 0},
				new double[]{0, 0, 0, 1}
		};  // remise à zéro de la matrice d'évolution

		persuade_external(fluxLinks[1]);
		isolate_external(fluxLinks[2]);
		split_external(fluxLinks[3]);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				outputVector[i] += this.external_evolution_matrix[i][j] * inputVector[j];
			}
		}
		this.normal.setNewCount(outputVector[0]);
		this.believer.setNewCount(outputVector[1]);
		this.recluse.setNewCount(outputVector[2]);
		this.heretic.setNewCount(outputVector[3]);
	}


	public void render (GameContainer container, StateBasedGame game, Graphics context) {
		//Calcul des proportions de chaque partie de la population
		int sum = (int)(normal.getCount()+believer.getCount()+recluse.getCount()+heretic.getCount());
		double t0=0;
		double t1=360*normal.getCount()/sum;
		double t2=360*(normal.getCount()+believer.getCount())/sum;
		double t3 = 360*(normal.getCount()+believer.getCount()+recluse.getCount())/sum;

		context.setColor(Color.decode("#EF8A26"));// Population normale
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t0, (float)t1);
		context.setColor(Color.decode("#AD5746"));// Population croyante
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t1, (float)t2);
		context.setColor(Color.decode("#6C2466"));// Population recluse
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t2, (float)t3);
		context.setColor(Color.decode("#8E6F67"));// Population hérétique
		context.fillArc(this.x-this.size/2, this.y-this.size/2, this.size, this.size, (float)t3, (float)t0);

		//context.setColor(this.color);
		//context.fillOval(x-size/2, y-size/2, size, size);
		context.setColor(Color.white);
		context.setLineWidth(2);
		int n = 64;
		double theta1, theta2;
		for (int i = 0; i < n; i+=2) {
			theta1 = 2*Math.PI*(this.dashesStart+(double)i/n);
			theta2 = 2*Math.PI*(this.dashesStart+(double)(i+1)/n);
			context.drawLine((float)(x+size/2.25*Math.cos(theta1)), (float)(y-size/2.25*Math.sin(theta1)), (float)(x+size/2.25*Math.cos(theta2)), (float)(y-size/2.25*Math.sin(theta2)));;
		}
		//this.drawing(context);
		context.drawString(""+(int)(this.rate*100)+"%", x-12, y-8);
	}

	/**
	 * Met à jour les count des population
	 * À utiliser lorsque tous les Country ont fini de calculer les nouvelles valeurs de leurs populations
	 */
	public void updateCount(){
		normal.updateCount();
		believer.updateCount();
		heretic.updateCount();
		recluse.updateCount();
	}

	private void change_internal(int originIndex, int destIndex, double x) {
		this.internal_evolution_matrix[originIndex][originIndex] -= x;
		this.internal_evolution_matrix[destIndex][originIndex] += x;
	}

	private void change_external(int originIndex, int destIndex, double x) {
		this.external_evolution_matrix[originIndex][originIndex] -= x;
		this.external_evolution_matrix[destIndex][originIndex] += x;
	}

	public void persuade(double x) {
		this.change_internal(0, 1, x);
	}

	public void isolate(double x) {
		this.change_internal(1, 2, x);
	}

	public void split(double x) {
		this.change_internal(2, 3, x);
	}

	public void persuade_external(double x) {
		this.change_external(0, 1, x);
	}

	public void isolate_external(double x) {
		this.change_external(1, 2, x);
	}

	public void split_external(double x) {
		this.change_external(2, 3, x);
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


}
