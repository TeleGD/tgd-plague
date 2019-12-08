package plague;

public abstract class Population {

	private double count;
//	private double newCount;

	public Population(double count) {
		this.setCount(count);
//		this.setNewCount(count);
	}

	public void setCount(double count) {
		this.count = count;
	}

	public double getCount() {
		return this.count;
	}

//	public void setNewCount(double newCount) {
//		this.newCount = newCount;
//	}

//	public double getNewCount() {
//		return newCount;
//	}
//
//	/**
//	 * Met à jour count avec la valeur de newCount
//	 * À utiliser lorsque tous les Country ont fini de calculer les nouvelles valeurs de leurs populations
//	 */
//	public void updateCount(){
//		this.setCount(newCount);
//	}
}
