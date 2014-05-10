package engine;

import java.util.ArrayList;

public class Cobra extends Tabuleiro
{
	// Componentes da cobra
	private ArrayList<Integer[]> posCobra = new ArrayList<Integer[]>();
	private char direccao;

	// Número de vezes a não mover a posição de trás da cobra
	private int timesNotToMovePosTras = 0;

	/** Construtor */
	public Cobra(Engine e)
	{
		super(e);
	}

	/** Devolve o ArrayList contendo as posições da cobra ordenadas */
	public ArrayList<Integer[]> getPosCobra()
	{
		return posCobra;
	}

	/** Devolve a direcção da cobra */
	public char getDireccao()
	{
		return direccao;
	}

	/** Define a direcção da cobra */
	public void setDireccao(char novaDireccao)
	{
		if( (this.direccao == 'u' && novaDireccao == 'd') ||
			(this.direccao == 'd' && novaDireccao == 'u') ||
			(this.direccao == 'l' && novaDireccao == 'r') ||
			(this.direccao == 'r' && novaDireccao == 'l') )
		{
			return;
		}

		this.direccao = novaDireccao;
	}

	/** Devolve o número de vezes a não mover a posição de trás da cobra */
	public int getTimesNotToMovePosTras()
	{
		return timesNotToMovePosTras;
	}

	/** Define o número de vezes a não mover a posição de trás da cobra */
	public void setTimesNotToMovePosTras(int timesNotToMovePosTras)
	{
		this.timesNotToMovePosTras = timesNotToMovePosTras;
	}

	/** Mover cobra */
	public void doAction()
	{
		moveForward();
		movePosTrasForward();
	}

	/** Move a cobra para a frente */
	public void moveForward()
	{
		switch( getDireccao() )
		{
			case 'u':
				moveUp();
				break;
			case 'd':
				moveDown();
				break;
			case 'l':
				moveLeft();
				break;
			case 'r':
				moveRight();
				break;
			default:
				break;
		}
	}

	/** Adiciona a cobra */
	public void add()
	{
		for(int x=0; x<Engine.NUM_COLUMNS; x++)
		{
			for(int y=0; y<Engine.NUM_LINES; y++)
				setTabuleiro(x, y, false);
		}

		Integer[] posCobraFrente = new Integer[2];
		posCobraFrente[0] = Engine.NUM_COLUMNS/2;
		posCobraFrente[1] = Engine.NUM_LINES/2;

		posCobra.add( posCobraFrente );
		setTabuleiro( posCobraFrente[0], posCobraFrente[1], true);
	}

	/** Métodos a executar depois de processar o movimento */
	private void afterMoveHook( Integer[] newPosCobraFrente )
	{
		// Verifica se o jogo terminou por colisão de cobra com ela própria ou paredes da arena
		if(	newPosCobraFrente[0] >= getTabuleiro().length || newPosCobraFrente[0] < 0 ||
			newPosCobraFrente[1] >= getTabuleiro()[0].length || newPosCobraFrente[1] < 0 ||
			isPosTrue( newPosCobraFrente[0], newPosCobraFrente[1]) )
		{
			getEngine().setGameOver(true);
			return;
		}

		// Adiciona a nova posição da frente da cobra ao ArrayList
		posCobra.add( newPosCobraFrente );
		setTabuleiro( newPosCobraFrente[0], newPosCobraFrente[1], true);
	}

	/** Move a cobra para cima */
	private void moveUp()
	{
		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0];
		newPosCobraFrente[1] = posCobraFrente[1] + 1;

		afterMoveHook(newPosCobraFrente);
	}

	/** Move a cobra para baixo */
	private void moveDown()
	{
		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0];
		newPosCobraFrente[1] = posCobraFrente[1] - 1;

		afterMoveHook(newPosCobraFrente);
	}

	/** Move a cobra para a esquerda */
	private void moveLeft()
	{
		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0] - 1;
		newPosCobraFrente[1] = posCobraFrente[1];

		afterMoveHook(newPosCobraFrente);
	}

	/** Move a cobra para a direita */
	private void moveRight()
	{
		Integer[] posCobraFrente = posCobra.get( posCobra.size()-1 );
		Integer[] newPosCobraFrente = new Integer[2];

		newPosCobraFrente[0] = posCobraFrente[0] + 1;
		newPosCobraFrente[1] = posCobraFrente[1];

		afterMoveHook(newPosCobraFrente);
	}

	/** Move a posição de trás da cobra para a frente */
	private void movePosTrasForward()
	{
		// Fazer crescer a cobra não movendo a posição de trás
		if( timesNotToMovePosTras > 0 )
		{
			timesNotToMovePosTras--;
			return;
		}

		// Mover a posição de trás da cobra para a frente
		if( posCobra.size() > 1)
		{
			Integer[] posCobraTras = posCobra.get(0);
			getTabuleiro()[ posCobraTras[0] ][ posCobraTras[1] ] = false;
			posCobra.remove(posCobraTras);
		}
	}
}