package gui;

import engine.Arena;
import engine.Pontuacao;

@SuppressWarnings("unused")
class GuiThread extends Thread
{
	// Constantes para o teclado
	private static final int KEY_UP = 38;
	private static final int KEY_DOWN = 40;
	private static final int KEY_LEFT = 37;
	private static final int KEY_RIGHT = 39;

	// Objectos do jogo
	private Arena arena;
	private Pontuacao pontuacao;

	// Objectos do teclado
	private int keyCode;

	// Objecto do GUI
	private GuiPanel guiPanel;

	/** Constructor */
	public GuiThread(Arena arena, Pontuacao pontuacao, GuiPanel guiPanel)
	{
		super();
		this.arena = arena;
		this.pontuacao = pontuacao;
		this.guiPanel = guiPanel;
	}

	/** Executa */
	public void run()
	{
		while( !isGameOver() )
		{
			moveCobra();
			desenharCobra();

			try
			{
				Thread.sleep(150);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}

		desenharCobra();
	}

	/** Retorna se o jogo terminou */
	private boolean isGameOver()
	{
		return arena.isGameOver();
	}

	/** Desenha a cobra na interface gráfica */
	private void desenharCobra()
	{
		guiPanel.repaint();
	}

	/** Define a tecla que o jogador premiu */
	public void setKeyCode(int newKeyCode)
	{
		this.keyCode = newKeyCode;
	}

	/** Move a cobra */
	public void moveCobra()
	{
		switch( keyCode )
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
}