package pages;

import app.AppMenu;
import app.elements.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import plague.World;

import java.util.Arrays;

public class MenuNewGame extends AppMenu {


    public MenuNewGame(int ID) {
        super(ID);
    }


    public void init(GameContainer container, StateBasedGame game) {
        super.initSize (container, game, 600, 400);
        super.init (container, game);
        this.setTitle ("Paramètres d'une nouvelle partie");
        this.setSubtitle ("Comment voulez-vous les convertir");
        this.setMenu (Arrays.asList (new MenuItem[] {
                new MenuItem ("Difficulté 1") {
                    public void itemSelected () {
                        ((World) game.getState (3)).setState (2);
                        game.enterState (3, new FadeOutTransition(), new FadeInTransition());
                    }
                },
                new MenuItem ("Difficulté 2") {
                    public void itemSelected () {
                        ((World) game.getState (3)).setState (0);
                        game.enterState (1, new FadeOutTransition (), new FadeInTransition ());
                    }
                }
        }));
        this.setHint ("HAVE A SNACK");
    }
}