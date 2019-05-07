package boardgame;

public class BoardException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	//Repassa a mensagem para a Classe RuntimeException
	public BoardException(String msg) {
		super(msg);		
	}
}