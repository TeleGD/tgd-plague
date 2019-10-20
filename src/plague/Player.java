package plague;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Player {

    private int experience;
    private Religion religion;

    public Player(Religion religion) {
        this.religion = religion;
        experience = 0;
    }

	public void update(GameContainer container, StateBasedGame game, int delta) {
//		Input input = container.getInput();
//		
//		if(input.isMousePressed(0)) {
//			experience -= religion.clickOnSkill(input.getMouseX(),input.getMouseY(), experience);
//		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
//		religion.render(container, game, context);
//		
	}
}
