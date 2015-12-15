package ProgAssignment3;

public class TestMethod {
	public static void main(String[] args){
		Pebble e = Pebble.EMPTY;
		Pebble x = Pebble.X;
		Pebble o = Pebble.O;
		/*Pebble[][] board = {{e,e,e,e,e,e,e,e,e},
							{e,e,e,e,e,e,e,e,e},
							{e,e,e,e,e,e,e,e,e},
							{e,e,e,e,e,e,e,e,e},
							{e,e,e,e,o,o,o,e,e},
							{e,e,e,o,o,o,e,e,e},
							{e,e,o,e,e,e,o,e,e},
							{e,o,e,e,o,e,e,e,e},
							{o,e,e,e,o,e,e,e,e}};*/
		Pebble[][] board = {{x,e,e,e,o,e,e,x,e},
							{e,o,x,e,x,o,e,e,e},
							{e,x,o,o,o,x,o,e,e},
							{e,x,e,e,e,e,e,e,e},
							{e,x,e,e,o,o,o,e,e},
							{e,x,e,o,o,o,e,e,e},
							{e,e,o,e,e,e,o,e,e},
							{e,o,e,e,o,e,e,e,e},
							{o,e,e,e,o,e,e,e,e}};
		System.out.println(numInARow(2,0,e,board));
		System.out.println(numInARowHorizontal(4,4,x,board));
		System.out.println(numInARowVertical(4,4,x,board));
		System.out.println(numInARowRightDiagonal(4,4,x,board));
		System.out.println(numInARowLeftDiagonal(4,4,x,board));

	}
	
	
	//NOTE: We only care about Vertical/ Horizontal
	/**
	 * 
	 * @param x valid position on board
	 * @param y valid position on board
	 * @param p Pebble type we are trying to find in a row
	 * @param board 
	 * @return number of Pebbles of type p in a row
	 */
	private static int numInARowVertical(int x, int y, Pebble p, Pebble[][] board){
		int i = x;
		int inARow = 0;
		while(board[i][y] == p){
			inARow++;
			i++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(i,y,9))
				break;
		}
		return inARow;
	}
	private static int numInARowHorizontal(int x, int y, Pebble p, Pebble[][] board){
		int j = y; 
		int inARow = 0;
		while(board[x][j] == p){
			inARow++;
			j++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(x,j,9))
				break;
		}
		return inARow;
	}
	private static int numInARowRightDiagonal(int x, int y, Pebble p, Pebble[][] board){
		int i = x;
		int j = y;
		int inARow = 0; 
		while(board[i][j] == p){
			inARow++;
			j++;
			i++;
			if(inARow == 4)
				return inARow;
			if(!isValidPos(i,j,9))
				break;
		} 
		return inARow;
	}
	private static int numInARowLeftDiagonal(int x, int y, Pebble p, Pebble[][] board){
		int i = x;
		int j = y;
		int inARow = 0; 
		while(board[i][j] == p){
			inARow++;
			j--;
			i++;
			if(inARow == 4)
				return inARow;
			if(!isValidPos(i,j,9))
				break;
		} 
		return inARow; 
	}
	
	//METHOD IN BOARD.JAVA except added in parameter board.
	private static int numInARow(int x, int y, Pebble p, Pebble[][] board){
		int max = 0;
		int i = x;
		int j = y; 
		while(board[i][j] == p){
			max++;
			i++;
			if(max == 5)
				return max;
			if(!isValidPos(i,j,9))
				break;
		}
		
		int inARow = 0;
		i = x;
		while(board[i][j] == p){
			inARow++;
			j++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(i,j,9))
				break;
		}
		max = Math.max(max, inARow);
		
		inARow = 0;
		j = y;
		while(board[i][j] == p){
			inARow++;
			j++;
			i++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(i,j,9))
				break;
		}
		max =  Math.max(max, inARow);

		inARow = 0;
		j = y;
		i = x;
		while(board[i][j] == p){
			inARow++;
			j--;
			i++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(i,j,9))
				break;
		}
		return Math.max(max, inARow);		
	}
	
	private static boolean isValidPos(int i, int j, int boardSize){
		if(i < boardSize && j < boardSize && i >= 0 && j >=0)
			return true;
		return false;
	}

}
