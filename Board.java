package ProgAssignment3;

import java.util.Random;

enum Pebble{
	X, O, EMPTY;
}

public class Board {
	private int boardSize;
	private Pebble[][] board;
	private int takenSpaces; 
	
	public Board(){
		boardSize = 5;
		board = new Pebble[5][5];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				board[i][j] = Pebble.EMPTY; 
			}
		}
		takenSpaces = 0;
	}
	
	public Board(int n){
		boardSize = n; 
		board = new Pebble[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				board[i][j] = Pebble.EMPTY; 
			}
		}
		takenSpaces = 0;
	}
	
	public void setCell(int xPos, int yPos, Pebble aPebble){
		board[xPos-1][yPos-1] = aPebble;
		takenSpaces++;
	}
	
	public Pebble getCell(int xPos, int yPos){
		return board[xPos-1][yPos-1];
	}
	
	public int getTakenCells(){
		return takenSpaces;
	}
	
	public boolean isEmpty(int xPos, int yPos){
		return (board[xPos][yPos] == Pebble.EMPTY);
	}
	
	public void clearBoard(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				board[i][j] = Pebble.EMPTY; 
			}
		}
		takenSpaces = 0; 
	}
	
	public boolean gameEnds(){
		if(fiveX() || fiveO())
			return true;
		if(takenSpaces == boardSize*boardSize)
			return true;
		return false;
	}
	
	public boolean fiveX(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(numInARowVertical(i, j, Pebble.X)==5 || numInARowHorizontal(i, j, Pebble.X) == 5)
					return true;
			}
		}
		return false;
	}
		
	public boolean fiveO(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(numInARowVertical(i, j, Pebble.O)==5 || numInARowHorizontal(i, j, Pebble.O) == 5)
					return true;
			}
		}
		return false;
	}
	
	public int fourX(){
		int num4InARow = 0;
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(numInARowLeftDiagonal(i, j, Pebble.X)==4)
					num4InARow++;
				if(numInARowRightDiagonal(i, j, Pebble.X) == 4)
					num4InARow++;
			}
		}
		return num4InARow; 
	}
	
	public int fourO(){
		int num4InARow = 0;
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(numInARowLeftDiagonal(i, j, Pebble.O)==4)
					num4InARow++;
				if(numInARowRightDiagonal(i, j, Pebble.O) == 4)
					num4InARow++;
			}
		}
		return num4InARow; 
	}
	
	Pebble[][] getBoard(){
		return board;
	}
	
	public int[] randomClose() {
		// TODO Auto-generated method stub
		int[] pos = randomEmpty(); 		
		
		while(!hasXOAdjacent24(pos[0]-1, pos[1]-1))
			pos = randomEmpty();
		
		return pos;	
	}
	
	private boolean hasXOAdjacent24(int x, int y){
		for(int i = x-2; i <= x+2; i++){
			for(int j = y-2; j<= y+2; j++){
				if(isValidPos(i,j))
					if(board[i][j] != Pebble.EMPTY)
						return true;
			}
		}
		return false;
	}
	
	//randomly select an empty cell such that at least 1 of 8 adjacent cells is not empty
	public int[] randomAdjacent() {
		// TODO Auto-generated method stub
		int[] pos = randomEmpty(); 		
		
		while(!hasXOAdjacent8(pos[0]-1, pos[1]-1))
			pos = randomEmpty();
		
		return pos;
	}
	
	private boolean hasXOAdjacent8(int x, int y){
		if(isValidPos(x+1, y+1))
			if(board[x+1][y+1] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x+1, y))
			if(board[x+1][y] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x+1, y-1))
			if(board[x+1][y-1] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x, y-1))
			if(board[x][y-1] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x-1, y-1))
			if(board[x-1][y-1] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x-1, y))
			if(board[x-1][y] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x-1, y+1))
			if(board[x-1][y+1] != Pebble.EMPTY)
				return true;
		
		if(isValidPos(x, y+1))
			if(board[x][y+1] != Pebble.EMPTY)
				return true;
		
		return false;
	}

	//Select an empty cell
	public int[] randomEmpty() {
		int[] pos = new int[2];
		
		Random r = new Random();
		int x = r.nextInt(boardSize)+1;
		int y = r.nextInt(boardSize)+1;		
		System.out.println(x + ", " + y);
		while(getCell(x,y) != Pebble.EMPTY){
			x = r.nextInt(boardSize)+1;
			y = r.nextInt(boardSize)+1;
		}
			
		pos[0] = x; pos[1] = y;
		
		
		return pos;
	}
	
	public int[] clever(Pebble color){
		if(takenSpaces == 0){
			return randomEmpty();
		}
		
		Pebble otherColor = Pebble.EMPTY;
		if(color == Pebble.X)
			otherColor = Pebble.O;
		if(color == Pebble.O)
			otherColor = Pebble.X;
		
		int[] pos = canWin(color);
		if(pos != null){
			pos[0]++; pos[1]++;
			return pos; 
		}
				
		pos = canWin(otherColor);
		if(pos != null){
			pos[0]++; pos[1]++;
			return pos; 
		}
		
		pos = randomAdjacent();
		return pos; 
					
	}
	
	private int[] canWin(Pebble p){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(board[i][j] == Pebble.EMPTY){
					if(checkFour(i, j, p)){
						int[] pos = {i,j};
						return pos;
					}
				}
			}
		}
		
		return null; 
	}
	
	private boolean checkFour(int x, int y, Pebble p){
		int upTo4 = 0;
		int i = x+1;
		int j = y;
		if(isValidPos(i,j)){
			while(board[i][j] == p){
				upTo4++;
				i++;
				if(!isValidPos(i,j))
					break;
			}
		}
		if(upTo4 == 4)
			return true;
		
		i = x-1;
		if(isValidPos(i,j)){
			while(board[i][j] == p){
				upTo4++;
				i--;
				if(!isValidPos(i,j))
					break;
			}
		}
		if(upTo4 == 4)
			return true;
		
		
		upTo4 = 0;
		i = x;
		j = y+1;
		if(isValidPos(i,j)){
			while(board[i][j] == p){
				upTo4++;
				j++;
				if(!isValidPos(i,j))
					break;
			}
		}
		if(upTo4 == 4)
			return true;
		
		
		j = y-1;
		if(isValidPos(i,j)){
			while(board[i][j] == p){
				upTo4++;
				j--;
				if(!isValidPos(i,j))
					break;
			}
		}
		if(upTo4 == 4)
			return true;
		
		return false;

		
	}
	
	
	//PRIVATE HELPER METHODS START HERE
	private int numInARowVertical(int x, int y, Pebble p){
		int i = x;
		int inARow = 0;
		while(board[i][y] == p){
			inARow++;
			i++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(i,y))
				break;
		}
		return inARow;
	}
	private int numInARowHorizontal(int x, int y, Pebble p){
		int j = y; 
		int inARow = 0;
		while(board[x][j] == p){
			inARow++;
			j++;
			if(inARow == 5)
				return inARow;
			if(!isValidPos(x,j))
				break;
		}
		return inARow;
	}
	private int numInARowRightDiagonal(int x, int y, Pebble p){
		int i = x;
		int j = y;
		int inARow = 0; 
		while(board[i][j] == p){
			inARow++;
			j++;
			i++;
			if(inARow == 4)
				return inARow;
			if(!isValidPos(i,j))
				break;
		} 
		return inARow;
	}
	private int numInARowLeftDiagonal(int x, int y, Pebble p){
		int i = x;
		int j = y;
		int inARow = 0; 
		while(board[i][j] == p){
			inARow++;
			j--;
			i++;
			if(inARow == 4)
				return inARow;
			if(!isValidPos(i,j))
				break;
		} 
		return inARow; 
	}
	private boolean isValidPos(int i, int j){
		if(i < boardSize && j < boardSize && i >= 0 && j >=0)
			return true;
		return false;
	}

	
	
}
