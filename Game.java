package ProgAssignment3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Game implements ActionListener, MouseListener{
	
	private Board board; 
	private Player player1;
	private Player player2;
	
	private JFrame window; 
	private GamePanel gamePanel;
	private int boardSize; 
    private int cellSize;
    
    private boolean isPlayer1 = true; 
    private boolean isP1Human = true;
    private boolean isP2Human = true;

		
	public Game(){
		init(5);
		makeGUI();
	}
	public Game(int boardSize){
		init(boardSize);
		makeGUI();
	}
	
	private void init(int boardSize){
		this.boardSize = boardSize;
		board = new Board(boardSize);
		cellSize = 500/boardSize;
		player1 = new HumanPlayer(Pebble.X, board);
		player2 = new HumanPlayer(Pebble.O, board);
	}
	
	public void makeGUI(){
		window = new JFrame("Five in a Row");
		window.setSize(540,680);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the menu bar for restarting games, etc
		JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game", true);
        
        JMenuItem newGame = new JMenuItem("New Game", 'n');
        JMenuItem changeBoardSize = new JMenuItem("Board Size", 't');
        
        gameMenu.add(newGame); 
        gameMenu.add(changeBoardSize);
        
        menuBar.add(gameMenu); 
        
        //Create a main Panel to include Player options
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        
        //Make the Game PAnel
        gamePanel = new GamePanel();
        gamePanel.setBounds(20, 20, 500,500);
        gamePanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(gamePanel);

        //Make Radio Buttons
        JPanel player1 = new JPanel();
        player1.setBorder(BorderFactory.createTitledBorder("Player1"));
        player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
        player1.setBounds(100, 535, 150, 75);
        
        JPanel player2 = new JPanel();
        player2.setBorder(BorderFactory.createTitledBorder("Player2"));
        player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));
        player2.setBounds(290, 535, 150, 75);
        
        JRadioButton p1H = new JRadioButton("Human");
        JRadioButton p1S = new JRadioButton("Computer");
        ButtonGroup p1 = new ButtonGroup();
        p1.add(p1H);p1.add(p1S);
        p1H.setSelected(true);
        p1H.addActionListener(this);p1S.addActionListener(this);
        player1.add(p1H); player1.add(p1S);
        mainPanel.add(player1);
        
        JRadioButton p2H = new JRadioButton("Human");
        JRadioButton p2S = new JRadioButton("Computer");
        ButtonGroup p2 = new ButtonGroup();
        p2.add(p2H);p2.add(p2S);
        p2H.setSelected(true);
        p2H.addActionListener(this);p2S.addActionListener(this);
        p2H.setActionCommand("Human2"); p2S.setActionCommand("Cumputer2");
        player2.add(p2H); player2.add(p2S);
        mainPanel.add(player2);
        
        
        //Add everything to Window
        window.setJMenuBar(menuBar);
        window.getContentPane().add(mainPanel);
		window.setVisible(true);
		
		newGame.addActionListener(this);
		changeBoardSize.addActionListener(this);
		gamePanel.addMouseListener(this);
		

	}
	
	private class GamePanel extends JPanel{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, this.getWidth()-2, this.getHeight()-2);

            g.setColor(Color.BLACK);
            int cellSize = 500/boardSize;
            for (int x = 0; x < this.getWidth(); x += cellSize)
                g.drawLine(x, 0, x, this.getHeight());

            for (int y = 0; y < this.getHeight(); y += cellSize)
                g.drawLine(0, y, this.getWidth(), y);
        }
	}
	
	
	//LISTENERS START HERE
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if(str.equals("New Game"))
			newGame();
		if(str.equals("Board Size"))
			newBoard();
		if(str.equals("Computer"))
			p1ToComputer();
		if(str.equals("Cumputer2"))
			p2ToComputer();
		if(str.equals("Human"))
			p1ToHuman();
		if(str.equals("Human2"))
			p2ToHuman();

	}
	
	private void p1ToComputer(){
		player1 = new Sammy(Pebble.X, board);
		nextMove();
		isP1Human = false;
	}
	private void p1ToHuman(){
		player1 = new HumanPlayer(Pebble.X, board);
		isP1Human = true;
	}
	private void p2ToComputer(){
		player2 = new Sammy(Pebble.O, board);
		nextMove();
		isP2Human = false;
	}
	private void p2ToHuman(){
		player2 = new HumanPlayer(Pebble.O, board);
		isP2Human = true;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + ", " + e.getY());
		if(isP1Human || isP2Human){
		int x = e.getX(); int y = e.getY();
		int x2 = x/cellSize; int y2 = y/cellSize; 
		System.out.println("Check Cell: " + (x2+1) + ", " + (y2+1));
		if(board.getCell(x2+1,y2+1) != Pebble.EMPTY)
			return;
		x = x - x%cellSize; y = y- y%cellSize;
        try {
        	Image img;
        	if(isPlayer1){
        		img = ImageIO.read(new File("X.png"));
        		isPlayer1 = false;
				board.setCell(x2+1,y2+1, Pebble.X);
        	}else{
        		img = ImageIO.read(new File("O.png"));
        		isPlayer1 = true;
				board.setCell(x2+1,y2+1, Pebble.O);

        	}
        		
			gamePanel.getGraphics().drawImage(img, x+1, y+1, cellSize-2, cellSize-2, Color.WHITE, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		}
		if(gameOver()){
			newGame();
			return;
		}
        nextMove();
        
	}
	//DONT CARE ABOUT THESE METHODS
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void nextMove(){
		if(isPlayer1 && !isP1Human){
			int[] pos = player1.makeMove();
			if(pos == null)
				return;
			
			int x = pos[0]-1; int y = pos[1]-1;
			board.setCell(x+1,y+1, Pebble.X);
			x = x*cellSize; y = y*cellSize;
	        try {
				gamePanel.getGraphics().drawImage(ImageIO.read(new File("X.png")), x+1, y+1, cellSize-2, cellSize-2, Color.WHITE, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        isPlayer1 = false;  	

		}else if(!isPlayer1 && !isP2Human){
			System.out.println("Player 2's turn");
			int[] pos = player2.makeMove();
			System.out.println("Player 2 made a move at " + (pos[0]) + ", " + (pos[1]));
			if(pos == null)
				return;
			
			int x = pos[0]-1; int y = pos[1]-1;
			board.setCell(x+1,y+1, Pebble.O);
			x = x*cellSize; y = y*cellSize;
			try {
				gamePanel.getGraphics().drawImage(ImageIO.read(new File("O.png")), x+1, y+1, cellSize-2, cellSize-2, Color.WHITE, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        isPlayer1 = true;  	

		}
		if(gameOver()){
			newGame();
		}
			
	}
	
	public void newGame(){
		gamePanel.repaint();
		board.clearBoard();
		isPlayer1 = true;
	}
	
	public void newBoard(){
		String answer = JOptionPane.showInputDialog("Input size of grid");
		boardSize = Integer.parseInt(answer);
		cellSize = 500/boardSize;
		board = new Board(boardSize);
		isPlayer1 = true;
		if(isP1Human)
			player1 = new HumanPlayer(Pebble.X, board);
		else
			player1 = new Sammy(Pebble.X, board);
		
		if(isP2Human)
			player2 = new HumanPlayer(Pebble.O, board);
		else
			player2 = new Sammy(Pebble.X, board);
		
		newGame();
	}
	
	public boolean gameOver(){
		if(board.fiveO()){
            JOptionPane.showMessageDialog(null, "Player 2 Wins!");
            return true;
		}
		
		if(board.fiveX()){
            JOptionPane.showMessageDialog(null, "Player 1 Wins!");
            return true;
		}
		
		if(board.getTakenCells() == boardSize*boardSize){
			int O4 = board.fourO();
			int X4 = board.fourX();
			if(O4 > X4){
	            JOptionPane.showMessageDialog(null, "Player 2 Wins! : " + O4 + ", " + X4);
			}
			else if(X4 > O4){
	            JOptionPane.showMessageDialog(null, "Player 1 Wins!");
			}
			else{
	            JOptionPane.showMessageDialog(null, "Game Over. It's a tie.");
			}

			return true;
		}
		
		return false;
	}
	
	public static void main(String args[]){
		Game g = new Game();
	}
	
}
