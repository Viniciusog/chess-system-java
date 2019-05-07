package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	// Tiramos o setRow e o setColumn para que n�o seja poss�vel mudar as linhas e
	// colunas do tabuleiro

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException(
					"Erro na cria��o do tabuleiro: � necess�rio que haja pelo menos 1 linha e 1 coluna!");
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
			throw new BoardException("Esta posi��o n�o est� no tabuleiro");
		}
		return pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posi��o n�o est� no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("J� existe uma pe�a na posi��o " + position);
		}
		// Pega a matriz na posi��o dada e atribui a ela a pe�a que eu informei
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Esta posi��o n�o est� no tabuleiro");
		}
		if(piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		//Na minha matriz de pe�as, nessa posi��o aonde eu estou removendo a pe�a, recebe null;
		//Indicando que agora n�o tem pe�a nessa posi��o da matriz
		//Aux cont�m a pe�a que foi retirada
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
		
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posi��o n�o est� no tabuleiro");
		}
		return piece(position) != null;

	}
	
	
}
