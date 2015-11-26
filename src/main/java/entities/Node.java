package entities;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

import enums.ColorEnum;

public class Node {

	private String name;
	private List<Vertice> vertices;
	private int numberOfPieces;
	private String continentName;
	private Player player;
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final Node other = (Node)obj;
		
		return new EqualsBuilder().append(name,other.name).isEquals();
	}
	
	@Override
	public int hashCode() {
		return name.length();
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int x;
	public int y;
	
	public Node(String name,int x,int y, String continentName) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.continentName = continentName;
		this.numberOfPieces = 1;
	}
	
	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}
	
	public void addNumberOfPieces(int number) {
		this.numberOfPieces = this.numberOfPieces + number;
	}
	
	public void removeNumberOfPieces(int number) {
		this.numberOfPieces = this.numberOfPieces - number;
		if(this.numberOfPieces  < 0) {
			throw new RuntimeException("Invalid number of pieces");
		}
	}
	
	public int getNumberOfPieces() {
		return this.numberOfPieces;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}
	
	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	
	public boolean isLocked() {
		ColorEnum color = getPlayer().getColorEnum();
		for (Vertice v : getVertices()) {
			if (!color.equals(v.getDestiny().getPlayer().getColorEnum())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canAttack() {
		if((this.getNumberOfPieces() > 1) && (!this.isLocked()) ) {
			return true;
		}
		return false;
	}
	
	public int numberOfEnemysAdjacent() {
		int count = 0;
		for(Vertice v : getVertices()) {
			if(!v.getDestiny().getPlayer().equals(this.player)) {
				count = count + 1;
			}
		}
		
		return count;
		
	}
}