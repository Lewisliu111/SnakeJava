package engine;

import java.util.ArrayList;

public class Arena
{
	private static final int NUM_LINES=35;
	private static final int NUM_COLUMNS=40;

	// Componentes da cobra
	private boolean[][] tabuleiroCobra = new boolean[NUM_COLUMNS][NUM_LINES];
	private ArrayList<Integer[]> posCobra = new ArrayList<Integer[]>();
	private char direccaoCobra;

	private boolean[][] tabuleiroObstaculos = new boolean[NUM_COLUMNS][NUM_LINES];
	private int[] posComida = new int[2];
	private int nivelDeDificuldade;

	// Componentes do jogo
	private boolean gameOver = false;
	private int pontuacao = 0;

	private Aleatorio aleatorio = new Aleatorio();

	/** Constructor */
	public Arena( int nivelDeDificuldade )
	{
		this.nivelDeDificuldade = nivelDeDificuldade;
		clear();
	}

	/** Retorna o tabuleiro da cobra */
	public boolean[][] getTabuleiroCobra()
	{
		return tabuleiroCobra;
	}

	/** Retorna o tabuleiro dos obstaculos */
	public boolean[][] getTabuleiroObstaculos()
	{
		return tabuleiroObstaculos;
	}

	/** Retorna a posição da comida */
	public int[] getPosComida()
	{
		return posComida;
	}

	/** Retorna se o jogo terminou */
	public boolean isGameOver()
	{
		return gameOver;
	}

	/** Retorna pontuacao */
	public int getPontuacao()
	{
		return pontuacao;
	}

	/** Limpa a arena e adiciona novos componentes*/
	public void clear()
	{
		for(int x=0; x<NUM_COLUMNS; x++)
		{
			for(int y=0; y<NUM_LINES; y++)
			{
				setTabuleiroCobra(x, y, false);
				setTabuleiroObstaculos(x,y, false);
			}
		}

		addCobra();
		addObstaculos();
		addComida();
	}

	/** Move a cobra para cima */
	public void moveCobraUp()
	{
		if( direccaoCobra == 'd' )
			return;

		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0];
		newPosCobraFrente[1] = posCobraFrente[1] + 1;

		if( newPosCobraFrente[1] >= tabuleiroCobra[0].length || hasCobra( newPosCobraFrente[0], newPosCobraFrente[1]) )
		{
			gameOver = true;
			return;
		}

		afterMoveHook(newPosCobraFrente);
		direccaoCobra = 'u';
	}

	/** Move a cobra para baixo */
	public void moveCobraDown()
	{
		if( direccaoCobra == 'u' )
			return;

		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0];
		newPosCobraFrente[1] = posCobraFrente[1] - 1;

		if( newPosCobraFrente[1] < 0 || hasCobra( newPosCobraFrente[0], newPosCobraFrente[1]) )
		{
			gameOver = true;
			return;
		}

		afterMoveHook(newPosCobraFrente);
		direccaoCobra = 'd';
	}

	/** Move a cobra para a esquerda */
	public void moveCobraLeft()
	{
		if( direccaoCobra == 'r' )
			return;

		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0] - 1;
		newPosCobraFrente[1] = posCobraFrente[1];

		if( newPosCobraFrente[0] < 0 || hasCobra( newPosCobraFrente[0], newPosCobraFrente[1]) )
		{
			gameOver = true;
			return;
		}

		afterMoveHook(newPosCobraFrente);
		direccaoCobra = 'l';
	}

	/** Move a cobra para a direita */
	public void moveCobraRight()
	{
		if( direccaoCobra == 'l' )
			return;

		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0] + 1;
		newPosCobraFrente[1] = posCobraFrente[1];

		if( newPosCobraFrente[0] >= tabuleiroCobra.length || hasCobra( newPosCobraFrente[0], newPosCobraFrente[1]) )
		{
			gameOver = true;
			return;
		}

		afterMoveHook(newPosCobraFrente);
		direccaoCobra = 'r';
	}

	/** Move a cobra para a frente */
	public void moveCobraForward()
	{
		switch(direccaoCobra)
		{
			case 'u':
				moveCobraUp();
				break;
			case 'd':
				moveCobraDown();
				break;
			case 'l':
				moveCobraLeft();
				break;
			case 'r':
				moveCobraRight();
				break;
			default:
				moveCobraUp();
				break;
		}
	}

	/** Adiciona a cobra */
	private void addCobra()
	{
		Integer[] posCobraFrente = new Integer[2];
		posCobraFrente[0] = NUM_COLUMNS/2;
		posCobraFrente[1] = NUM_LINES/2;

		posCobra.add( posCobraFrente );
		setTabuleiroCobra( posCobraFrente[0], posCobraFrente[1], true);
	}

	/** Adiciona os obstaculos */
	private void addObstaculos()
	{
		if( nivelDeDificuldade == 0)
			return;

		for(int i=0; i<5; i++)
		{
			int x = aleatorio.nextInt(NUM_COLUMNS);
			int y = aleatorio.nextInt(NUM_LINES);

			if( hasCobra( x, y ) )
			{
				i--;
				continue;
			}

			setTabuleiroObstaculos(x, y, true);
		}
	}

	/** Adiciona comida */
	private void addComida()
	{
		int x,y;

		do
		{
			x = aleatorio.nextInt(NUM_COLUMNS);
			y = aleatorio.nextInt(NUM_LINES);

			posComida[0] = x;
			posComida[1] = y;
		}
		while( hasCobra( x, y ) || tabuleiroObstaculos[x][y] );
	}

	/** Métodos a executar depois de processar o movimento */
	private void afterMoveHook( Integer[] newPosCobraFrente )
	{
		// Adiciona a nova posição da frente da cobra ao ArrayList
		posCobra.add( newPosCobraFrente );
		setTabuleiroCobra( newPosCobraFrente[0], newPosCobraFrente[1], true);

		// Verifica se atingiu obstaculo
		hasHitObstaculo();

		// Verifica se a cobra comeu a comida, se não avança a posição de trás.
		if( !cobraComeComida() )
			moveCobraPosTrasForward();
	}

	/** Cobra come comida **/
	private boolean cobraComeComida()
	{
		if( !hasCobra( posComida[0], posComida[1] ) )
			return false;

		pontuacao++;
		addComida();
		return true;
	}

	/** Move a posição de trás da cobra para a frente */
	private void moveCobraPosTrasForward()
	{
		Integer[] posCobraTras = posCobra.get(0);
		tabuleiroCobra[ posCobraTras[0] ][ posCobraTras[1] ] = false;
		posCobra.remove(posCobraTras);
	}

	/** Verifica se na posição x,y existe cobra */
	private boolean hasCobra( int x, int y)
	{
		if( x < 0 || x >= tabuleiroCobra.length || y < 0 || y >= tabuleiroCobra[0].length )
			return false;

		return tabuleiroCobra[x][y];
	}

	/** Verifica se atingui um obstaculo */
	private boolean hasHitObstaculo()
	{
		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );

		if( !tabuleiroObstaculos[ posCobraFrente[0] ][ posCobraFrente[1] ] )
			return false;

		gameOver = true;
		return true;
	}

	/** Modifica a posição x,y do tabuleiroCobra para o estado */
	private void setTabuleiroCobra( int x, int y, boolean estado )
	{
		tabuleiroCobra[x][y] = estado;
	}

	/** Modifica a posição x,y do tabuleiroObstaculos para o estado */
	private void setTabuleiroObstaculos( int x, int y, boolean estado )
	{
		tabuleiroObstaculos[x][y] = estado;
	}
}