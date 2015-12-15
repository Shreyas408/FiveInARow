package ProgAssignment3;

public abstract class Player {
	protected Pebble color; 
	protected Board board; 
	
	Player(Pebble color, Board board){
		this.board = board;
		this.color = color; 
	}
	
	public int[] makeMove() {
		return null;
	}
	
	//makeMove??
	
}
