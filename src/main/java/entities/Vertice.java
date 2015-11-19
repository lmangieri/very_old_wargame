package entities;

public class Vertice {

	private Node origin;
	private Node destiny;
	private int weight;
	
	public Vertice(Node origin, Node destiny,int weight) {
		this.origin = origin;
		this.destiny = destiny;
		this.weight = weight;
	}
	
	public Node getOrigin() {
		return origin;
	}

	public void setOrigin(Node origin) {
		this.origin = origin;
	}

	public Node getDestiny() {
		return destiny;
	}

	public void setDestiny(Node destiny) {
		this.destiny = destiny;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}	
	
}