package plague;

public abstract class Population {

    private int count;
    
    public Population(int count) {
    	this.count = count;
    }
    
    public int getCount() {
    	return this.count;
    }
    
    public void setCount(int count) {
    	this.count = count;
    }

}
