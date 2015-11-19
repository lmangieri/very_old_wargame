package auxiliaryEntities;

import java.util.ArrayList;
import java.util.List;

import entities.Node;
import entities.Player;

public class AuxPutOrRelocatePiece implements Comparable<AuxPutOrRelocatePiece> {
	
	private Node node;
	private int rate;
	private int piecesThatCanBeRelocated;
	
	public AuxPutOrRelocatePiece(Node node) {
		this.node = node;
		this.rate = 0;
	}
	

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public void increaseRate() {
		this.rate = this.rate + 2;
	}
	
	public void increaseRate(int rateToAdd) {
		this.rate = this.rate + rateToAdd;
	}	
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}	

	@Override
	public int compareTo(AuxPutOrRelocatePiece o) {
		if(this.rate < o.getRate()) {
			return 1;
		} else 
		if(this.rate > o.getRate()) {
			return -1;
		} else

		return 0;
	}


	public int getPiecesThatCanBeRelocated() {
		return piecesThatCanBeRelocated;
	}

	public void setPiecesThatCanBeRelocated(int piecesThatCanBeRelocated) {
		this.piecesThatCanBeRelocated = piecesThatCanBeRelocated;
	}
	
	public void setInitialValueToPiecesThatCanBeRelocated() {
		int tot = 0;
		if(this.node.getNumberOfPieces() > 1) {
			tot = this.node.getNumberOfPieces() -1;
		}
		setPiecesThatCanBeRelocated(tot);
	}
	
	public static List<AuxPutOrRelocatePiece> getStructureToRelocatePieces(Player p) {
		List<AuxPutOrRelocatePiece> list = new ArrayList<>();
		for(Node n : p.getNodes()) {
			AuxPutOrRelocatePiece aux = new AuxPutOrRelocatePiece(n);
			aux.setInitialValueToPiecesThatCanBeRelocated();
			list.add(aux);
		}
		return list;
	}	
}
