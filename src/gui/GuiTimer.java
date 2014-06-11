package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GuiTimer implements ActionListener
{
	// Constantes para o teclado
	private static final int KEY_UP = 38;
	private static final int KEY_DOWN = 40;
	private static final int KEY_LEFT = 37;
	private static final int KEY_RIGHT = 39;

	// Direcção
	private int direccaoCode;

	// Objectos
	private GuiPanelGame guiPanelGame;
	private Timer timer;

	/** Construtor */
	public GuiTimer(GuiPanelGame guiPanelGame)
	{
		super();

		this.guiPanelGame = guiPanelGame;
		this.timer = new Timer(GuiPanelGame.WAIT_1, this);
	}

	/** Inicia */
	public void init()
	{
		guiPanelGame.getEngine().init();
	}

	/** Começa */
	public void start(int wait)
	{
		getTimer().setDelay(wait);
		getTimer().start();
	}

	/** Executa */
	public void actionPerformed(ActionEvent e)
	{
		if( isGameOver() )
			return;

		moveCobra();
		guiPanelGame.getEngine().run();
		desenharCobra();
	}

	/** Devolve o timer */
	public Timer getTimer()
	{
		return timer;
	}

	/** Define a tecla que o jogador premiu (setas) */
	public void setDireccaoCode(int newDireccaoCode)
	{
		this.direccaoCode = newDireccaoCode;
	}

	/** Define a tecla que o jogador premiu (letras) */
	public void setDireccaoChar(char newDireccaoChar)
	{
		switch( newDireccaoChar )
		{
			case 'w':
				this.direccaoCode = KEY_UP;
				break;
			case 'a':
				this.direccaoCode = KEY_LEFT;
				break;
			case 's':
				this.direccaoCode = KEY_DOWN;
				break;
			case 'd':
				this.direccaoCode = KEY_RIGHT;
				break;
		}
	}

	/** Move a cobra */
	public void moveCobra()
	{
		char direccao;

		switch( direccaoCode )
		{
			case KEY_UP:
				direccao = 'u';
				break;
			case KEY_DOWN:
				direccao = 'd';
				break;
			case KEY_LEFT:
				direccao = 'l';
				break;
			case KEY_RIGHT:
				direccao = 'r';
				break;
			default:
				return;
		}

		guiPanelGame.getEngine().getCobra().setDireccao(direccao);
	}

	/** Devolve se o jogo terminou */
	private boolean isGameOver()
	{
		return guiPanelGame.getEngine().isGameOver();
	}

	/** Desenha a cobra na interface gráfica */
	private void desenharCobra()
	{
		guiPanelGame.repaint();
	}
}