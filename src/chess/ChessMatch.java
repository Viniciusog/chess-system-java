package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board; // Uma partida de xadrez necessita de um tabuleiro

	public ChessMatch() {
		board = new Board(8, 8); // � a classe ChessMatch que tem que saber a dimens�o o tabuleiro
		initialSetup();
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	// Coloca as pe�as nos lugares certos
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.BLACK));
	

	}
}
