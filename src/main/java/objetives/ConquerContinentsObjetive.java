package objetives;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import entities.Continent;
import entities.Node;

public class ConquerContinentsObjetive implements Objetive {
	
	private List<Continent> targetContinents;
	private boolean isThereAnAleatoryContinent;
	private int identifier;
	private Image imageObjetive;
	
	@Override
	public int getIdentifier() {
		return identifier;
	}
	
	@Override
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public ConquerContinentsObjetive(int identifier, List<Continent> continents, boolean isThereAnAleatoryContinent) {
		this.identifier = identifier;
		this.targetContinents = continents;
		this.isThereAnAleatoryContinent = isThereAnAleatoryContinent;
	}
	
	@Override
	public ConcurrentLinkedQueue<Node> getTargets() {
		ConcurrentLinkedQueue<Node> targets = new ConcurrentLinkedQueue<>();
		for(Continent c : targetContinents) {
			targets.addAll(c.getCountries());
		}
		return targets;
	}
	
	@Override
	public String toString() {
		String continentes = "";
		for(Continent c : targetContinents) {
			continentes = continentes.concat(c.getName() + "  ");
		}
		
		return ("Conquistar os seguintes continentes : "+continentes);
	}

	public List<Continent> getContinents() {
		return targetContinents;
	}

	public void setContinents(List<Continent> continents) {
		this.targetContinents = continents;
	}

	public boolean isThereAnAleatoryContinent() {
		return isThereAnAleatoryContinent;
	}

	public void setThereAnAleatoryContinent(boolean isThereAnAleatoryContinent) {
		this.isThereAnAleatoryContinent = isThereAnAleatoryContinent;
	}

	@Override
	public void setImageObjetive(Image image) {
		this.imageObjetive = image;
	}

	@Override
	public Image getImageObjetive() {
		return this.imageObjetive;
	}


}
