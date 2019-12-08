package plague;

import app.AppLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import plague.nodes.Country;
import plague.nodes.Link;
import plague.nodes.links.AirLink;
import plague.nodes.links.EarthLink;
import plague.nodes.links.SeaLink;

import java.util.ArrayList;
import java.util.List;

public class World extends BasicGameState {

	private int ID;
	private int state;
	private int width;
	private int height;
	private Player player;
	private List<Country> countries;
	private List<Link> links;
	private float aspectRatio;
	private CountrySelector countrySelector;
	private static Music theme;
	private Image sprite = AppLoader.loadPicture("/res/images/equirectangular.png");
	private Image background;
	private Interface visuel;
	static {
		try {
			theme = new Music("res/musics/Bio_UnitMetre_-_06_-_Resonance.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
		this.countries = new ArrayList<Country>();
		this.links = new ArrayList<Link>();
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		this.width = container.getWidth();
		this.height = container.getHeight();
		this.aspectRatio = Math.min(container.getWidth() / 1280f, container.getHeight() / 720f);
		background = sprite.getScaledCopy(this.width, this.height);
		//countries.add(new Country("Pays 1", (int) 60e6, 41, 10, this));
		//countries.add(new Country("Pays 2", (int) 1e6, -9, -120, this));
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		} else if (input.isKeyDown(Input.KEY_K)){
			((SkillPage) game.getState (5)).setPlayer(player);
			//this.setState(1);
			game.enterState(5,new FadeOutTransition(), new FadeInTransition());
		}

		for (Link link : links){    // Mise à jour des flux des Link pour préparer le calcul de propagation entre Country
			link.updateFlux();
		}
		for (Country c : countries) {   // Première passe : contient notamment le calcul des newCount des populations
			c.update(container, game, delta);
		}
//		for (Country c : countries) {   // Deuxième passe : met à jour les count des populations avec leur newCount
//			c.updateCount();
//		}
		if(countrySelector!=null){
			countrySelector.update(container, game, delta);
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setColor(Color.white);
		context.fillRect(0, 0, width, height);
		context.drawImage(background, (float)0, (float)0);
		context.setColor(Color.decode("#4C4C4C"));
		String title = "Propagation de la religion "+this.player.getReligion().getName();
		context.drawString (title, (width-context.getFont().getWidth(title))/2, 24);
		visuel.render(container, game, context);

		for (Link l : links) {
			if(! (l instanceof AirLink)) {
				l.render(container, game, context);
			}
		}
		
		for (Country c : countries) {
			c.render(container, game, context);
		}
		for (Link l : links) {
			if(l instanceof AirLink) {
				l.render(container, game, context);
			}
		}
		
		if (countrySelector !=null){
			countrySelector.render(container, game, context);
		}
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.theme.loop(1, (float) 0.4);
		this.loadGraph();
		this.countrySelector = new CountrySelector(this, container);   // Selecteur de Country de patient 0. //TODO : changer lorsqu'on utilisera plusieurs selecteurs ayant des effets différents
		//		this.player = new Player();
		this.visuel = new Interface(countrySelector,null);
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		this.theme.pause();
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		this.theme.resume();
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		this.theme.stop();
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	//TODO : Ajouter la selection du Country du croyant 0 (Country de départ)
	@Override
	public void mousePressed(int arg0, int x, int y) {
		if (countrySelector!=null){
			countrySelector.mousePressed(arg0, x, y);
		}
	}

	public Country whichCountryPressed(int x, int y){
		for (Country country: countries) {
			if (country.isCursorOnCountry(x, y)){
				return country;
			}
		}
		return null;
	}

	private void loadGraph() {
    	String load = AppLoader.loadData("/data/world.json");
		try {
			JSONObject json = new JSONObject(load);
			JSONArray nodes = json.getJSONArray("nodes");
			for (int i = 0, li = nodes.length(); i < li; i++) {
				JSONObject node = nodes.getJSONObject(i);
				String name = node.getString("name");
				int population = node.getInt("population");
				double latitude = node.getDouble("latitude");
				double longitude = node.getDouble("longitude");
				this.countries.add(new Country(name, population, latitude, longitude, this));
			}
			JSONArray links = json.getJSONArray("links");
			for (int i = 0, li = links.length(); i < li; i++) {
				JSONObject link = links.getJSONObject(i);
				double weight = link.getDouble("weight");
				JSONArray countryIDs = link.getJSONArray("countries");
				List<Country> countries = new ArrayList<Country>();
				for (int j = 0, lj = countryIDs.length(); j < lj; j++) {
					int countryID = countryIDs.getInt(j);
					Country country = this.countries.get(countryID);
					countries.add(country);
				}
				String type = link.getString("type");
				switch (type) {
				case "earth" :  this.links.add(new EarthLink(weight, countries)); break;
				case "air" : this.links.add(new AirLink(weight,countries)); break;
				case "sea" : this.links.add(new SeaLink(weight,countries)); break;
				default : break;
				}
			}
		} catch (JSONException exception) {}
	}
}
