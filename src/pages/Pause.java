package pages;

import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppMenu;
import app.elements.MenuItem;

public class Pause extends AppMenu {

	int gameID;

	public Pause (int ID, int gameID) {
		super (ID);
		this.gameID = gameID;
	}

	@Override
	public void init (GameContainer container, StateBasedGame game) {
		super.initSize (container, game, 600, 400);
		super.init (container, game);
		this.setTitle ("Pause");
		this.setSubtitle ("Le temps de prendre un goûter");
		this.setMenu (Arrays.asList (new MenuItem [] {
			new MenuItem ("Retour") {
				public void itemSelected () {
					if (game.getState (Pause.this.gameID) instanceof plague.World) {
						((plague.World) game.getState (Pause.this.gameID)).setState (2);
					} else if (game.getState (Pause.this.gameID) instanceof smash.World) {
						((smash.World) game.getState (Pause.this.gameID)).setState (2);
					}
					game.enterState (Pause.this.gameID, new FadeOutTransition (), new FadeInTransition ());
				}
			},
			new MenuItem ("Abandon") {
				public void itemSelected () {
					if (game.getState (Pause.this.gameID) instanceof plague.World) {
						((plague.World) game.getState (Pause.this.gameID)).setState (0);
					} else if (game.getState (Pause.this.gameID) instanceof smash.World) {
						((smash.World) game.getState (Pause.this.gameID)).setState (0);
					}
					game.enterState (1, new FadeOutTransition (), new FadeInTransition ());
				}
			}
		}));
		this.setHint ("HAVE A SNACK");
	}

}
