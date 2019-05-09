package chess;

import boardgame.Position;

public class ChessPosition {
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Erro instanciando ChessPosition. Validos valores de a1 at� h8");
		}
			
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public  int getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a' );
	}
	
	//Pega uma posi�a� de matriz e passa para uma posi��o de xadrez
	protected static ChessPosition fromPosition(Position position) {
		//no xadrez informa primeiro a coluna e depois a linha
		//� + porque quer o codigo da letra a + a coluna da matriz
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}
	
	@Override 
	public String toString() {
		return "" + column + row;
	}
	
	

}
  