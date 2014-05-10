package gui;

import java.awt.*;
import javax.swing.*;

import engine.Arena;
import engine.Pontuacao;

@SuppressWarnings({ "serial" })
public class GuiPanel extends JPanel
{
	// Constantes das pontuações
	private static final String TOP_SCORES_FILE="TopScores.txt";
	private static final int MAX_TOP_SCORES=10;

	// Constantes do GUI
	private final int tamanhoComponente = 10;

	// Arena
	private Arena arena = new Arena(0); // TODO Variar nível de dificuldade

	// Pontuação
	private Pontuacao pontuacao = new Pontuacao(MAX_TOP_SCORES, TOP_SCORES_FILE);

	// Objecto do GUI
	private GuiThread guiThread = new GuiThread(arena, pontuacao, this);

	/** Constructor */
	public GuiPanel()
	{
		// Iniciar painel
		int sizeX = arena.getTabuleiroCobra().length * tamanhoComponente;
		int sizeY = arena.getTabuleiroCobra()[0].length * tamanhoComponente;

		// Definir tamanho
		setPreferredSize(new Dimension(sizeX, sizeY));

		// Iniciar jogo
		guiThread.start();
	}

	/** Desenha a cobra na interface gráfica */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		boolean[][] tabuleiroCobra = arena.getTabuleiroCobra();
		boolean[][] tabuleiroObstaculos = arena.getTabuleiroObstaculos();
		int[] posComida = arena.getPosComida();

		Graphics2D g2D=(Graphics2D)g;

		for(int x=0; x<tabuleiroCobra.length; x++)
		{
			for(int y=0; y<tabuleiroCobra[0].length; y++)
			{
				int drawX = x;
				int drawY = tabuleiroCobra[0].length-y;

				if( tabuleiroCobra[x][y] == true ) // Cobra
				{
					g2D.setColor(Color.BLUE);
					fillRect(g2D, drawX, drawY);
				}
				else if( x == posComida[0] && y == posComida[1] ) // Comida
				{
					g2D.setColor(Color.RED);
					fillRect(g2D, drawX, drawY);
				}
				else if( tabuleiroObstaculos[x][y] == true ) // Obstaculos
				{
					g2D.setColor(Color.GREEN);
					fillRect(g2D, drawX, drawY);
				}
			}
		}
	}

	/** Cria um rectângulo no tabuleiro */
	public void fillRect(Graphics2D g2D, int x, int y)
	{
		x = x * tamanhoComponente;
		y = y * tamanhoComponente;

		g2D.fillRect(x, y, tamanhoComponente, tamanhoComponente);
	}

	/** Move a cobra */
	public void move(int keyCode)
	{
		guiThread.setKeyCode(keyCode);
	}
}