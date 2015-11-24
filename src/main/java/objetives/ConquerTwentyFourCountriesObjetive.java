package objetives;

import java.awt.Image;
import java.util.Collections;
import java.util.List;

import basic.GameExecutor;
import basic.GameInterface;
import entities.Node;

public class ConquerTwentyFourCountriesObjetive implements Objetive {
	private int identifier;
	GameExecutor gameExecutor;
	private Image imageObjetive;
	
	public ConquerTwentyFourCountriesObjetive(int identifier,GameExecutor gameExecutor) {
		this.identifier = identifier;
		this.gameExecutor = gameExecutor;
	}
	
	@Override
	public int getIdentifier() {
		return identifier;
	}
	
	@Override
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	
	@Override
	public List<Node> getTargets() {
		return gameExecutor.getGraph().getNodes();
	}
	
	@Override
	public String toString() {
		return "Conquistar 24 territórios";
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
