package basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import objetives.ConquerTwentyFourCountriesObjetive;
import objetives.PutTwoPiecesOnEighteenCountriesObjetive;
import entities.Continent;
import entities.Graph;
import entities.Node;
import entities.Player;
import entities.Vertice;
import algorithm.MathAlgorithm;
import algorithm.StrategiesAlgorithm;
import auxiliaryEntities.AuxPutOrRelocatePiece;
import auxiliaryEntities.DadosJogados;
import auxiliaryEntities.StructureAuxToPutPiecesOnContinent;

public class GameInterface extends JPanel implements ActionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameExecutor gameExecutor;

	private Thread animator;
	private Timer time;
	
	private StateMachine stateMachine;

	// SHOULD IT BE HERE? 
	public StrategiesAlgorithm strategiesAlgorithm;

	/* Inicio: Conjunto de imagens utilizadas para a interface gráfica */
	public Image imgBoard;
	public Image imgFooter;
	public Image imgAttacker;
	public Image imgDefender;
	public Image imgBlueCircle;

	public GameInterface() {
		Graph graph = Graph.getGraphWithDefaultConfiguration();
		gameExecutor = new GameExecutor(graph);
		this.stateMachine = new StateMachine(gameExecutor);
		
		// sei lá se isso deveria estar aqui.... ZZZ
		this.strategiesAlgorithm = new StrategiesAlgorithm(this.gameExecutor.getGraph());
		


		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				MouseClicked(evt);
			}
		});

		ImageIcon icon = new ImageIcon(getClass().getResource(
				"/images/paintedCropped.png"));
		this.imgBoard = icon.getImage();

		icon = new ImageIcon(getClass().getResource("/images/footer.png"));
		this.imgFooter = icon.getImage();

		icon = new ImageIcon(getClass().getResource(
				"/images/swordWithLayerCleared.png"));
		this.imgAttacker = icon.getImage();

		icon = new ImageIcon(getClass().getResource(
				"/images/shieldWithLayerCleared.png"));
		this.imgDefender = icon.getImage();
		
		icon = new ImageIcon(getClass().getResource(
				"/images/circuloazul.png"));
		this.imgBlueCircle = icon.getImage();
	}
	

	public void initGame() {
		this.stateMachine.initGameStateOne();
	}	
 
	// OK
	public void initAnimation() {
		// time = new Timer(1000, this);
		// time.start();

		animator = new Thread(this);
		animator.start();
	}

	// OK
	private void jumpStepButtonClicked(java.awt.event.MouseEvent evt) {
		if (this.stateMachine.getStateGame() == 3) {
			this.stateMachine.listOfAuxPutOrRelocatePiece = AuxPutOrRelocatePiece.getStructureToRelocatePieces(this.stateMachine.currentPlayer);
			
			// TODO : quem deve transitar de um estado para o outro é o stateMachine....
			this.stateMachine.setStateGame(7);
		} else if (this.stateMachine.getStateGame() == 7)  {
			this.stateMachine.setStateGame(2);
			this.stateMachine.currentIndex = this.stateMachine.currentIndex + 1;
			this.stateMachine.stage2();
		}
	}


	/* Estado 3... jogador está escolhendo país para realizar um ataque. */
	private void MouseClicked(java.awt.event.MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		if (x > 1004 && y > 795) {
			jumpStepButtonClicked(evt);

		} else if (this.stateMachine.getStateGame() == 3) {
			markNodeAsAttackerOrTarget(x, y);
			if (this.stateMachine.nodeAttacker != null && this.stateMachine.nodeTarget != null) {
				// should it be like it ? 
				this.gameExecutor.attack(this.stateMachine.nodeAttacker, this.stateMachine.nodeTarget);
				this.stateMachine.theGameHasEnded();
			} else {
			}
		} else if (this.stateMachine.getStateGame() == 5) { // estado para colocar peças, jogador...
			playerPutPieceAt(x, y);
		} else if (this.stateMachine.getStateGame() == 6) { // estado para colocar peças continente
			putPiecesContinent(x,y);
		} else if (this.stateMachine.getStateGame() == 7) {
			markNodeToTransfer(x,y);
			if(this.stateMachine.nodeToTransferOrigin != null && this.stateMachine.nodeToTransferDestiny != null) {
				playerTransfer();
				if(cantTransferAnymore()) {
					// TODO : todas essas mudanças de estado devem ser feitas pela StateMachine
					this.stateMachine.setStateGame(2);
					this.stateMachine.currentIndex = this.stateMachine.currentIndex + 1;
					this.stateMachine.stage2();
				}
			}
		}
	}
	
	// SHOULD IT BE HERE?????
	public boolean cantTransferAnymore() {
		for(AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
			if(aux.getPiecesThatCanBeRelocated() > 0) {
				return false;
			}
		}
		return true;
	}
	
	// SHOULD IT BE HERE?????
	private void playerTransfer() {
		for(AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
			if(aux.getNode().equals(this.stateMachine.nodeToTransferOrigin)) {
				aux.setPiecesThatCanBeRelocated(aux.getPiecesThatCanBeRelocated() - 1);
				
				this.stateMachine.nodeToTransferOrigin.addNumberOfPieces(-1);
				this.stateMachine.nodeToTransferDestiny.addNumberOfPieces(1);

				this.stateMachine.nodeToTransferOrigin = null;
				this.stateMachine.nodeToTransferDestiny = null;
				
				break;
			}
		}		
	}




	private void playerPutPieceAt(int x, int y) {
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 50) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())) {
					n.setNumberOfPieces(n.getNumberOfPieces() + 1);
					this.stateMachine.numberOfPiecesToPut = this.stateMachine.numberOfPiecesToPut - 1;

				}
			}
		}

		if (this.stateMachine.numberOfPiecesToPut == 0) {
			this.stateMachine.listOfStructureAuxToPutPiecesOnContinent = StructureAuxToPutPiecesOnContinent.getListOfStructureAuxToPutPiecesOnContinent(this.stateMachine.currentPlayer, this.gameExecutor.getGraph().getContinentes());
			if(this.strategiesAlgorithm.isListOfStructureAuxToPutPiecesOnContinentIsEmpty(this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
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
		for(Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 50) {
				if (n.getPlayer().getColorEnum().equals(this.stateMachine.currentPlayer.getColorEnum())) {
					if(this.strategiesAlgorithm.canPutPieceOfContinent(n, this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
						// subtrair o numero de peças daquele continente na estrutura
						for(StructureAuxToPutPiecesOnContinent str : this.stateMachine.listOfStructureAuxToPutPiecesOnContinent) {
							if(str.continent.getName().equals(n.getContinentName())) {
								if(str.numberOfPieces > 0) {
									str.numberOfPieces = str.numberOfPieces - 1;   // TODO: i could create a function inside str to do it
									n.addNumberOfPieces(1);
								}
							}
						}
					}	
				}
			}
		}
		if(strategiesAlgorithm.isThisStructureAuxToPutPiecesOnContinentUtilized(this.stateMachine.listOfStructureAuxToPutPiecesOnContinent)) {
			// TODO : quem deveria transitar os estados é a stateMachine.
			this.stateMachine.setStateGame(3);
			this.stateMachine.stage3();
		}
	}


	private void markNodeAsAttackerOrTarget(int x, int y) {
		boolean wasASelectClick = false;

		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 50) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum()) && n.getNumberOfPieces() > 1) {
					this.stateMachine.nodeAttacker = n;
					boolean flag = false;
					if (this.stateMachine.nodeTarget != null) {
						for (Vertice v : this.stateMachine.nodeAttacker.getVertices()) {
							if (v.getDestiny() == this.stateMachine.nodeTarget) {
								flag = true;
							}
						}
						if (!flag) {
							this.stateMachine.nodeTarget = null;
						}
					}
				} else {
					this.stateMachine.nodeTarget = n;
					boolean flag = false;
					if (this.stateMachine.nodeAttacker != null) {
						for (Vertice v : this.stateMachine.nodeTarget.getVertices()) {
							if (v.getDestiny() == this.stateMachine.nodeAttacker) {
								flag = true;
							}
						}
						if (!flag) {
							this.stateMachine.nodeAttacker = null;
						}
					}
				}
				wasASelectClick = true;
				break;
			}
		}
		if (!wasASelectClick) {
			this.stateMachine.nodeAttacker = null;
			this.stateMachine.nodeTarget = null;
		}
	}
	

	public void markNodeToTransfer(int x,int y) {
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			int distance = MathAlgorithm.distanceBetween(x, y, n.x, n.y);
			if (distance < 50) {
				if (n.getPlayer().getColorEnum()
						.equals(this.stateMachine.currentPlayer.getColorEnum())) {
					if(this.stateMachine.nodeToTransferOrigin == null) {
						for(AuxPutOrRelocatePiece aux : this.stateMachine.listOfAuxPutOrRelocatePiece) {
							if(aux.getNode().equals(n)) {
								if(aux.getPiecesThatCanBeRelocated() > 0) {
									this.stateMachine.nodeToTransferOrigin = n;
								}		
							}
						}
					} else {
						if(this.strategiesAlgorithm.isThisNodeAdjacentTo(this.stateMachine.nodeToTransferOrigin, n)) {
							this.stateMachine.nodeToTransferDestiny = n;
						} else {
							this.stateMachine.nodeToTransferOrigin = n;
						}
					}
				}
			}
		}
	}

	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(imgBoard, 0, 0, null);
		g2d.drawImage(imgFooter, 0, 790, null);

		/* Desenhando circulos com seus respectivos exércitos */
		for (Player p : gameExecutor.getPlayers()) {
			for (Node n : p.getNodes()) {
				drawCircle(g2d, n.x, n.y, p.getColorEnum().getColor(), 25, true);
			}
		}
		for (Node n : this.gameExecutor.getGraph().getNodes()) {
			if(n.getPlayer().getColorEnum().getColor().equals(Color.BLACK)) {
				g2d.setColor(Color.WHITE);
			} else {
				g2d.setColor(Color.BLACK);
			}
			
			g2d.drawString(Integer.toString(n.getNumberOfPieces()), n.x + 10,
					n.y + 15);
		}

		/* Desenhando círculos dos steps */
		drawStepCircles(g2d);

		if (this.stateMachine.getStateGame() == 3 || this.stateMachine.getStateGame() == 4) {
			if (this.stateMachine.nodeAttacker != null) {
				g2d.drawImage(imgAttacker, this.stateMachine.nodeAttacker.x + 25,
						this.stateMachine.nodeAttacker.y, null);
			}
			if (this.stateMachine.nodeTarget != null) {
				g2d.drawImage(imgDefender, this.stateMachine.nodeTarget.x + 25,
						this.stateMachine.nodeTarget.y, null);
			}
		}
		
		if(this.stateMachine.getStateGame() == 7) {
			if(this.stateMachine.nodeToTransferOrigin != null) {
				g2d.drawImage(imgBlueCircle, this.stateMachine.nodeToTransferOrigin.x + 25,
						this.stateMachine.nodeToTransferOrigin.y,null);
			}
		}
		setOpaque(false);
		super.paint(g2d);
		setOpaque(true);
	}

	public void drawStepCircles(Graphics g) {
		// será pintado com a Color.MAGENTA apenas o current state do
		// current player.
		int stateGame = this.stateMachine.getStateGame();
		
		if(stateGame == 1 || stateGame == 2 || stateGame == 5) {
			drawCircle(g, 90, 790, Color.MAGENTA, 30, true);
		} else {
			drawCircle(g, 90, 790, Color.BLUE, 30, true);
		}
		
		
		if(stateGame == 6) { 
			drawCircle(g, 290, 790, Color.MAGENTA, 30, true);
		} else {
			drawCircle(g, 290, 790, Color.BLUE, 30, true);
		}
		
		drawCircle(g, 490, 790, Color.BLUE, 30, true);
		
		if(stateGame == 3 || stateGame == 4) { 
			drawCircle(g, 690, 790, Color.MAGENTA, 30, true);
		} else {
			drawCircle(g, 690, 790, Color.BLUE, 30, true);
		}
		
		if(stateGame == 7) {
			drawCircle(g, 890, 790, Color.MAGENTA, 30, true);
		} else {
			drawCircle(g, 890, 790, Color.BLUE, 30, true);
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
		int ARR_SIZE = 4;

		Graphics2D g = (Graphics2D) g1.create();

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
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.update(this.getGraphics());
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		paint(this.getGraphics());
	}

}
