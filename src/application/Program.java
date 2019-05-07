package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();

		while (true) {
			try {
				UI.cleanScreen();
				UI.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("Origem ");
				ChessPosition source = UI.readChessPosition(sc);

				System.out.println();
				System.out.print("Destino ");

				ChessPosition target = UI.readChessPosition(sc);
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Espera o usu�rio digitar enter
				
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Espera o usu�rio digitar enter
			}
		}
	}
}