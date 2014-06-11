package engine;

import util.Aleatorio;

public class Toca
{
	// Objecto do motor de jogo
	private Engine e;

	// Gerador de n�meros aleat�rios
	private Aleatorio aleatorio = new Aleatorio();

	// Posi��o da toca
	private Integer[] posToca = new Integer[2];

	/** Constructor */
	public Toca(Engine e)
	{
		this.e = e;
	}

	/** Devolve o objecto do motor de jogo */
	public Engine getEngine()
	{
		return e;
	}

	/** Devolve o objecto do gerador de n�meros aleat�rios */
	public Aleatorio getAleatorio()
	{
		return aleatorio;
	}

	/** Devolve a posi��o da toca */
	public Integer[] getPosToca()
	{
		return posToca;
	}

	/** Modifica a posi��o da toca */
	public void setPosToca(Integer[] posToca)
	{
		this.posToca = posToca;
	}

	/** Verifica se a cobra comeu a toca na jogada */
	public boolean check()
	{
		Integer[] posToca = getPosToca();

		return isTocaNull() ? false : getEngine().getCobra().isPosTrue(posToca[0], posToca[1]);
	}

	/** Verifica se n�o existe toca */
	public boolean isTocaNull()
	{
		return (posToca == null || posToca[0] == null || posToca[1] == null );
	}

	/** Verifica se a posi��o x,y existe e � verdadeira */
	public boolean isPosTrue( int x, int y)
	{
		return isTocaNull() ? false : ( x == posToca[0] && y == posToca[1] );
	}

	/** Verifica se uma posi��o x,y est� vazia */
	public boolean isPosFree( int x, int y)
	{
		// Comparar
		return !(	e.getTocaPermanente().isPosTrue(x, y) ||
					e.getTocaTemporaria().isPosTrue(x, y) ||
					e.getCobra().isPosTrue(x, y) ||
					e.getObstaculos().isPosTrue(x, y)	);
	}
}