package pages;

import app.AppPage;
import plague.TextField;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class NameChoice extends AppPage {

	private String text;
	private TextField textField;
	
	public NameChoice(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.init(container, game);
		this.textField = new TextField(100,100,100,100,100,100,5);
		//TODO : ajouter TextField
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update (container, game, delta);
		text = textField.getText();
		Input input = container.getInput ();
		textField.update(container,game,delta);
		if (input.isKeyDown (Input.KEY_ESCAPE)) {
			container.exit ();
		} else if (input.isKeyDown (Input.KEY_ENTER)) {
			
			//TODO : exécuter une méthode sur World pour passer le Player/Religion
			game.enterState (3, new FadeOutTransition(), new FadeInTransition());
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		super.render(container, game, context);
		context.drawString("TEST NameChoice : "+text, 100,100);
		textField.render(container, game, context);
	}
	
	public void keyPressed(int key,char value) {
		textField.keyPressed(key,value);
	}
}
