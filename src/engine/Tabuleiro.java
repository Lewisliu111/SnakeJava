package engine;

public class Tabuleiro
{
	// Objecto do motor de jogo
	private Engine e;

	// Tabuleiro de jogo
	private boolean[][] tabuleiro = new boolean[Engine.NUM_COLUMNS][Engine.NUM_LINES];

	/** Constructor */
	public Tabuleiro(Engine e)
	{
		this.e = e;
	}

	/** Devolve o objecto do motor de jogo */
	public Engine getEngine()
	{
		return e;
	}

	/** Devolve o tabuleiro */
	public boolean[][] getTabuleiro()
	{
		return tabuleiro;
	}

	/** Modifica a posição x,y do tabuleiro para o estado */
	public void setTabuleiro( int x, int y, boolean estado )
	{
		tabuleiro[x][y] = estado;
	}

	/** Verifica se a posição x,y existe e é verdadeira */
	public boolean isPosTrue( int x, int y)
	{
		if( x < 0 || x >= getTabuleiro().length || y < 0 || y >= getTabuleiro()[0].length )
			return false;

		return getTabuleiro()[x][y];
	}
}