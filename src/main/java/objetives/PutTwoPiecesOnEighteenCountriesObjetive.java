package objetives;

import java.awt.Image;
import java.util.List;

import basic.GameExecutor;
import entities.Node;

public class PutTwoPiecesOnEighteenCountriesObjetive implements Objetive {

	private int identifier;	
	GameExecutor gameExecutor;
	private Image imageObjetive;
	
	public PutTwoPiecesOnEighteenCountriesObjetive(int identifier,GameExecutor gameExecutor) {
		this.identifier = identifier;
		this.gameExecutor = gameExecutor;
	}
	
	@Override
	public List<Node> getTargets() {
		return gameExecutor.getGraph().getNodes();
	}

	@Override
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	@Override
	public int getIdentifier() {
		return this.identifier;
	}
	
	@Override
	public String toString() {
		return "Colocar pelo menos 2 exércitos em 18 territórios";
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
