package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entities.Graph;
import entities.Node;
import entities.Vertice;
import auxiliaryEntities.AuxDistances;

public class GraphAlgorithm {

	private Graph graph;
	
	public GraphAlgorithm(Graph graph) {
		this.graph = graph;
	}
	
	public List<String> wayToTarget(String nodeOriginName, String nodeDestinyName) {
		List<AuxDistances> distances = getDistancesNotInitialize();
		Node originNode = this.graph.getNodeByName(nodeOriginName);
		configureDistanceOfOrigin(distances, originNode);
		
		
		List<Node> queue = new LinkedList<>();
		
		
		Node currentNode = originNode;
		Node destinyNode;
		
		queue.add(currentNode);
		
		while(!queue.isEmpty()) {
			currentNode = queue.remove(0);
			for(Vertice v : currentNode.getVertices() ) {
				destinyNode = v.getDestiny();				                                                   // mandar o vertice... 
				changeDistanceNotInitialize(distances, destinyNode , v);
				if(!isNodeVisited(distances, destinyNode)) {
					queue.add(destinyNode);
				}
				
			}
			setNodeAsVisited(distances, currentNode);
		}
		
		// printVectorDistances(distances);
		
		return getDistanceFromNode(distances, this.graph.getNodeByName(nodeDestinyName));
	}
	
//	private void printVectorDistances(List<AuxDistances> distances) { 
//		Collections.sort(distances);
//		for(AuxDistances ad : distances) {
//			System.out.println(ad.name + " " + ad.distance);
//		}
//	}
	
	private List<String> getDistanceFromNode(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				// System.out.println("Way => " + ad.ways);
				return ad.ways;
			}
		}
		return null;
	}
	
	/*  */
	private boolean isNodeVisited(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				return ad.visited;
			}
		}
		return false;
	}
	
	private void configureDistanceOfOrigin(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				ad.distance = 0;
				// ad.way = n.getName();
				ad.ways.add(n.getName());
			}
		}
	}
	
	private List<AuxDistances> getDistancesNotInitialize() {
		List<AuxDistances> distances = new ArrayList<>();
		for(Node n : graph.getNodes()) {
			distances.add(new AuxDistances(n.getName()));
		}
		return distances;
	}
	
	private void changeDistanceNotInitialize(List<AuxDistances> distances, Node n, Vertice v ) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				if ((distanceFromNode(distances,v.getOrigin()) + v.getWeight()) < distanceFromNode(distances,v.getDestiny())) {
						ad.distance = distanceFromNode(distances,v.getOrigin()) + v.getWeight();
						ad.ways = new ArrayList<>(waysFromNode(distances,v.getOrigin()));
						ad.ways.add(v.getDestiny().getName());
				}
			}
		}
	}
	
	
	private List<String> waysFromNode(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				return ad.ways;
			}
		}
		throw new RuntimeException("Trying to get a distance from an invalid node - program error");
	}
	
	private int distanceFromNode(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				return ad.distance;
			}
		}
		return 1000000;
	}
	
	private void setNodeAsVisited(List<AuxDistances> distances, Node n) {
		for(AuxDistances ad : distances) {
			if(ad.name.equals(n.getName())) {
				ad.visited = true;
			}
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
