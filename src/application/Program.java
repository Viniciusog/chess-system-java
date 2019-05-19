package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		//Enquanto o resultado da partida for diferente de cheque mate, continue o jogo
		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Origem ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Destino ");

				ChessPosition target = UI.readChessPosition(sc);
				
				//Por padr�o vai colocar uma rainha no lugar
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null) {
					System.out.println("Entre com uma pe�a para promo��o (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type); //A� trocamos a rainha (antigo pe�o) pela pe�a escolhida
				}
			
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Espera o usu�rio digitar enter
				
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Espera o usu�rio digitar enter
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}
}