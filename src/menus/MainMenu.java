package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



public class MainMenu extends Menu{

	public static int ID = -3;	
	
	public MainMenu(){
		super.setTitrePrincipal("INSERER TITRE ICI");
		super.setTitreSecondaire("SOUS TITRE");
		
		super.setItems(plague.World1.GAME_NAME,smash.World2.GAME_NAME,"Scores", "Quitter");

		super.setEnableClignote(false);
		super.setCouleurClignote(Color.red);
		super.setTempsClignote(400);
	}
	
	@Override
	public void onOptionItemFocusedChanged(int position) {
		time=System.currentTimeMillis();
	}

	@Override
	public void onOptionItemSelected(int position) {
		switch (position) {
		case 0:
			plague.World1.reset();
			game.enterState(plague.World1.ID, new FadeOutTransition(),
			new FadeInTransition());
			break;
		case 1:
			smash.World2.reset();
			game.enterState(smash.World2.ID, new FadeOutTransition(),
			new FadeInTransition());
			break;
		case 2:
			System.out.println("exit");
			System.exit(0);
			break;
		}
	}
	
	@Override
	public int getID() {
		return ID;
	}

}