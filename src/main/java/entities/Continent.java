package entities;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

import algorithm.ImageReader;
import basic.GameInterface;
import enums.ContinentNames;

public class Continent {
	private List<Node> countries;
	private String name;
	public Image image;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Continent(List<Node> countries, String name) {
		this.countries = countries;
		this.name = name;
		
		if(name.equals(ContinentNames.AFRICA.getName())) {
			image= ImageReader.getFormatedImage("/images/somenteAfrica.png",GameInterface.resize);
		} else if (name.equals(ContinentNames.AMERICA_DO_NORTE.getName())) {
			image = ImageReader.getFormatedImage("/images/somenteAmericaNorte.png",GameInterface.resize);
		} else if (name.equals(ContinentNames.AMERICA_DO_SUL.getName())) {
			image = ImageReader.getFormatedImage("/images/somenteAmericaSul.png",GameInterface.resize);
		} else if (name.equals(ContinentNames.ASIA.getName())) {
			image = ImageReader.getFormatedImage("/images/somenteAsia.png",GameInterface.resize);
		} else if (name.equals(ContinentNames.EUROPA.getName())) {
			image = ImageReader.getFormatedImage("/images/somenteEuropa.png",GameInterface.resize);
		} else {
			image = ImageReader.getFormatedImage("/images/somenteOceania.png",GameInterface.resize);
		}
		
	}
	
	public List<Node> getCountries() {
		return countries;
	}

	public void setCountries(List<Node> countries) {
		this.countries = countries;
	}
	
	public boolean thisNodeBelongsToThisContinent(Node n) {
		for(Node g : getCountries()) {
			if(n.equals(g)) {
				return true;
			}
		}
		return false;
	}
	
	public int getPiecesToFillContinent() {
		if (getName().equals(ContinentNames.AFRICA.getName())) {
			return 3;
		} else if (getName().equals(ContinentNames.AMERICA_DO_NORTE.getName())) {
			return 5;
		} else if (getName().equals(ContinentNames.AMERICA_DO_SUL.getName())) {
			return 2;
		} else if (getName().equals(ContinentNames.ASIA.getName())) {
			return 7;
		} else if (getName().equals(ContinentNames.OCEANIA.getName())) {
			return 2;
		} else if (getName().equals(ContinentNames.EUROPA.getName())) {
			return 5;
		}
		throw new RuntimeException("Trying to get pieces from an invalid continent");
	}
	

}
