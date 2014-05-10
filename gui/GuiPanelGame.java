package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import engine.Engine;
import util.Util;

@SuppressWarnings({ "serial" })
public class GuiPanelGame extends JPanel implements KeyListener
{
	// Constante do GUI
	private static final int tamanhoComponente = 10;

	// Constantes da velocidade
	private static final int WAIT_1 = 200;
	private static final int WAIT_2 = 150;
	private static final int WAIT_3 = 100;
	private static final int WAIT_4 = 50;

	// Motor de jogo
	private Engine e;

	// Objectos do GUI
	private GuiFrame guiFrame;
	private GuiTimer guiTimer;

	/** Construtor */
	public GuiPanelGame(GuiFrame guiFrame)
	{
		super();

		this.guiFrame = guiFrame;
		this.guiTimer = new GuiTimer(this);

		// Iniciar painel
		int sizeX = Engine.NUM_COLUMNS * tamanhoComponente;
		int sizeY = Engine.NUM_LINES * tamanhoComponente;

		// Para o Y aparecer duas posi��es abaixo
		sizeY += 2*tamanhoComponente;

		// Definir tamanho
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(sizeX, sizeY));

		// Moldura
		setBorder(BorderFactory.createLineBorder(Color.ORANGE));

		// Adiciona um KeyListener
		setFocusable(true);
		addKeyListener(this);

		// Obst�culos
		int obstaculos = JOptionPane.showConfirmDialog(this, "Colocar obst�culos", "", JOptionPane.YES_NO_OPTION);

		if( obstaculos == JOptionPane.YES_OPTION )
			this.e = new Engine(1);
		else
			this.e = new Engine(0);

		// Nome do jogador
		String nomeJogador = new String("");

		while( nomeJogador.isEmpty() )
		{
			do
			{
				nomeJogador = JOptionPane.showInputDialog(this, "Insira o seu nome","");
			}
			while( nomeJogador == null );

			nomeJogador = nomeJogador.trim();
		}

		getEngine().setNomeJogador(nomeJogador);

		// Iniciar jogo
		guiTimer.init();
		guiTimer.start(WAIT_1);
	}

	/** Devolve o objecto da frame */
	public GuiFrame getGuiFrame()
	{
		return guiFrame;
	}

	/** Devolve o objecto do motor de jogo */
	public Engine getEngine()
	{
		return e;
	}

	/** Desenha a interface gr�fica */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		paintJogo(g);
		paintPontuacao(g);

		// Terminar o jogo ao haver game over
		if( getEngine().isGameOver() )
		{
			if( guiTimer == null)
				return;

			// Terminar timer
			guiTimer.getTimer().cancel();
			guiTimer = null;

			// Adicionar a pontua��o
			getEngine().getPontuacao().add();
		}
	}

	/** Captura ac��o de press�o de tecla (setas) */
	public void keyPressed(KeyEvent e)
	{
		if( guiTimer == null) return;

		guiTimer.setDireccaoCode( e.getKeyCode() ); // Mover cobra
	}

	/** Captura ac��o de press�o de tecla (letras/numeros) */
	public void keyTyped(KeyEvent e)
	{
		if( guiTimer == null) return;

		char c = e.getKeyChar();

		if( Util.isInteger(c+"") )
			mudarVelocidade( Util.parseInt(c+"") ); // Modificar velocidade
		else
			guiTimer.setDireccaoChar(c); // Mover cobra
	}

	public void keyReleased(KeyEvent e){ /* N�o implementado */ }

	/** Desenha o jogo na interface gr�fica */
	private void paintJogo(Graphics g)
	{
		// Desenhar
		for(int x=0; x<Engine.NUM_COLUMNS; x++)
		{
			for(int y=0; y<Engine.NUM_LINES; y++)
			{
				int drawY = Engine.NUM_LINES-1-y;

				if( getEngine().getCobra().isPosTrue(x,y) ) // Cobra
				{
					rectanguloJogo(g, Color.BLUE, x, drawY);
				}
				else if( getEngine().getObstaculos().isPosTrue(x,y) ) // Obstaculos
				{
					rectanguloJogo(g, Color.BLACK, x, drawY);
				}
				else if( getEngine().getTocaPermanente().isPosTrue(x,y) ) // Toca Permanente
				{
					rectanguloJogo(g, Color.RED, x, drawY);
				}
				else if( getEngine().getTocaTemporaria().isPosTrue(x,y) ) // Toca Tempor�ria
				{
					drawTocaTemporaria(g, x, drawY);
				}
			}
		}
	}

	/** Desenha a pontua��o na interface gr�fica */
	private void paintPontuacao(Graphics g)
	{
		linhaTabuleiro(g, Color.ORANGE, 20);

		g.setColor(Color.BLACK);

		if( getEngine().isGameOver() )
			g.drawString( "Game Over: "+Integer.toString( getEngine().getPontuacao().getValor() )+" Pontos", 15, 15);
		else
			g.drawString(Integer.toString( getEngine().getPontuacao().getValor() )+" Pontos", 15, 15);
	}

	/** Cria um rect�ngulo de um componente do jogo no tabuleiro */
	private void rectanguloJogo(Graphics g, Color c, int x, int y)
	{
		y += 2; // Para o Y aparecer duas posi��es abaixo
		x *= tamanhoComponente;
		y *= tamanhoComponente;

		g.setColor(c);
		g.fillRect(x, y, tamanhoComponente, tamanhoComponente);
	}

	/** Desenha uma toca tempor�ria */
	private void drawTocaTemporaria(Graphics g, int x, int y)
	{
		y += 2; // Para o Y aparecer duas posi��es abaixo
		x *= tamanhoComponente;
		y *= tamanhoComponente;

		// Desenha o rect�ngulo
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, tamanhoComponente, tamanhoComponente);

		// Desenha o n�mero de ratos na toca
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString( getEngine().getTocaTemporaria().getNumRatos() ), x, y);
	}

	/** Cria uma linha no tabuleiro */
	private void linhaTabuleiro(Graphics g, Color c, int y)
	{
		g.setColor(c);
		g.fillRect(0, y, (getEngine().getCobra().getTabuleiro().length*tamanhoComponente) , 1);
	}

	/** Muda a velocidade no jogo */
	private void mudarVelocidade(int n)
	{
		if( guiTimer == null) return;

		int time;

		switch(n)
		{
			case 1:
				time = WAIT_1;
				break;
			case 2:
				time = WAIT_2;
				break;
			case 3:
				time = WAIT_3;
				break;
			case 4:
				time = WAIT_4;
				break;
			default:
				return;
		}

		// Modifica o n�vel da pontua��o
		getEngine().getPontuacao().setNivel(n);

		// Cancela o timer
		guiTimer.getTimer().cancel();
		guiTimer = null;

		// Criar um timer novo com a velocidade nova
		guiTimer = new GuiTimer(this);
		guiTimer.start(time);
	}
}