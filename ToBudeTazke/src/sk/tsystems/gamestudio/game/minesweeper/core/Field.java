package sk.tsystems.gamestudio.game.minesweeper.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;


/**
 * Field represents playing field and game logic.
 */
public class Field {
	
	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;
	
	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		generate();
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	
	public int getRowCount() {
		return rowCount;
	}
	
	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public Tile[][] getTiles() {
		return tiles;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	/*
	 * Returns the value of amount of tiles of type Mine that are neighbors to a specified tile
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random number = new Random();
		int i = 0;
		int row, column;
		while (i < mineCount) {
			row = number.nextInt(rowCount);
			column = number.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				i++;
			}
		}
		for (int y = 0; y < getColumnCount(); y++) {
			for (int x = 0; x < getRowCount(); x++) {
				if (tiles[x][y] == null) {
					tiles[x][y] = new Clue(countAdjacentMines(x, y));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int y = 0; y < getColumnCount(); y++) {
			for (int x = 0; x < getRowCount(); x++) {
				if (tiles[x][y].getState().equals(state)) {
					count++;
				}
			}
		}
		return count;
	}
	
	/*
	 * Returns true if 
	 */
	public boolean isSolved() {
		if ((getRowCount() * getColumnCount() - getNumberOf(Tile.State.OPEN) == getMineCount()) && minesAreClosed()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean minesAreClosed() {
		boolean miny = false;
		for (int y = 0; y < getColumnCount(); y++) {
			for (int x = 0; x < getRowCount(); x++) {
				if (tiles[x][y].getState().equals(Tile.State.CLOSED) && tiles[x][y] instanceof Mine) {
					miny = true;
				}
			}
		}
		return miny;
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState().equals(State.CLOSED)) {
			tile.setState(State.MARKED);
		} else if (tile.getState().equals(State.MARKED)) {
			tile.setState(State.CLOSED);
		}
	}
	
	
	/*
	 * Opens all neighboring tiles of type Clue after opening tile 
	 * of type Clue with value of 0
	 */
	void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						Tile orig = tiles[row][column];
						Tile tile = tiles[actRow][actColumn];
						if (orig instanceof Clue && ((Clue) orig).getValue() == 0 && tile.getState().equals(State.CLOSED)) { 
							tile.setState(State.OPEN);
							openAdjacentTiles(actRow, actColumn);
						}
					}
				}
			}
		}
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState().equals(State.CLOSED)) {
			tile.setState(State.OPEN);
			if (tile instanceof Mine) {
				setState(GameState.FAILED);
				return;
			}
			if (tile instanceof Clue && ((Clue) tile).getValue() == 0) {
				openAdjacentTiles(row, column);
			}

			if (isSolved()) {
				setState(GameState.SOLVED);
				return;
			}
		}
	}
	
	public int getRemainingMineCount() {
		return getMineCount() - getNumberOf(State.MARKED);
	}
}
