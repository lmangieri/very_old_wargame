package auxiliaryEntities;

import java.util.ArrayList;
import java.util.List;

import entities.Continent;
import entities.Node;
import entities.Player;
import enums.ColorEnum;

public class StructureAuxToPutPiecesOnContinent {
	public Continent continent;
	public int numberOfPieces;
	public StructureAuxToPutPiecesOnContinent(Continent continent, int numberOfPieces) {
		this.continent = continent;
		this.numberOfPieces = numberOfPieces;
	}
	
	public static List<StructureAuxToPutPiecesOnContinent> getListOfStructureAuxToPutPiecesOnContinent(Player p, List<Continent> continents ) {
		List<StructureAuxToPutPiecesOnContinent> list = new ArrayList<>();

		ColorEnum colorPlayer = p.getColorEnum();
		boolean hasContinent = true;

		for (Continent c : continents) {
			for (Node n : c.getCountries()) {
				if (!n.getPlayer().getColorEnum().equals(colorPlayer)) {
					hasContinent = false;
					break;
				}
			}
			if (hasContinent) {
				list.add(new StructureAuxToPutPiecesOnContinent(c,c.getPiecesToFillContinent()));
			}
			hasContinent = true;
		}
		
		return list;
	}	
}
