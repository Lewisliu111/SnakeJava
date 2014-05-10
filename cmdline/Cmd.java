package cmdline;

import engine.Arena;
import engine.Pontuacao;
import engine.Util;

@SuppressWarnings("unused")
public class Cmd
{
	// Constantes das pontuações
	private static final String TOP_SCORES_FILE="TopScores.txt";
	private static final int MAX_TOP_SCORES=10;

	// Constantes para o teclado
	private static final char KEY_UP = 'w';
	private static final char KEY_DOWN = 's';
	private static final char KEY_LEFT = 'a';
	private static final char KEY_RIGHT = 'd';

	// Objectos
	private Arena arena;
	private Pontuacao pontuacao = new Pontuacao(MAX_TOP_SCORES, TOP_SCORES_FILE);
	private Teclado teclado = new Teclado();

	/** Constructor */
	public Cmd()
	{
		int nivelDeDificuldade = teclado.nextInt("Nivel de dificuldade [0 ou 1] >");
		arena = new Arena( nivelDeDificuldade );

		Util.println("Cima: "+KEY_UP+" | Baixo: "+KEY_DOWN+" | Esquerda: "+KEY_LEFT+" | Direita: "+KEY_RIGHT);

		while( !arena.isGameOver() )
		{
			drawArena();
			switch( teclado.nextChar(">") )
			{
				case KEY_UP:
					arena.moveCobraUp();
					break;
				case KEY_DOWN:
					arena.moveCobraDown();
					break;
				case KEY_LEFT:
					arena.moveCobraLeft();
					break;
				case KEY_RIGHT:
					arena.moveCobraRight();
					break;
				default:
					arena.moveCobraForward();
					break;
			}
		}
		Util.println("Game Over - Pontuação: "+arena.getPontuacao());
	}

	/** Desenha tabuleiro */
	private void drawArena()
	{
		boolean[][] tabuleiroCobra = arena.getTabuleiroCobra();
		boolean[][] tabuleiroObstaculos = arena.getTabuleiroObstaculos();
		int[] posComida = arena.getPosComida();

		drawArenaLine(tabuleiroCobra);

		for(int y=tabuleiroCobra[0].length-1; y>=0; y--)
		{
			Util.print('#');

			for(int x=0; x<tabuleiroCobra.length; x++)
			{
				if( tabuleiroCobra[x][y] == true )
					Util.print('-');

				else if( x == posComida[0] && y == posComida[1] )
					Util.print('+');

				else if( tabuleiroObstaculos[x][y] == true )
					Util.print('/');

				else
					Util.print(' ');
			}

			Util.print('#');
			Util.println();
		}

		drawArenaLine(tabuleiroCobra);
	}

	/** Desenha a linha superior e inferior do tabuleiro */
	private void drawArenaLine(boolean[][] tabuleiroCobra)
	{
		for(int x=0; x<tabuleiroCobra.length; x++)
		{
			if( x==0 )
				Util.print('#');

			Util.print('#');

			if( x==tabuleiroCobra.length-1 )
				Util.print('#');
		}
		Util.println();
	}
}