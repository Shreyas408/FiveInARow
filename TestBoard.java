package ProgAssignment3;

import static org.junit.Assert.*;

import org.junit.*;

//import Board.Pebble;

public class TestBoard {

	@Test
	public void testSetGetCell() {
		final Board board2 = new Board(2);
		final Pebble expected = Pebble.O;
		board2.setCell(1, 1, Pebble.O);
		assertEquals(expected, board2.getCell(1, 1));
	}

	@Test
	public void TestIsEmpty() {
		final Board board2 = new Board(2);
		final Pebble expected = Pebble.EMPTY;
		assertEquals(expected, board2.getCell(1, 1));

	}

	@Test
	public void testClearBoard() {
		final Board board2 = new Board(2);
		board2.setCell(1, 1, Pebble.X);
		board2.setCell(2, 2, Pebble.X);
		board2.clearBoard();
		final Pebble expected = Pebble.EMPTY;
		assertTrue(expected == board2.getCell(1, 1) && expected == board2.getCell(2, 2));
	}

	@Test
	public void testGameEndsFiveAcross() {
		final Board board2 = new Board(5);
		for (int i = 1; i <= 5; i++)
			board2.setCell(2, i, Pebble.X);
		assertTrue(board2.gameEnds());
	}

	@Test
	public void testGameEndsFiveDown() {
		final Board board2 = new Board(5);
		for (int i = 1; i <= 5; i++)
			board2.setCell(i, 2, Pebble.O);
		assertTrue(board2.gameEnds());
	}

	@Test
	public void testGameEndsFull() {
		final Board board2 = new Board(2);
		for (int i = 1; i <= 2; i++)
			for (int j = 1; j <= 2; j++)
				board2.setCell(i, j, Pebble.X);
		assertTrue(board2.gameEnds());
	}

	@Test
	public void testGameEndsFalse() {
		final Board board2 = new Board(2);
		assertFalse(board2.gameEnds());
	}

	@Test
	public void testFiveX() {
		final Board board2 = new Board(5);
		for (int i = 1; i <= 5; i++)
			board2.setCell(i, 2, Pebble.X);
		assertTrue(board2.fiveX());
	}

	@Test
	public void testFiveO() {
		final Board board2 = new Board(5);
		for (int i = 1; i <= 5; i++)
			board2.setCell(2, i, Pebble.O);
		assertTrue(board2.fiveO());
	}

	@Test
	public void testFourO() {
		final Board board2 = new Board(10);
		for (int i = 1; i <= 10; i++)
			for (int j = 1; j <= 10; j++)
				board2.setCell(i, j, Pebble.X);

		int x = 2;
		int y = 3;
		for (int i = 0; i < 4; i++)
			board2.setCell(x++, y++, Pebble.O);

		x = 1;
		y = 5;
		for (int i = 0; i < 4; i++)
			board2.setCell(x++, y++, Pebble.O);
		assertEquals(2, board2.fourO());
	}

	@Test
	public void testFourX() {
		final Board board2 = new Board(10);
		for (int i = 1; i <= 10; i++)
			for (int j = 1; j <= 10; j++)
				board2.setCell(i, j, Pebble.O);

		int x = 9;
		int y = 1;
		for (int i = 0; i < 4; i++)
			board2.setCell(x--, y++, Pebble.X);
		assertEquals(1, board2.fourX());
	}

	@Test
	public void testRandomEmpty() {
		final Board board2 = new Board(3);
		board2.setCell(1, 1, Pebble.O);
		board2.setCell(1, 3, Pebble.O);
		board2.setCell(3, 3, Pebble.O);
		int[] idx = board2.randomEmpty();
		int x = idx[0];
		int y = idx[1];
		assertEquals(Pebble.EMPTY, board2.getCell(x, y));
	}

	@Test
	public void testRandomAdjacent() {
		final int size = 5;
		final Board board2 = new Board(size);
		board2.setCell(1, 1, Pebble.O);
		board2.setCell(5, 5, Pebble.O);
		int[] idx = board2.randomAdjacent();
		int x = idx[0];
		int y = idx[1];
		boolean empty = true;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if ((i == x && j == y) || i < 1 || j < 1 || i > size || j > size) {
					continue;
				}
				if (board2.getCell(i, j) != Pebble.EMPTY)
					empty = false;
			}

		}
		assertFalse(empty);
		assertEquals(Pebble.EMPTY, board2.getCell(x, y));
	}

	@Test
	public void testRandomClose() {
		final int size = 7;
		final Board board2 = new Board(size);
		board2.setCell(1, 1, Pebble.O);
		board2.setCell(7, 7, Pebble.O);
		int[] idx = board2.randomClose();
		int x = idx[0];
		int y = idx[1];
		boolean empty = true;
		for (int i = x - 2; i <= x + 2; i++) {
			for (int j = y - 2; j <= y + 2; j++) {
				if ((i == x && j == y) || i < 1 || j < 1 || i > size || j > size) {
					continue;
				}
				if (board2.getCell(i, j) != Pebble.EMPTY) {
					empty = false;
				}
			}

		}
		assertFalse(empty);
		assertEquals(Pebble.EMPTY, board2.getCell(x, y));
	}

}
