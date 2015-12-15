package ProgAssignment3;

public class Sammy extends Player {

	Sammy(Pebble color, Board board) {
		super(color, board);
	}
	
	@Override
	public int[] makeMove(){
		return board.clever(color);
		
	}
	
}
