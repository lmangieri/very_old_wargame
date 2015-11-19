package objetives;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basic.GameExecutor;
import algorithm.MathAlgorithm;
import entities.Player;
import enums.ColorEnum;
import enums.ContinentNames;
import exceptions.PlayerWithColorNotExist;

public class ObjetiveInitializer {
	private GameExecutor gameExecutor;
	private List<Objetive> objetives;
	
	public ObjetiveInitializer(GameExecutor gameExecutor) {
		this.gameExecutor = gameExecutor;
		
		
		objetives = new ArrayList<>();
		
		objetives.add(new ConquerContinentsObjetive(1,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.OCEANIA.getName())),true));
		objetives.add(new ConquerContinentsObjetive(2,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),false));
		objetives.add(new ConquerContinentsObjetive(3,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),true));
		objetives.add(new ConquerContinentsObjetive(4,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false));
		objetives.add(new ConquerContinentsObjetive(5,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false));
		objetives.add(new ConquerContinentsObjetive(6,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName("Oceania")),true));
		
		objetives.add(new ConquerTwentyFourCountriesObjetive(7,gameExecutor));
		objetives.add(new PutTwoPiecesOnEighteenCountriesObjetive(8, gameExecutor));

		addObjetiveToDestroyAnColor(9,ColorEnum.WHITE.getColor());
		addObjetiveToDestroyAnColor(10,ColorEnum.BLACK.getColor());
		addObjetiveToDestroyAnColor(11,ColorEnum.YELLOW.getColor());
		addObjetiveToDestroyAnColor(12,ColorEnum.GREEN.getColor());
		addObjetiveToDestroyAnColor(13,ColorEnum.BLUE.getColor());
		addObjetiveToDestroyAnColor(14,ColorEnum.RED.getColor());		
	}
	
	private void addObjetiveToDestroyAnColor(int identifier,Color color) {
		try {
			objetives.add(new DestroyAnColorObjetive(identifier,gameExecutor.getPlayerByColor(color)));
		} catch (PlayerWithColorNotExist e) {
			objetives.add(new ConquerTwentyFourCountriesObjetive(identifier,gameExecutor));
		}
	}

	public void giveAnObjective(Player p) {
		Objetive obj = objetives.remove(MathAlgorithm.getRandomInt(objetives.size()));
		
		if(obj.getIdentifier() > 8) {
			if(obj.getTargets().get(0).getPlayer().equals(p)) {
				p.setObjetive(new ConquerTwentyFourCountriesObjetive(obj.getIdentifier(),gameExecutor));
				return;
			}
		}
		p.setObjetive(obj);
	}
	
	public List<Objetive> getObjetives() {
		return objetives;
	}
	public void setObjetives(List<Objetive> objetives) {
		this.objetives = objetives;
	}
}