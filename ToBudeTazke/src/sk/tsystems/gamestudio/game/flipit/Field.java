package sk.tsystems.gamestudio.game.flipit;

public class Field {
	private final boolean[][] tiles;

	private final int rowCount;

	private final int columnCount;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new boolean[rowCount][columnCount];
		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public boolean getValueAt(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = false;
			}
		}
	}

	public void flip(int row, int column) {
		tiles[row][column] = !tiles[row][column];
		if(row - 1 >= 0 && row + 1 < rowCount && column - 1 >= 0 && column + 1 < columnCount) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column-1] = !tiles[row][column-1];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(row - 1 < 0 && column - 1 < 0){
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(row + 1 == rowCount && column + 1 == columnCount) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row][column-1] = !tiles[row][column-1];
		} else if(row - 1 < 0 && column + 1 == columnCount) {
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column-1] = !tiles[row][column-1];
		} else if(row + 1 == rowCount && column - 1 < 0) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(row - 1 < 0) {
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column-1] = !tiles[row][column-1];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(row + 1 == rowCount) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row][column-1] = !tiles[row][column-1];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(column - 1 < 0) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column+1] = !tiles[row][column+1];
		} else if(column + 1 == columnCount) {
			tiles[row-1][column] = !tiles[row-1][column];
			tiles[row+1][column] = !tiles[row+1][column];
			tiles[row][column-1] = !tiles[row][column-1];
		}
	}

	public boolean isSolved() {
		boolean solved = true;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == false) {
					solved = false;
				}
			}
		}
		return solved;
	}
}
