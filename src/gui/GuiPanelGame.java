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
	// Constantes da velocidade
	public static final int WAIT_1 = 200;
	public static final int WAIT_2 = 150;
	public static final int WAIT_3 = 100;
	public static final int WAIT_4 = 50;

	// Constante do GUI
	private static final int tamanhoComponente = 10;

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

		// Para o Y aparecer duas posições abaixo
		sizeY += 2*tamanhoComponente;

		// Definir tamanho
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(sizeX, sizeY));

		// Moldura
		setBorder(BorderFactory.createLineBorder(Color.ORANGE));

		// Adiciona um KeyListener
		setFocusable(true);
		addKeyListener(this);

		// Obstáculos
		int obstaculos = JOptionPane.showConfirmDialog(this, "Colocar obstáculos", "", JOptionPane.YES_NO_OPTION);

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

	/** Desenha a interface gráfica */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		paintJogo(g);
		paintPontuacao(g);

		// Terminar o jogo ao haver game over
		if( getEngine().isGameOver() )
		{
			// Terminar timer
			guiTimer.getTimer().stop();

			// Adicionar a pontuação
			getEngine().getPontuacao().add();
		}
	}

	/** Captura acção de pressão de tecla (setas) */
	public void keyPressed(KeyEvent e)
	{
		guiTimer.setDireccaoCode( e.getKeyCode() ); // Mover cobra
	}

	/** Captura acção de pressão de tecla (letras/numeros) */
	public void keyTyped(KeyEvent e)
	{
		char c = e.getKeyChar();

		if( Util.isInteger(c+"") )
			mudarVelocidade( Util.parseInt(c+"") ); // Modificar velocidade
		else
			guiTimer.setDireccaoChar(c); // Mover cobra
	}

	public void keyReleased(KeyEvent e){ /* Não implementado */ }

	/** Desenha o jogo na interface gráfica */
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
				else if( getEngine().getTocaTemporaria().isPosTrue(x,y) ) // Toca Temporária
				{
					drawTocaTemporaria(g, x, drawY);
				}
			}
		}
	}

	/** Desenha a pontuação na interface gráfica */
	private void paintPontuacao(Graphics g)
	{
		linhaTabuleiro(g, Color.ORANGE, 20);

		g.setColor(Color.BLACK);

		if( getEngine().isGameOver() )
			g.drawString( "Game Over: "+Integer.toString( getEngine().getPontuacao().getValor() )+" Pontos", 15, 15);
		else
			g.drawString(Integer.toString( getEngine().getPontuacao().getValor() )+" Pontos", 15, 15);
	}

	/** Cria um rectângulo de um componente do jogo no tabuleiro */
	private void rectanguloJogo(Graphics g, Color c, int x, int y)
	{
		y += 2; // Para o Y aparecer duas posições abaixo
		x *= tamanhoComponente;
		y *= tamanhoComponente;

		g.setColor(c);
		g.fillRect(x, y, tamanhoComponente, tamanhoComponente);
	}

	/** Desenha uma toca temporária */
	private void drawTocaTemporaria(Graphics g, int x, int y)
	{
		y += 2; // Para o Y aparecer duas posições abaixo
		x *= tamanhoComponente;
		y *= tamanhoComponente;

		// Desenha o rectângulo
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, tamanhoComponente, tamanhoComponente);

		// Desenha o número de ratos na toca
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
		int wait;

		switch(n)
		{
			case 1:
				wait = WAIT_1;
				break;
			case 2:
				wait = WAIT_2;
				break;
			case 3:
				wait = WAIT_3;
				break;
			case 4:
				wait = WAIT_4;
				break;
			default:
				return;
		}

		// Reinciar o jogo no caso de game over
		if( getEngine().isGameOver() )
		{
			// Desactivar game over
			getEngine().setGameOver(false);

			// Reiniciar motor de jogo
			getEngine().init();

			// Limpar certos componentes do jogo
			getEngine().getCobra().setDireccao('\0');
			getEngine().getPontuacao().setValor(0);
			getEngine().getTocaTemporaria().remove();

			// Iniciar
			guiTimer.start(wait);
		}

		// Modifica o nível da pontuação
		getEngine().getPontuacao().setNivel(n);

		// Modifica a velocidade
		guiTimer.getTimer().setDelay(wait);
	}
}