package cmdline;

import util.Util;
import engine.Engine;

public class Cmd
{
	// Constantes para o teclado
	private static final char KEY_UP = 'w';
	private static final char KEY_DOWN = 's';
	private static final char KEY_LEFT = 'a';
	private static final char KEY_RIGHT = 'd';

	// Objectos
	private Engine e;
	private Teclado teclado = new Teclado();

	/** Construtor */
	public Cmd()
	{
		int nivelDeDificuldade = teclado.nextInt("Nivel de dificuldade [0 ou 1] >");
		e = new Engine( nivelDeDificuldade );
		e.init();

		Util.println("Cima: "+KEY_UP+" | Baixo: "+KEY_DOWN+" | Esquerda: "+KEY_LEFT+" | Direita: "+KEY_RIGHT);

		while( !e.isGameOver() )
		{
			drawArena();
			switch( teclado.nextChar(">") )
			{
				case KEY_UP:
					e.getCobra().setDireccao('u');
					break;
				case KEY_DOWN:
					e.getCobra().setDireccao('d');
					break;
				case KEY_LEFT:
					e.getCobra().setDireccao('l');
					break;
				case KEY_RIGHT:
					e.getCobra().setDireccao('r');
					break;
				default:
					break;
			}

			e.run();
		}

		drawArena();
		Util.println("Game Over - Pontuação: "+e.getPontuacao().getValor());
	}

	/** Desenha tabuleiro */
	private void drawArena()
	{
		drawArenaLine(Engine.NUM_COLUMNS);

		for(int y=Engine.NUM_LINES; y>=0; y--)
		{
			Util.print('#');

			for(int x=0; x<Engine.NUM_COLUMNS; x++)
			{
				if( e.getCobra().isPosTrue(x,y) ) // Cobra
					Util.print('-');

				else if( e.getTocaPermanente().isPosTrue(x,y) ) // Toca Permanente
					Util.print('+');

				else if( e.getTocaTemporaria().isPosTrue(x,y) ) // Toca Temporária
					Util.print( e.getTocaTemporaria().getNumRatos() );

				else if( e.getObstaculos().isPosTrue(x,y) ) // Obstaculos
					Util.print('/');

				else
					Util.print(' ');
			}

			Util.print('#');
			Util.println();
		}

		drawArenaLine(Engine.NUM_COLUMNS);
	}

	/** Desenha a linha superior e inferior do tabuleiro */
	private void drawArenaLine(int length)
	{
		for(int x=0; x<length; x++)
		{
			if( x==0 )
				Util.print('#');

			Util.print('#');

			if( x==length-1 )
				Util.print('#');
		}
		Util.println();
	}
}