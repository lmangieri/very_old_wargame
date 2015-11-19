package objetives;

import java.util.List;

import entities.Node;

public interface Objetive {
	public List<Node> getTargets();
	public void setIdentifier(int identifier);
	public int getIdentifier();
	
	
	// existem 4 tipos de objetivos... 
	// 1 deles possui uma lista de continentes
	// 1 deles possui uma cor  para ser atacada
	// 1 deles para conquistar 18 territórios e colocar 2 peças em cada
	// 1 deles para conquistar 24 territórios...
}