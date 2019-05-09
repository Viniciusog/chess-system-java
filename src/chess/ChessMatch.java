package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	// Uma propriedade boolean come�a com false
	private int turn;
	private Color currentPlayer;
	private Board board; // Uma partida de xadrez necessita de um tabuleiro
	private boolean check;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8); // � a classe ChessMatch que tem que saber a dimens�o do tabuleiro
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);

			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();

	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {                //Se o jogador se colocou em check
			undoMovie(source, target, capturedPiece); //Desfazer movimento
			throw new ChessException("Voc� n�o pode se colocar em check!");
		}
		
		//Se o oponente ficou em check, retorne true, sen�o retorne false
		check = testCheck(opponent(currentPlayer)) ? true : false;
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		// Tira a pe�a do tabuleiro e coloca na lista de pe�as capturadas
		if (capturedPiece != null) { // capturou um pe�a
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;

	}

	public void undoMovie(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);

		// Se a pe�a capturada for diferente de null
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target); // Recoloca a pe�a na posi��o de destino
			capturedPieces.remove(capturedPiece); // Em seguida tira a pe�a da lista de pe�as capturadas
			piecesOnTheBoard.add(capturedPiece); // E coloca na lista de pe�as que est�o no tabuleiro
		}
	}

	public void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("N�o existe pe�a na posi��o de origem");
		}
		// Pega a pe�a nesta posi��o e pega a cor dela.
		// Se esta cor for diferente da cor do jogador atual, � uma pe�a advers�ria
		if (currentPlayer != (((ChessPiece) board.piece(position)).getColor())) {
			throw new ChessException("Esta pe�a n�o � sua! ");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("N�o existe movimentos dispon�veis para a pe�a escolhida");
		}
	}

	public void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A pe�a escolhida n�o pode mover para a posi��o de destino");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

	}

	// Se a cor passada for igual a Color.WHITE, retorne Color.BLACK, sen�o retorne
	// Color.WHITE
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("N�o existe o rei " + color + " no tabuleiro");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		
		//Enquanto tiver pe�as na matriz de pe�as oponente, passa as posi��es de movimentos possiveis dessa pe�a para a matriz boleana mat
		//Em seguida verifica se na posi��o em que o rei se encontra tem um movimento possivel para essas pe�as advers�rias
		//Se tiver retorna true, se n�o retorna false
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	// Coloca as pe�as nos lugares certos
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));

	}
}
