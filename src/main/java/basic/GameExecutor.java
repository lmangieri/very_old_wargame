package basic;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objetives.ConquerTwentyFourCountriesObjetive;
import objetives.DestroyAnColorObjetive;
import objetives.ObjetiveInitializer;
import algorithm.MathAlgorithm;
import algorithm.StrategiesAlgorithm;
import auxiliaryEntities.DadosJogados;
import entities.Continent;
import entities.Graph;
import entities.Node;
import entities.Player;
import enums.ColorEnum;
import enums.ContinentNames;
import exceptions.PlayerWithColorNotExist;

public class GameExecutor {

	private Graph graph;
	private ObjetiveInitializer objetiveInitializer;
	private StrategiesAlgorithm strategiesAlgorithm; // TODO: this probably will
														// leave from here.

	private List<Player> players;

	public GameExecutor(Graph graph) {
		this.graph = graph;
		this.strategiesAlgorithm = new StrategiesAlgorithm(graph);
		players = new ArrayList<>();
	}

	private void giveObjectives() {
		for (Player p : players) {
			objetiveInitializer.giveAnObjective(p);
			System.out.println("Player " + p.getColorEnum() + ", objetive == "
					+ p.getObjetive().toString());
		}
	}
	
	public void initPlayers(String colorChoosed, int numberOfPlayersChoosed) {
		List<ColorEnum> availableColors = new ArrayList<>();
		availableColors.add(ColorEnum.BLUE);
		availableColors.add(ColorEnum.RED);
		availableColors.add(ColorEnum.WHITE);
		availableColors.add(ColorEnum.BLACK);
		availableColors.add(ColorEnum.GREEN);
		availableColors.add(ColorEnum.YELLOW);
		
		// players.add(new Player(availableColors.get(0), true));
		players.add(new Player(availableColors.get(0), false));
		players.add(new Player(availableColors.get(1), false));
		players.add(new Player(availableColors.get(2), false));
		players.add(new Player(availableColors.get(3), false));
		players.add(new Player(availableColors.get(4), false));
		players.add(new Player(availableColors.get(5), false));
		
		for(Player p : players) {
			if(p.getColorEnum().getName().equals(colorChoosed)) {
				p.setIsPlayer(true);
			}
		}
		
		int currentQtdPlayers = 6;
		int toBeRemoved = currentQtdPlayers - numberOfPlayersChoosed;
		while(toBeRemoved > 0) {
			Player p = players.get(MathAlgorithm.getRandomInt(currentQtdPlayers));
			if(!p.isPlayer()) {
				players.remove(p);
				toBeRemoved = toBeRemoved - 1;
				currentQtdPlayers = currentQtdPlayers - 1;
			}
		}

		Collections.shuffle(players);
		
		sortNodesForPlayers();
		this.objetiveInitializer = new ObjetiveInitializer(this);
		giveObjectives();
		putPiecesForAll();
	}

	/* embaralha os jogadores... e eles colocam as peças... */
	public void putPiecesForAll() {
		int tot = 3;
		// Collections.shuffle(players);
		while (tot > 0) {
			for (Player p : players) {
				strategiesAlgorithm.decideNextPiece(p,null).addNumberOfPieces(1);
			}
			tot = tot - 1;
		}
	}

	public void putPieceToAPlayer(Player p, int tot) {
		while (tot > 0) {
			strategiesAlgorithm.decideNextPiece(p,null).addNumberOfPieces(1);
			tot = tot - 1;
		}
	}

	private void sortNodesForPlayers() {
		List<Node> copyNodes = new ArrayList<Node>(this.graph.getNodes());

		int copyNodesSize = copyNodes.size();

		while (!copyNodes.isEmpty()) {
			for (Player p : this.players) {
				Node n = copyNodes.remove(MathAlgorithm
						.getRandomInt(copyNodesSize));
				n.setPlayer(p);
				p.getNodes().add(n);
				copyNodesSize = copyNodesSize - 1;
				if (copyNodes.isEmpty()) {
					break;
				}
			}
		}
	}

	public Player getPlayerByColor(Color color) throws PlayerWithColorNotExist {
		for (Player p : players) {
			if (p.getColorEnum().getColor().equals(color)) {
				return p;
			}
		}
		throw new PlayerWithColorNotExist(color);
	}

	/* Setters and getters */

	public synchronized List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getTotalPlayers() {
		return this.players.size();
	}

