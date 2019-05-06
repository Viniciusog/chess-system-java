package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	// Tiramos o setRow e o setColumn para que não seja possível mudar as linhas e
	// colunas do tabuleiro

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException(
					"Erro na criação do tabuleiro: É necessário que haja pelo menos 1 linha e 1 coluna!");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Esta posição não está no tabuleiro");
		}
		return pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posição não está no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça na posição " + position);
		}
		// Pega a matriz na posição dada e atribui a ela a peça que eu informei
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posição não está no tabuleiro");
		}
		return piece(position) != null;

	}
}
