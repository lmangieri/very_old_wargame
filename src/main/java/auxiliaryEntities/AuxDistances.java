package auxiliaryEntities;

import java.util.ArrayList;
import java.util.List;

public class AuxDistances implements Comparable<AuxDistances>{

	public String name;
	public int distance;
	public boolean visited;
	
	public String way;
	public List<String> ways;
	
	public AuxDistances(String name) {
		this.ways = new ArrayList<>();
		this.name = name;
		this.distance = 1000000;
		this.visited = false;
		this.way = "";
	}

	@Override
	public int compareTo(AuxDistances other) {
		if(this.distance > other.distance) {
			return -1;
		} else 
		if (this.distance < other.distance) {
			return 1;
		} 
		
		return 0;
	}
}