package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import objetives.PutTwoPiecesOnEighteenCountriesObjetive;
import auxiliaryEntities.AuxPutOrRelocatePiece;
import auxiliaryEntities.StructureAuxToPutPiecesOnContinent;
import entities.Continent;
import entities.Graph;
import entities.Node;
import entities.Player;
import entities.Vertice;
import enums.ColorEnum;

public class StrategiesAlgorithm {

	private Graph graph;

	public StrategiesAlgorithm(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public boolean isThisNodeFromAnDominatedContinent(Node n) {
		ColorEnum c = n.getPlayer().getColorEnum();
		Continent continent = this.graph.getContinentByName(n
				.getContinentName());
		for (Node g : continent.getCountries()) {
			if (!g.getPlayer().getColorEnum().equals(c)) {
				return false;
			}
		}
		return true;
	}

	public boolean isThisNodeWithMajorityOnHisCountry(Node n) {
		ColorEnum c = n.getPlayer().getColorEnum();
		Continent continent = this.graph.getContinentByName(n
				.getContinentName());
		int total = continent.getCountries().size();
		int count = 0;
		for (Node g : continent.getCountries()) {
			if (g.getPlayer().getColorEnum().equals(c)) {
				count = count + 1;
			}
		}
		if ((total / 2) + 1 <= count) {
			return true;
		}
		return false;
	}


	// Decide o próximo node que o computador colocará a peça...
	public Node decideNextPieceToPut(Player p, Continent c) {
		ColorEnum playerColor = p.getColorEnum();
		


		List<AuxPutOrRelocatePiece> auxPutPiecesList = new ArrayList<>();
		for (Node n : p.getNodes()) {
			if(c != null) {
				if(c.thisNodeBelongsToThisContinent(n)) {
					auxPutPiecesList.add(new AuxPutOrRelocatePiece(n));
				}
			} else {
				auxPutPiecesList.add(new AuxPutOrRelocatePiece(n));
			}
			
		}

		for (AuxPutOrRelocatePiece aux : auxPutPiecesList) {
			if (aux.getNode().isLocked()) {
				aux.setRate(-20);
			} else {
				if (this.isThisNodeFromAnDominatedContinent(aux.getNode())) {
					aux.increaseRate(10);
					if(isThisNodeUnprotected(aux.getNode())) {
						aux.increaseRate(20);
					}
				} else {
					if (this.isThisNodeWithMajorityOnHisCountry(aux.getNode())) {
						aux.setRate(1);
						if(isThisNodeUnprotected(aux.getNode())) {
							aux.increaseRate();
						}
					}
				}
			}
		}

		for (Node targetNode : p.getObjetive().getTargets()) {
			for (Vertice v : targetNode.getVertices()) {
				if (v.getDestiny().getPlayer().getColorEnum().equals(playerColor)) {
					findAndAddRateToNode(auxPutPiecesList, v.getDestiny());
					if(isThisNodeUnprotected(v.getDestiny())) {
						findAndAddRateToNode(auxPutPiecesList,v.getDestiny());
					}
				}
			}
		}
		
		
		// ANALISANDO OBJETIVO DE CONQUISTAR 18 TERRITÓRIOS E TER 2 PEÇAS EM CADA UM.
		if(p.getObjetive().getClass().equals(PutTwoPiecesOnEighteenCountriesObjetive.class)) {
			analyzingPutPieceForPutTwoPiecesOnEighteenCountriesObjetive(p, auxPutPiecesList);
		}
		
		for(AuxPutOrRelocatePiece n : auxPutPiecesList) {
			if(n.getNode().getNumberOfPieces() > 3) {
				n.increaseRate(n.getNode().getNumberOfPieces() * -2);
			}
		}
		

		Collections.sort(auxPutPiecesList);
		// printAuxPutPiecesList(auxPutPiecesList);
		return auxPutPiecesList.get(0).getNode();
	}
	
	// TODO: bad name :( 
	private void analyzingPutPieceForPutTwoPiecesOnEighteenCountriesObjetive(Player p, List<AuxPutOrRelocatePiece> list) {
		if(p.getNodes().size() > 17) {
			for(AuxPutOrRelocatePiece aux : list) {
				if(aux.getNode().getNumberOfPieces() == 1) {
					aux.increaseRate(50);
				}
			}
		}
	}
	
	public boolean isThisNodeUnprotected(Node node) {
		if(node.getNumberOfPieces() == 1) {
			return true;
		}
		return false;
	}

	public void printAuxPutPiecesList(List<AuxPutOrRelocatePiece> auxPutPiecesList) {
		for (AuxPutOrRelocatePiece aux : auxPutPiecesList) {
			System.out.println("aux ... node -> " + aux.getNode().getName()
					+ " rate -> " + aux.getRate());
		}
	}

	private void findAndAddRateToNode(List<AuxPutOrRelocatePiece> auxPutPiecesList, Node n) {
		for (AuxPutOrRelocatePiece auxPutPiece : auxPutPiecesList) {
			if (auxPutPiece.getNode().equals(n)) {
				increaseRateFromAuxPutPiece(auxPutPiece);
			}
		}
	}

	private void increaseRateFromAuxPutPiece(AuxPutOrRelocatePiece auxPutPiece) {
		auxPutPiece.setRate(auxPutPiece.getRate() + 1);
	}
	
	public Node decideNextTarget(Node n) {
		int numberOfPieces = n.getNumberOfPieces();
		
		if(numberOfPieces == 1) {
			return null;
		}
		
		if(isThisNodeFromAnDominatedContinent(n)) {  // impedir que o node ataque caso esteja defendendo o continente.
			if(numberOfPieces <= 3) {
				return null;
			}
		}
		
		ConcurrentLinkedQueue<Node> targets = n.getPlayer().getObjetive().getTargets();
		if(!targets.isEmpty()) {
			for(Node tar : targets) {  // o alvo que vem do dobjetivo pode já pertencer ao jogador...
				
				if(!tar.getPlayer().getColorEnum().equals(n.getPlayer().getColorEnum())) {
					if(isThisNodeAdjacentTo(n,tar)) { // TODO: isso é necessário ?
						if(!(numberOfPieces == 2 && tar.getNumberOfPieces() > 1)) {
							return tar;
						}
					}
				}
			}
		}
		
		for(Vertice v : n.getVertices()) {
			if(!v.getDestiny().getPlayer().getColorEnum().equals(n.getPlayer().getColorEnum())) {
				if(!(numberOfPieces == 2 && v.getDestiny().getNumberOfPieces() > 1)) {
					return v.getDestiny();
				}
			}
		}		
		return null;
	}
	
	// If n2 is adjacent to n1, return TRUE
	public boolean isThisNodeAdjacentTo(Node n1, Node n2) {
		for(Vertice v : n1.getVertices()) {
			if(v.getDestiny().equals(n2)) {
				return true;
			}
		}
		return false;
	}
	
	
	// returns true if all pieces were used
	public boolean isThisStructureAuxToPutPiecesOnContinentUtilized(List<StructureAuxToPutPiecesOnContinent> list) {
		for(StructureAuxToPutPiecesOnContinent str : list) {
			if(str.numberOfPieces > 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canPutPieceOfContinent(Node n,
			List<StructureAuxToPutPiecesOnContinent> listOfStructureAuxToPutPiecesOnContinent) {
		for(StructureAuxToPutPiecesOnContinent str : listOfStructureAuxToPutPiecesOnContinent) {
			if(str.continent.getName().equals(n.getContinentName())) {
				return true;
			}
		}
		return false;
	}

	public boolean isListOfStructureAuxToPutPiecesOnContinentIsEmpty(
			List<StructureAuxToPutPiecesOnContinent> list) {
		for(StructureAuxToPutPiecesOnContinent str : list) {
			if(str.numberOfPieces > 0) {
				return false;
			}
		}
		return true;
	}
}
