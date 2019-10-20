package pages;

import app.AppPage;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class NameChoice extends AppPage {

	public NameChoice(int ID) {
		super(ID);
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) {
		super.init(container, game);
		//TODO : ajouter TextField
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		super.update (container, game, delta);
		Input input = container.getInput ();
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

		context.drawString("TEST NameChoice", 100,100);
	}
}
