package plague;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.AppLoader;


public class Religion {

    private String name;
    private List<Skill> possessedSkills;
    private List<Skill> unlockedSkills;
    private List<Skill> lockedSkills;
    
    private int isolement;
    private int persuasion;
    private int cohesion;
    
    public Religion(String name) {
    	this.name = name;
    	
    	isolement = 0;
    	persuasion = 0;
    	cohesion = 0;
    	
    	possessedSkills = new ArrayList<>();
    	unlockedSkills = new ArrayList<>();
    	lockedSkills = new ArrayList<>();
    	
    	loadSkills();
    }
    
    // passe un skill de unlocked à possessed si celui-ci était bien unlocked et le player a assez d'exp
    // retourne le nombre d'exp à enlever au player.
    public int activeSkill(int id, int playerExp) {
    	for(int i=0; i<unlockedSkills.size(); i++) {
    		if(unlockedSkills.get(i).getId() == id && unlockedSkills.get(i).getExperienceNeeded()<=playerExp) {
    			Skill activatedSkill = unlockedSkills.get(i);
    			unlockSuccessors(activatedSkill);
    			possessedSkills.add(activatedSkill);
    			unlockedSkills.remove(activatedSkill);
    			isolement += activatedSkill.getIsolement();
    			persuasion += activatedSkill.getPersuasion();
    			cohesion += activatedSkill.getCohesion();
    			
    			return(activatedSkill.getExperienceNeeded());
    		}
    	}
    		
    	return 0;
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
            Iterator i = ((List<Skill>) jsonSkills).iterator();
            while (i.hasNext()) {
                JSONObject objSkill = (JSONObject) i.next();

                Skill skill = new Skill();

                if(objSkill.get("id") != null) skill.setId((int)(long) objSkill.get("id"));
                if(objSkill.get("isolement") != null) skill.setIsolement((int)(long) objSkill.get("isolement"));
                if(objSkill.get("persuasion") != null) skill.setPersuasion((int)(long) objSkill.get("persuasion"));
                if(objSkill.get("cohesion") != null) skill.setCohesion((int)(long) objSkill.get("cohesion"));
                if(objSkill.get("experienceNeeded") != null) skill.setExperienceNeeded((int)(long) objSkill.get("experienceNeeded"));
                if(objSkill.get("name") != null) skill.setName((String) objSkill.get("name"));
                if(objSkill.get("description") != null) skill.setDescription((String) objSkill.get("description"));
                
                JSONArray jsonSuccessors = (JSONArray) objSkill.get("successors");
                Iterator j = ((List<Skill>) jsonSuccessors).iterator();
                while (j.hasNext()) {
                    JSONObject innerObjSuccessor = (JSONObject) j.next();
                    
                    if(innerObjSuccessor.get("id") != null) skill.addSuccessor((int)(long) innerObjSuccessor.get("id"));
                }
                
                if(objSkill.get("unlocked") != null) {
                	if((int)(long)objSkill.get("unlocked")==1) {
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
}
