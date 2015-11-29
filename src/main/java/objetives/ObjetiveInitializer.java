package objetives;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import algorithm.ImageReader;
import algorithm.MathAlgorithm;
import basic.GameExecutor;
import basic.GameInterface;
import entities.Node;
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
		
		
		img = ImageReader.getFormatedImage("/images/objetiveEuropaOceania.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(1,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.OCEANIA.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = ImageReader.getFormatedImage("/images/objetiveAsiaAmericaDoSul.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(2,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = ImageReader.getFormatedImage("/images/objetiveEuropaAmericaDoSul.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(3,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.EUROPA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_SUL.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = ImageReader.getFormatedImage("/images/objetiveAsiaAfrica.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(4,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.ASIA.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = ImageReader.getFormatedImage("/images/objetiveAmericaDoNorteAfrica.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(5,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.AFRICA.getName())),false);
		objetive.setImageObjetive(img);
		objetives.add(objetive);		
		
		img = ImageReader.getFormatedImage("/images/objetiveAmericaDoNorteOceania.png",GameInterface.resize);
		objetive = new ConquerContinentsObjetive(6,Arrays.asList(gameExecutor.getGraph().getContinentByName(ContinentNames.AMERICA_DO_NORTE.getName()),
				gameExecutor.getGraph().getContinentByName(ContinentNames.OCEANIA.getName())),true);
		objetive.setImageObjetive(img);
		objetives.add(objetive);	

		img = ImageReader.getFormatedImage("/images/objetive24ter.png",GameInterface.resize);
		objetive = new ConquerTwentyFourCountriesObjetive(7,gameExecutor);
		objetive.setImageObjetive(img);
		objetives.add(objetive);		
		
		img = ImageReader.getFormatedImage("/images/objetive18ter.png",GameInterface.resize);
		objetive = new PutTwoPiecesOnEighteenCountriesObjetive(8, gameExecutor);
		objetive.setImageObjetive(img);
		objetives.add(objetive);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirBranco.png",GameInterface.resize);
		addObjetiveToDestroyAnColor(9,ColorEnum.WHITE.getColor(), img);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirPreto.png",GameInterface.resize);
		addObjetiveToDestroyAnColor(10,ColorEnum.BLACK.getColor(), img);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirAmarelo.png",GameInterface.resize);
		addObjetiveToDestroyAnColor(11,ColorEnum.YELLOW.getColor(), img);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirVerde.png",GameInterface.resize);
		addObjetiveToDestroyAnColor(12,ColorEnum.GREEN.getColor(), img);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirAzul.png",GameInterface.resize);
		addObjetiveToDestroyAnColor(13,ColorEnum.BLUE.getColor(), img);
		
		img = ImageReader.getFormatedImage("/images/objetiveDestruirVermelho.png",GameInterface.resize);
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
			img = ImageReader.getFormatedImage("/images/objetive24ter.png",GameInterface.resize);
			objetive.setImageObjetive(img);
			objetives.add(objetive);
		}
	}

	public void giveAnObjective(Player p) {
		Objetive obj = objetives.remove(MathAlgorithm.getRandomInt(objetives.size()));
		
		
		if(obj.getIdentifier() > 8) {
			Iterator<Node> iterator = obj.getTargets().iterator(); 
			if(iterator.next().getPlayer().equals(p)) {
				obj = new ConquerTwentyFourCountriesObjetive(obj.getIdentifier(),gameExecutor);
				obj.setImageObjetive(new ImageIcon(getClass().getResource("/images/objetive24ter.png")).getImage());
				p.setObjetive(obj);
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