	public void setTotalPlayers(int totalPlayers) {
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	// TODO : mudar o nome deste método.
	public void putPiecesOnContinent(Player p) {
		ColorEnum colorPlayer = p.getColorEnum();
		boolean hasContinent = true;

		for (Continent c : this.graph.getContinentes()) {
			for (Node n : c.getCountries()) {
				if (!n.getPlayer().getColorEnum().equals(colorPlayer)) {
					hasContinent = false;
					break;
				}
			}
			if (hasContinent) {
				putPiecesOnContinent(p, c);
			}
			hasContinent = true;
		}

	}

	private void putPiecesOnContinent(Player p, Continent c) {
		int numberOfPieces = 0;
		if (c.getName().equals(ContinentNames.AFRICA.getName())) {
			numberOfPieces = 3;
		} else if (c.getName().equals(ContinentNames.AMERICA_DO_NORTE.getName())) {
			numberOfPieces = 5;
		} else if (c.getName().equals(ContinentNames.AMERICA_DO_SUL.getName())) {
			numberOfPieces = 2;
		} else if (c.getName().equals(ContinentNames.ASIA.getName())) {
			numberOfPieces = 7;
		} else if (c.getName().equals(ContinentNames.OCEANIA.getName())) {
			numberOfPieces = 3;
		} else if (c.getName().equals(ContinentNames.EUROPA.getName())) {
			numberOfPieces = 5;
		}
		
		while(numberOfPieces > 0) {
			this.strategiesAlgorithm.decideNextPiece(p, c).addNumberOfPieces(1);
			numberOfPieces = numberOfPieces -1;
		}
	}
	
	public synchronized void attack(Node nodeAttacker, Node nodeTarget) {
		Player pa = nodeAttacker.getPlayer();
		Player pt = nodeTarget.getPlayer();
		
		if (nodeAttacker.getNumberOfPieces() > 1) {
			DadosJogados d = MathAlgorithm.war(
					nodeAttacker.getNumberOfPieces() - 1,
					nodeTarget.getNumberOfPieces());

			nodeTarget.setNumberOfPieces(nodeTarget
					.getNumberOfPieces() - d.defLost);

			nodeAttacker.setNumberOfPieces(nodeAttacker
					.getNumberOfPieces() - d.atqLost);

			if (nodeTarget.getNumberOfPieces() == 0) {
				Player playerTarget = nodeTarget.getPlayer();

				int transferedPieces = nodeAttacker.getNumberOfPieces() - 1;
				if (transferedPieces > 3) {
					transferedPieces = 3;
				}

				// removo a o node do player alvo
				nodeTarget.getPlayer().getNodes().remove(nodeTarget);

				nodeTarget.setPlayer(nodeAttacker.getPlayer());

				nodeTarget.setNumberOfPieces(transferedPieces);

				nodeAttacker.setNumberOfPieces(nodeAttacker
						.getNumberOfPieces() - transferedPieces);

				nodeAttacker.getPlayer().getNodes().add(nodeTarget);
				
				verifyObjetive(pa,pt);
				if(playerTarget.getNodes().size() == 0) {
					System.out.println("Player "+pa.getColorEnum().getName()+" destruiu o player "+pt.getColorEnum().getName());
					getPlayers().remove(playerTarget);
					setTotalPlayers(getTotalPlayers() -1);
				}
			}
		}
		nodeAttacker = null;
		nodeTarget = null;
		
	}
	
	public void verifyObjetive(Player playerAttacker, Player playerTarget) {
		if(playerTarget.getNodes().size() == 0) {
			for(Player p : getPlayers()) {
				if(!p.equals(playerAttacker)) {
					if(p.getObjetive().getClass().equals(DestroyAnColorObjetive.class)) {
						DestroyAnColorObjetive obj = (DestroyAnColorObjetive) p.getObjetive();
						if(obj.getColor().equals(playerTarget.getColorEnum())) {
							System.out.println("Player "+p.getColorEnum().getName()+" CHANGING OBJECTIVE ... conquistar 24 territórios.");
							p.setObjetive(new ConquerTwentyFourCountriesObjetive(obj.getIdentifier(),this));
						}
					}
				}
			}
		}
	}
	
	public Image getImageObjetiveFromHumanPlayer() {
		return this.objetiveInitializer.getImageObjetiveFromHumanPlayer();
	}
}
