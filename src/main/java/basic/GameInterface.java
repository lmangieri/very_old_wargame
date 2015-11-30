package basic;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import algorithm.ImageReader;
import algorithm.MathAlgorithm;
import algorithm.StrategiesAlgorithm;
import auxiliaryEntities.AuxPutOrRelocatePiece;
import auxiliaryEntities.CheckboxGroupList;
import auxiliaryEntities.MP3Player;
import auxiliaryEntities.StructureAuxToPutPiecesOnContinent;
import entities.DadosJogados;
import entities.Graph;
import entities.Node;
import entities.Player;
import entities.Vertice;
import enums.ColorEnum;

public class GameInterface extends JPanel implements ActionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameExecutor gameExecutor;
	private StateMachine stateMachine;
	public StrategiesAlgorithm strategiesAlgorithm;
	private static double resize = 1.0;
	private Thread game;
	private Timer time;	
	

	/* Variáveis - initial screen */
	private JButton startButton;
	private CheckboxGroupList grpColorsSelect;
	private CheckboxGroupList grpTotalPlayers;
	private CheckboxGroupList grpVelocityGame;
	private String colorChoosed;
	private int numberOfPlayersChoosed;


	/* Conjunto de imagens utilizadas para a interface gráfica */
	public Image initialScreen;
	public Image imgBoard;
	public Image imgFooter;
	public Image imgAttacker;
	public Image imgDefender;
	public Image imgBlueCircle;
	public Image imgPlayerObjetive;
	public Image imgEstadoAtacar;
	public Image imgEstadoColocarPecas;
	public Image imgEstadoColocarPecasContinente;
	public Image imgEstadoRemanejamento;
	public Image imgEstadoTurnoComputador;
	public Image imgEstadoVitoria;
	public Image imgEstadoDerrota;
	public Image imgDados;
	
	/* Sounds */
	public static MP3Player victoryMusic = new MP3Player(
			"/musics/victorymusic.mp3");
	public static MP3Player failMusic = new MP3Player("/musics/failmusic.mp3");
	public static MP3Player attack1Music = new MP3Player("/musics/tuturu_1.mp3");
	public static MP3Player attack2Music = new MP3Player(
			"/musics/boomheadshot.swf.mp3");
	public static MP3Player attackfailMusic = new MP3Player(
			"/musics/failattack.mp3");
	public static MP3Player teleportMusic = new MP3Player(
			"/musics/teleport.mp3");

	
	public GameInterface(Double resize) {
		GameInterface.setResize(resize);
		Graph graph = Graph.getGraphWithDefaultConfiguration();
		graph.resizeNodeCoordenates(resize);
		gameExecutor = new GameExecutor(graph);
		this.stateMachine = new StateMachine(gameExecutor);
		this.setLayout(null);

		this.strategiesAlgorithm = new StrategiesAlgorithm(
				this.gameExecutor.getGraph());

		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				MouseClicked(evt);
			}
		});

		initGameImages();

		initStartScreenAttributes();
	
		this.stateMachine.setStateGame(0);
	}
	
	public void initStartScreenAttributes() {
		this.startButton = new JButton("Jogar");
		this.add(this.startButton);
		this.startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonPerformed(evt);
			}
		});
		initButtons();
	}
	
	public void initGameImages() {
		this.initialScreen = ImageReader.getFormatedImage("/images/initialScreen.png",resize);
		this.imgBoard = ImageReader.getFormatedImage("/images/paintedCropped.png",resize);
		this.imgFooter = ImageReader.getFormatedImage("/images/footer.png",resize);
		this.imgAttacker = ImageReader.getFormatedImage("/images/swordWithLayerCleared.png",resize);
		this.imgDefender = ImageReader.getFormatedImage("/images/shieldWithLayerCleared.png",resize);
		this.imgBlueCircle = ImageReader.getFormatedImage("/images/circuloazul.png",resize);
		this.imgEstadoAtacar = ImageReader.getFormatedImage("/images/estadoAtacar.png",resize);
		this.imgEstadoColocarPecas = ImageReader.getFormatedImage("/images/estadoColocarPecas.png",resize);
		this.imgEstadoColocarPecasContinente = ImageReader.getFormatedImage("/images/estadoColocarPecasContinente.png",resize);
		this.imgEstadoRemanejamento = ImageReader.getFormatedImage("/images/estadoRemanejamento.png",resize);
		this.imgEstadoTurnoComputador = ImageReader.getFormatedImage("/images/estadoTurnoComputador.png",resize);
		this.imgEstadoVitoria = ImageReader.getFormatedImage("/images/estadoVitoria.png",resize);
		this.imgEstadoDerrota = ImageReader.getFormatedImage("/images/estadoDerrota.png",resize);
		this.imgDados = ImageReader.getFormatedImage("/images/dados.png",resize);
	}
	

	protected void startButtonPerformed(ActionEvent evt) {
		this.numberOfPlayersChoosed = Integer.valueOf(this.grpTotalPlayers
				.getSelectedCheckbox().getLabel());

		this.colorChoosed = this.grpColorsSelect.getSelectedCheckbox()
				.getLabel();

		String v = this.grpVelocityGame.getSelectedCheckbox().getLabel();
		if (v.equals("Lento")) {
			this.stateMachine.velocityChoosed = 1200;
			this.gameExecutor.velocityChoosed = 1200;
		} else if (v.equals("Normal")) {
			this.stateMachine.velocityChoosed = 500;
			this.gameExecutor.velocityChoosed = 500;
		} else if (v.equals("Rápido")) {
			this.stateMachine.velocityChoosed = 100;
			this.gameExecutor.velocityChoosed = 100;
		} else {
			this.stateMachine.velocityChoosed = 0;
			this.gameExecutor.velocityChoosed = 0;
		}
		this.startButton.setVisible(false);
		for (Checkbox checkbox : this.grpColorsSelect.list) {
			checkbox.setVisible(false);
		}
		for (Checkbox checkbox : this.grpTotalPlayers.list) {
			checkbox.setVisible(false);
		}
		for (Checkbox checkbox : this.grpVelocityGame.list) {
			checkbox.setVisible(false);
		}

		gameExecutor.initPlayers(colorChoosed, numberOfPlayersChoosed);

		this.imgPlayerObjetive = this.gameExecutor
				.getImageObjetiveFromHumanPlayer();

		time = new Timer(5, this);
		time.start();

		this.game = new Thread(this);
		game.start();

	}

	public void initGame() {
		this.stateMachine.initGameStateOne();
	}

	// OK
	private void jumpStepButtonClicked(java.awt.event.MouseEvent evt) {
		int state = this.stateMachine.getStateGame();
		if (state == 3 || state == 5 || state == 6 || state == 7) {

			this.stateMachine.nodeAttacker = null;
			this.stateMachine.nodeTarget = null;
			this.stateMachine.nodeToTransferDestiny = null;
			this.stateMachine.nodeToTransferOrigin = null;
		}

		if (state == 3) {
			this.stateMachine.listOfAuxPutOrRelocatePiece = AuxPutOrRelocatePiece
					.getStructureToRelocatePieces(this.stateMachine.currentPlayer);

			// TODO : quem deve transitar de um estado para o outro é o
			// stateMachine....
			this.stateMachine.setStateGame(7);
		} else if (state == 7) {
			this.stateMachine.nextTurn();
		}
	}

	/* Estado 3... jogador está escolhendo país para realizar um ataque. */
	private void MouseClicked(java.awt.event.MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		if (x > (int)(900/getResize()) && y > (int)(795/getResize())) {
			jumpStepButtonClicked(evt);

		} else if (this.stateMachine.getStateGame() == 3) {
			markNodeAsAttackerOrTarget(x, y);
			if (this.stateMachine.nodeAttacker != null
					&& this.stateMachine.nodeTarget != null) {
				// should it be like it ?
				this.gameExecutor.attack(this.stateMachine.nodeAttacker,
						this.stateMachine.nodeTarget);
				if (this.stateMachine.nodeAttacker.getNumberOfPieces() <= 1) {
					this.stateMachine.nodeAttacker = null;
					this.stateMachine.nodeTarget = null;
				}
				if (this.stateMachine.theGameHasEnded()) {
					this.stateMachine.setStateGame(8);
					this.stateMachine.stage8();
				}
			} else {
			}
		} else if (this.stateMachine.getStateGame() == 5) { // estado para
															// colocar peças,
															// jogador...
			playerPutPieceAt(x, y);
		} else if (this.stateMachine.getStateGame() == 6) { // estado para
															// colocar peças
															// continente
			putPiecesContinent(x, y);
		} else if (this.stateMachine.getStateGame() == 7) {
			markNodeToTransfer(x, y);
			if (this.stateMachine.nodeToTransferOrigin != null
					&& this.stateMachine.nodeToTransferDestiny != null) {
				playerTransfer();
				if (cantTransferAnymore()) {
					// TODO : todas essas mudanças de estado devem ser feitas
					// pela StateMachine
					this.stateMachine.nextTurn();
				}
			}
		}
	}

	// SHOULD IT BE HERE?????
	public boolean cantTransferAnymore() {
		for (AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
			if (aux.getPiecesThatCanBeRelocated() > 0) {
				return false;
			}
		}
		return true;
	}

	// SHOULD IT BE HERE?????
	private void playerTransfer() {
		for (AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
			if (aux.getNode().equals(this.stateMachine.nodeToTransferOrigin)) {
				aux.setPiecesThatCanBeRelocated(aux
						.getPiecesThatCanBeRelocated() - 1);
				teleportMusic.doIt();
				this.stateMachine.nodeToTransferOrigin.addNumberOfPieces(-1);
				this.stateMachine.nodeToTransferDestiny.addNumberOfPieces(1);

				if (aux.getPiecesThatCanBeRelocated() == 0) {
					this.stateMachine.nodeToTransferOrigin = null;
				}
				this.stateMachine.nodeToTransferDestiny = null;

				break;
			}
		}
	}

	private void playerPutPieceAt(int x, int y) {
		x = x - 12;
		y = y - 12;		
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 25) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())) {
					n.setNumberOfPieces(n.getNumberOfPieces() + 1);
					this.stateMachine.numberOfPiecesToPut = this.stateMachine.numberOfPiecesToPut - 1;

				}
			}
		}

		if (this.stateMachine.numberOfPiecesToPut <= 0) {
			this.stateMachine.listOfStructureAuxToPutPiecesOnContinent = StructureAuxToPutPiecesOnContinent
					.getListOfStructureAuxToPutPiecesOnContinent(
							this.stateMachine.currentPlayer, this.gameExecutor
									.getGraph().getContinentes());
			if (this.strategiesAlgorithm
					.isListOfStructureAuxToPutPiecesOnContinentIsEmpty(this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
				this.stateMachine.setStateGame(3);
				this.stateMachine.stage3();
			} else {
				this.stateMachine.setStateGame(6);
			}

		}
	}

	// REFERENTE AO ESTADO ? DO JOGADOR.
	// o ato de colocar a peça... estar no GameInterface... é meio estranho....
	// TODO: o nome deste método está correto?
	private void putPiecesContinent(int x, int y) {
		x = x - 12;
		y = y - 12;		
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 25) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())) {
					if (this.strategiesAlgorithm
							.canPutPieceOfContinent(
									n,
									this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
						// subtrair o numero de peças daquele continente na
						// estrutura
						for (StructureAuxToPutPiecesOnContinent str : this.stateMachine.listOfStructureAuxToPutPiecesOnContinent) {
							if (str.continent.getName().equals(
									n.getContinentName())) {
								if (str.numberOfPieces > 0) {
									str.numberOfPieces = str.numberOfPieces - 1; // TODO:
																					// i
																					// could
																					// create
																					// a
																					// function
																					// inside
																					// str
																					// to
																					// do
																					// it
									n.addNumberOfPieces(1);
								}
							}
						}
					}
				}
			}
		}
		if (strategiesAlgorithm
				.isThisStructureAuxToPutPiecesOnContinentUtilized(this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
			// TODO : quem deveria transitar os estados é a stateMachine.
			this.stateMachine.setStateGame(3);
			this.stateMachine.stage3();
		}
	}

	private void markNodeAsAttackerOrTarget(int x, int y) {
		x = x - 12;
		y = y - 12;		
		boolean wasASelectClick = false;
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 25) {

				
				
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())
						&& n.getNumberOfPieces() > 1) {
					
					
					this.stateMachine.nodeAttacker = n;
					this.stateMachine.nodeTarget = null;
					wasASelectClick = true;
					
					
				} else if ((!n.getPlayer()
						.equals(this.stateMachine.currentPlayer))) {
					this.stateMachine.nodeTarget = n;
					boolean flag = false;
					if (this.stateMachine.nodeAttacker != null) {
						for (Vertice v : this.stateMachine.nodeAttacker
								.getVertices()) {
							if (v.getDestiny() == n) {
								flag = true;
							}
						}
						if (!flag) {
							this.stateMachine.nodeAttacker = null;
						}
					}
					wasASelectClick = true;
				}
				
				break;
			}
		}
		if (!wasASelectClick) {
			this.stateMachine.nodeAttacker = null;
			this.stateMachine.nodeTarget = null;
		}
	}

	// TODO: criar um vetor de distancias... e pegar a menor.
	public void markNodeToTransfer(int x, int y) {
		x = x - 12;
		y = y - 12;
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 25) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())) {

					if (this.stateMachine.nodeToTransferOrigin == null) {

						for (AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
							if (aux.getNode().equals(n)) {
								if (aux.getPiecesThatCanBeRelocated() > 0) {
									this.stateMachine.nodeToTransferOrigin = n;
								}
							}
						}

					} else {

						if (this.stateMachine.nodeToTransferOrigin.isAdjacentTo(n)) {

							for (AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {

								if (this.stateMachine.nodeToTransferOrigin
										.equals(aux.getNode())) {
									if (aux.getPiecesThatCanBeRelocated() > 0) {
										this.stateMachine.nodeToTransferDestiny = n;
										break;
									}
								}
							}

						} else {
							for (AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
								if (aux.getPiecesThatCanBeRelocated() > 0) {
									this.stateMachine.nodeToTransferOrigin = n;
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (this.stateMachine.getStateGame() == 0) {
			g2d.drawImage(this.initialScreen, 0, 0, null);

			int i = 0;
			for (Checkbox checkbox : this.grpColorsSelect.list) {
				checkbox.setBackground(Color.WHITE);
				checkbox.setBounds((int) ((440 + i * 80)/getResize()) , (int)((440)/getResize()), (int)(70/getResize()), (int)(30/getResize()));
				i = i + 1;
			}

			i = 0;
			for (Checkbox checkbox : this.grpTotalPlayers.list) {
				checkbox.setBackground(Color.WHITE);
				checkbox.setBounds((int)((440 + i * 110)/getResize()), (int)(550/getResize()), (int)(100/getResize()), (int)(30/getResize()));
				i = i + 1;
			}

			i = 0;
			for (Checkbox checkbox : this.grpVelocityGame.list) {
				checkbox.setBackground(Color.WHITE);
				checkbox.setBounds((int)((440 + i * 110)/getResize()), (int)(660/getResize()), (int)(100/getResize()), (int)(30/getResize()));
				i = i + 1;
			}

			this.startButton.setBounds((int)(320), (int)(750/getResize()), 80, 40);

		} else {
			g2d.drawImage(imgBoard, 0, 0, null);
			g2d.drawImage(imgFooter, 0, (int)(790/getResize()), null);
			g2d.drawImage(imgPlayerObjetive, 0, (int)(790/getResize()), null);

			if (this.stateMachine.getStateGame() == 6) {
				if (this.stateMachine.currentPlayer.isPlayer()) {
					for (StructureAuxToPutPiecesOnContinent aux : this.stateMachine.listOfStructureAuxToPutPiecesOnContinent) {
						if (aux.numberOfPieces > 0) {
							g2d.drawImage(aux.continent.image, 0, 0, null);
						}

					}
				}
			}

			Iterator<Node> iterator;

			/* Desenhando circulos com seus respectivos exércitos */
			for (Player p : gameExecutor.getPlayers()) {
				iterator = p.getNodes().iterator();
				while (iterator.hasNext()) {
					Node n = iterator.next();
					drawCircle(g2d, n.x, n.y, p.getColorEnum().getColor(), 25,
							true);
				}
			}

			iterator = this.gameExecutor.getGraph().getNodes().iterator();

			while (iterator.hasNext()) {
				Node n = iterator.next();
				Color k = n.getPlayer().getColorEnum().getColor();
				if (k.equals(Color.BLACK) || k.equals(Color.RED)
						|| k.equals(Color.BLUE)) {
					g2d.setColor(Color.WHITE);
				} else {
					g2d.setColor(Color.BLACK);
				}

				g2d.drawString(Integer.toString(n.getNumberOfPieces()),
						(n.x + 10), n.y + 15);
			}

			/* Desenhando círculos dos steps */
			drawStepCircles(g2d);

			if (this.stateMachine.getStateGame() == 3
					|| this.stateMachine.getStateGame() == 4) {
				if (this.stateMachine.nodeAttacker != null) {
					g2d.drawImage(imgAttacker,
							this.stateMachine.nodeAttacker.x + 25,
							this.stateMachine.nodeAttacker.y, null);
				}
				if (this.stateMachine.nodeTarget != null) {
					g2d.drawImage(imgDefender,
							this.stateMachine.nodeTarget.x + 25,
							this.stateMachine.nodeTarget.y, null);
				}
			}

			if (this.stateMachine.getStateGame() == 7) {
				if (this.stateMachine.nodeToTransferOrigin != null) {
					g2d.drawImage(imgBlueCircle,
							this.stateMachine.nodeToTransferOrigin.x + 25,
							this.stateMachine.nodeToTransferOrigin.y, null);
				}
			}

			/* Desenhando flecha de guerra */
			Node tempNa = this.stateMachine.nodeAttacker;
			Node tempNt = this.stateMachine.nodeTarget;
			if (tempNa != null && tempNt != null) {
				drawArrow(g2d, tempNa.x, tempNa.y, tempNt.x, tempNt.y);
			}

			if (this.stateMachine.getStateGame() == 5) {
				g2d.setColor(Color.BLACK);
				g2d.drawString(
						Integer.toString(this.stateMachine.numberOfPiecesToPut)
								+ " peças", (int)(390/getResize()), (int)(910/getResize()));
			}

			g2d.drawImage(imgDados, (int)(600/getResize()), (int)(790/getResize()), null);
			DadosJogados d = this.gameExecutor.d;
			if (d != null) {
				int count = 0;
				g2d.setColor(Color.WHITE);
				for (int n : d.dadosAtaque) {
					n = n + 1;
					g2d.drawString(Integer.toString(n), (int)((642 + count * 100)/getResize()), (int)(835/getResize()));
					count = count + 1;
				}
				count = 0;
				g2d.setColor(Color.BLACK);
				for (int n : d.dadosDefense) {
					n = n + 1;
					g2d.drawString(Integer.toString(n), (int)((642 + count * 100)/getResize()), (int)(885/getResize()));
					count = count + 1;
				}
			}

		}
		setOpaque(false);
		super.paint(g);
		setOpaque(false);
	}

	public void drawStepCircles(Graphics g) {
		// será pintado com a Color.MAGENTA apenas o current state do
		// current player.
		Graphics2D g2d = (Graphics2D) g;
		int stateGame = this.stateMachine.getStateGame();

		if (stateGame == 3) {
			g2d.drawImage(imgEstadoAtacar, (int)(300/getResize()), (int)(790/getResize()), null);
		} else if (stateGame == 5) {
			g2d.drawImage(imgEstadoColocarPecas, (int)(300/getResize()), (int)(790/getResize()), null);
		} else if (stateGame == 6) {
			g2d.drawImage(imgEstadoColocarPecasContinente, (int)(300/getResize()), (int)(790/getResize()), null);
		} else if (stateGame == 7) {
			g2d.drawImage(imgEstadoRemanejamento, (int)(300/getResize()), (int)(790/getResize()), null);
		} else if (stateGame == 8) {
			if (this.stateMachine.currentPlayer.isPlayer()) {
				g2d.drawImage(imgEstadoVitoria, (int)(300/getResize()), (int)(790/getResize()), null);
			} else {
				g2d.drawImage(imgEstadoDerrota, (int)(300/getResize()), (int)(790/getResize()), null);
			}
		} else {
			g2d.drawImage(imgEstadoTurnoComputador, (int)(300/getResize()), (int)(790/getResize()), null);
		}

	}

	private void drawCircle(Graphics g1, int x, int y, Color color, int size,
			boolean fillOval) {
		Graphics2D g = (Graphics2D) g1.create();
		g.setColor(color);
		g.drawOval(x, y, size, size);
		if (fillOval) {
			g.fillOval(x, y, size, size);
		}
	}

	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		int ARR_SIZE = 20;

		Graphics2D g = (Graphics2D) g1.create();

		g.setColor(this.stateMachine.currentPlayer.getColorEnum().getColor());
		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}

	@Override
	public void run() {
		initGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void initButtons() {
		// colors
		this.grpColorsSelect = new CheckboxGroupList();

		Checkbox c1 = new Checkbox(ColorEnum.BLUE.getName(), grpColorsSelect,
				true);
		Checkbox c2 = new Checkbox(ColorEnum.RED.getName(), grpColorsSelect,
				false);
		Checkbox c3 = new Checkbox(ColorEnum.WHITE.getName(), grpColorsSelect,
				false);
		Checkbox c4 = new Checkbox(ColorEnum.BLACK.getName(), grpColorsSelect,
				false);
		Checkbox c5 = new Checkbox(ColorEnum.YELLOW.getName(), grpColorsSelect,
				false);
		Checkbox c6 = new Checkbox(ColorEnum.GREEN.getName(), grpColorsSelect,
				false);

		add(c1);
		add(c2);
		add(c3);
		add(c4);
		add(c5);
		add(c6);

		this.grpColorsSelect.list.add(c1);
		this.grpColorsSelect.list.add(c2);
		this.grpColorsSelect.list.add(c3);
		this.grpColorsSelect.list.add(c4);
		this.grpColorsSelect.list.add(c5);
		this.grpColorsSelect.list.add(c6);

		// players...
		this.grpTotalPlayers = new CheckboxGroupList();
		Checkbox c7 = new Checkbox("3", grpTotalPlayers, true);
		Checkbox c8 = new Checkbox("4", grpTotalPlayers, false);
		Checkbox c9 = new Checkbox("5", grpTotalPlayers, false);
		Checkbox c10 = new Checkbox("6", grpTotalPlayers, false);

		add(c7);
		add(c8);
		add(c9);
		add(c10);

		this.grpTotalPlayers.list.add(c7);
		this.grpTotalPlayers.list.add(c8);
		this.grpTotalPlayers.list.add(c9);
		this.grpTotalPlayers.list.add(c10);

		// velocity
		this.grpVelocityGame = new CheckboxGroupList();
		Checkbox c11 = new Checkbox("Lento", grpVelocityGame, true);
		Checkbox c12 = new Checkbox("Normal", grpVelocityGame, false);
		Checkbox c13 = new Checkbox("Rápido", grpVelocityGame, false);
		Checkbox c14 = new Checkbox("Muito Rápido", grpVelocityGame, false);

		add(c11);
		add(c12);
		add(c13);
		add(c14);
		this.grpVelocityGame.list.add(c11);
		this.grpVelocityGame.list.add(c12);
		this.grpVelocityGame.list.add(c13);
		this.grpVelocityGame.list.add(c14);
	}

	public static double getResize() {
		return resize;
	}

	public static void setResize(double resize) {
		GameInterface.resize = resize;
	}

}
