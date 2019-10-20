package plague;

import java.util.ArrayList;
import java.util.List;

public class Skill {
	
	private int id;
	
    private List<Integer> successors;
    
    private int isolement;
    private int persuasion;
    private int cohesion;
    
    private int experienceNeeded;
    
    private String name;
    private String description;
    
    public Skill() {
    	successors = new ArrayList<>();
    }

	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIsolement(int isolement) {
		this.isolement = isolement;
	}
	
	public void setCohesion(int cohesion) {
		this.cohesion  = cohesion;
	}
	
	public void setPersuasion(int persuasion) {
		this.persuasion = persuasion;
	}
	
	public void setExperienceNeeded(int exp) {
		this.experienceNeeded = exp;
	}
	
	public void addSuccessor(int id) {
		this.successors.add(new Integer(id));
	}

	public int getId() {
		return id;
	}

	public int getExperienceNeeded() {
		return experienceNeeded;
	}

	public int getIsolement() {
		return isolement;
	}

	public int getPersuasion() {
		return persuasion;
	}

	public int getCohesion() {
		return cohesion;
	}

	public List<Integer> getSuccessors() {
		return this.successors;
	}
}
