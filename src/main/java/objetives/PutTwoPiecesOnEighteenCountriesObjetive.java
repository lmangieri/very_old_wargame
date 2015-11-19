package objetives;

import java.util.List;

import basic.GameExecutor;
import entities.Node;
import entities.Player;

public class PutTwoPiecesOnEighteenCountriesObjetive implements Objetive {

	private Player p;
	private int identifier;	
	GameExecutor gameExecutor;
	
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
}
