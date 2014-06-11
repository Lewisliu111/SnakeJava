package engine;

import util.Aleatorio;

public class Toca
{
	// Objecto do motor de jogo
	private Engine e;

	// Gerador de números aleatórios
	private Aleatorio aleatorio = new Aleatorio();

	// Posição da toca
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

	/** Devolve o objecto do gerador de números aleatórios */
	public Aleatorio getAleatorio()
	{
		return aleatorio;
	}

	/** Devolve a posição da toca */
	public Integer[] getPosToca()
	{
		return posToca;
	}

	/** Modifica a posição da toca */
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

	/** Verifica se não existe toca */
	public boolean isTocaNull()
	{
		return (posToca == null || posToca[0] == null || posToca[1] == null );
	}

	/** Verifica se a posição x,y existe e é verdadeira */
	public boolean isPosTrue( int x, int y)
	{
		return isTocaNull() ? false : ( x == posToca[0] && y == posToca[1] );
	}

	/** Verifica se uma posição x,y está vazia */
	public boolean isPosFree( int x, int y)
	{
		// Comparar
		return !(	e.getTocaPermanente().isPosTrue(x, y) ||
					e.getTocaTemporaria().isPosTrue(x, y) ||
					e.getCobra().isPosTrue(x, y) ||
					e.getObstaculos().isPosTrue(x, y)	);
	}
}