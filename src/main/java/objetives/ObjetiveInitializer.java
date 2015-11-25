package objetives;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

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
		Objetive objetive;
		Image img;
		
		
		img = new ImageIcon(getClass().getResource("/images/objetiveEuropaOceania.png")).getImage();
		objetive = new ConquerContinentsObjetive(1,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.OCEANIA.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveAsiaAmericaDoSul.png")).getImage();
		objetive = new ConquerContinentsObjetive(2,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveEuropaAmericaDoSul.png")).getImage();
		objetive = new ConquerContinentsObjetive(3,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveAsiaAfrica.png")).getImage();
		objetive = new ConquerContinentsObjetive(4,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveAmericaDoNorteAfrica.png")).getImage();
		objetive = new ConquerContinentsObjetive(5,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);		
		
		img = new ImageIcon(getClass().getResource("/images/objetiveAmericaDoNorteOceania.png")).getImage();
		objetive = new ConquerContinentsObjetive(6,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.OCEANIA.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);	

		img = new ImageIcon(getClass().getResource("/images/objetive24ter.png")).getImage();
		objetive = new ConquerTwentyFourCountriesObjetive(7,gameExecutor);
		objetive.setImageObjetive(img);
		objetives.add(objetive);		
		
		img = new ImageIcon(getClass().getResource("/images/objetive18ter.png")).getImage();
		objetive = new PutTwoPiecesOnEighteenCountriesObjetive(8, gameExecutor);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirBranco.png")).getImage();
		addObjetiveToDestroyAnColor(9,ColorEnum.WHITE.getColor(), img);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirPreto.png")).getImage();
		addObjetiveToDestroyAnColor(10,ColorEnum.BLACK.getColor(), img);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirAmarelo.png")).getImage();
		addObjetiveToDestroyAnColor(11,ColorEnum.YELLOW.getColor(), img);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirVerde.png")).getImage();
		addObjetiveToDestroyAnColor(12,ColorEnum.GREEN.getColor(), img);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirAzul.png")).getImage();
		addObjetiveToDestroyAnColor(13,ColorEnum.BLUE.getColor(), img);
		
		img = new ImageIcon(getClass().getResource("/images/objetiveDestruirVermelho.png")).getImage();
		addObjetiveToDestroyAnColor(14,ColorEnum.RED.getColor(), img);		
	}
	
	private void addObjetiveToDestroyAnColor(int identifier,Color color, Image img) {
		Objetive objetive;
		try {
			objetive = new DestroyAnColorObjetive(identifier,gameExecutor.getPlayerByColor(color));
			objetive.setImageObjetive(img);
			objetives.add(objetive);
		} catch (PlayerWithColorNotExist e) {
			objetive = new ConquerTwentyFourCountriesObjetive(identifier,gameExecutor);
			img = new ImageIcon(getClass().getResource("/images/objetive24ter.png")).getImage();
			objetive.setImageObjetive(img);
			objetives.add(objetive);
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
	
	public Image getImageObjetiveFromHumanPlayer() {
		for(Player p : this.gameExecutor.getPlayers()) {
			if(p.isPlayer()) {
				return p.getObjetive().getImageObjetive();
			}
		}
		throw new RuntimeException("Trying to get image objetive from an non initialized player");
	}
}