package plague;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;


public class Religion {

    private String name;
    private List<Skill> possessedSkills;
    private List<Skill> unlockedSkills;
    private List<Skill> lockedSkills;
    
    private int isolement;
    private int persuasion;
    private int cohesion;
    
    private Image imgLocked;
    private Image imgUnlocked;
    private Image imgPossessed;
    
    public Religion(String name) {
    	this.name = name;
    	
    	isolement = 0;
    	persuasion = 0;
    	cohesion = 0;
    	
    	possessedSkills = new ArrayList<>();
    	unlockedSkills = new ArrayList<>();
    	lockedSkills = new ArrayList<>();
    	
    	loadSkills();
    	//DEBUG 
    	//System.out.println(lockedSkills.toString());

		imgLocked = app.AppLoader.loadPicture("/res/images/icons/skill_bg_locked.png");
    	imgUnlocked = app.AppLoader.loadPicture("/res/images/icons/skill_bg_unlocked.png");
    	imgPossessed = app.AppLoader.loadPicture("/res/images/icons/skill_bg_possessed.png");
    }
    
    // passe un skill de unlocked à possessed si celui-ci était bien unlocked et le player a assez d'exp
    // retourne le nombre d'exp à enlever au player.
    public int activeSkill(Skill activatedSkill, int playerExp) {
    	unlockSuccessors(activatedSkill);
    	possessedSkills.add(activatedSkill);
    	unlockedSkills.remove(activatedSkill);
    	isolement += activatedSkill.getIsolement();
    	persuasion += activatedSkill.getPersuasion();
    	cohesion += activatedSkill.getCohesion();
    			
    	return(activatedSkill.getExperienceNeeded());
    }
    
    private void unlockSuccessors(Skill skill) {
		for(Integer id: skill.getSuccessors()) {
			for(int i=0; i<lockedSkills.size(); i++) {
				if(lockedSkills.get(i).getId() == id) {
					unlockedSkills.add(lockedSkills.get(i));
					lockedSkills.remove(i);
					i=lockedSkills.size();
				}
			}
		}
	}

	private void loadSkills() {
    	String load = AppLoader.loadData("/res/data/skills.json");
		try {
			JSONObject json = new JSONObject (load);

			JSONArray jsonSkills = (JSONArray) json.get("skills");
			for (int i = 0; i < jsonSkills.length(); i++) {
				JSONObject objSkill = jsonSkills.getJSONObject(i);
                Skill skill = new Skill();

                if(objSkill.get("id") != null) skill.setId((int) objSkill.get("id"));
                if(objSkill.get("x") != null) skill.setX((int) objSkill.get("x"));
                if(objSkill.get("y") != null) skill.setY((int) objSkill.get("y"));
                if(objSkill.get("isolement") != null) skill.setIsolement((int) objSkill.get("isolement"));
                if(objSkill.get("persuasion") != null) skill.setPersuasion((int) objSkill.get("persuasion"));
                if(objSkill.get("cohesion") != null) skill.setCohesion((int) objSkill.get("cohesion"));
                if(objSkill.get("experienceNeeded") != null) skill.setExperienceNeeded((int) objSkill.get("experienceNeeded"));
                if(objSkill.get("name") != null) skill.setName((String) objSkill.get("name"));
                if(objSkill.get("description") != null) skill.setDescription((String) objSkill.get("description"));
                if(objSkill.get("imgPath") != null) skill.setImage((String) objSkill.get("imgPath"));
                
                JSONArray jsonSuccessors = (JSONArray) objSkill.get("successors");
                for(int j = 0; j < jsonSuccessors.length(); j++)
                {
                	JSONObject innerObjSuccessor = jsonSuccessors.getJSONObject(j);
                    
                    if(innerObjSuccessor.get("id") != null) skill.addSuccessor((int) innerObjSuccessor.get("id"));
                }
                
                if(objSkill.get("unlocked") != null) {
                	if((int)objSkill.get("unlocked")==1) {
                		unlockedSkills.add(skill);
                		
                	} else {
                		lockedSkills.add(skill);
                	}
                } else {
                	lockedSkills.add(skill);
                }
            }
		} catch (JSONException e) {}
    }
	
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
    	
		for(Skill s: possessedSkills) {
			s.render(container, game, context, imgPossessed);
		}
		for(Skill s: lockedSkills) {
			s.render(container, game, context, imgLocked);
		}
		for(Skill s: unlockedSkills) {
			s.render(container, game, context, imgUnlocked);
		}
		
	}

	public int clickOnSkill(int mouseX, int mouseY, int playerExp) {
		Skill clickedSkill = null;
		for(Skill s: unlockedSkills) {
			if(s.contains(mouseX, mouseY) && playerExp>=s.getExperienceNeeded()) {
				clickedSkill = s;
				break;
			}
		}
		
		if(clickedSkill == null)
			return 0;
		else
			return activeSkill(clickedSkill,playerExp);
	}
}
