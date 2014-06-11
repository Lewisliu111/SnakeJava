package engine;

import util.Aleatorio;

public class Obstaculos extends Tabuleiro
{
	// Gerador de números aleatórios
	private Aleatorio aleatorio = new Aleatorio();

	/** Construtor */
	public Obstaculos(Engine e)
	{
		super(e);
	}

	/** Adiciona os obstaculos */
	public void add()
	{
		if( getEngine().getNivelDeDificuldade() == 0 )
			return;

		for(int x=0; x<Engine.NUM_COLUMNS; x++)
		{
			for(int y=0; y<Engine.NUM_LINES; y++)
				setTabuleiro(x, y, false);
		}

		for(int i=0; i<5; i++)
		{
			int x = aleatorio.nextInt(Engine.NUM_COLUMNS);
			int y = aleatorio.nextInt(Engine.NUM_LINES);

			if( getEngine().getCobra().isPosTrue(x, y) )
			{
				i--;
				continue;
			}

			setTabuleiro(x, y, true);
		}
	}

	/** Verifica se atingiu um obstaculo */
	public boolean check()
	{
		if( getEngine().getNivelDeDificuldade() == 0 )
			return false;

		Integer[] posCobraFrente = getEngine().getCobra().getPosCobra().get( getEngine().getCobra().getPosCobra().size()-1 );

		if( !getTabuleiro()[ posCobraFrente[0] ][ posCobraFrente[1] ] )
			return false;

		return true;
	}

	/** Método de acção */
	public void doAction()
	{
		if( !check() )
			return;

		// Terminar o jogo
		getEngine().setGameOver(true);
	}
}