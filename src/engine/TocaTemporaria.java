package engine;

public class TocaTemporaria extends Toca
{
	// N�mero de ratos na toca
	private int numRatos;

	// N�mero de vezes em que toca permanente foi comida sem haver toca tempor�ria
	private int numTocaPermanenteComida;

	// N�mero de movimentos para tentar apanhar a toca tempor�ria
	private int numMovimentos;

	/** Construtor */
	public TocaTemporaria(Engine e)
	{
		super(e);
	}

	/** Devolve o n�mero de ratos na toca */
	public int getNumRatos()
	{
		return numRatos;
	}

	/** M�todo de ac��o */
	public void doAction()
	{
		// Verificar se n�o existe toca
		if( isTocaNull() )
		{
			// Incrementar n�mero de vezes em que se comeu toca permanente e correr m�todo de adi��o
			if( getEngine().getTocaPermanente().check() )
			{
				numTocaPermanenteComida++;
				add();
			}

			return;
		}

		// Verificar se n�o comeu a toca
		if( !check() )
		{
			// Incrementar n�mero de movimentos para tentar apanhar a toca tempor�ria
			numMovimentos++;

			// Decrescer o n�mero de ratos a cada Engine.NUM_COLUMNS/2 movimentos
			if( numMovimentos%(Engine.NUM_COLUMNS/2) == 0 && numMovimentos != 0)
				numRatos--;

			// Remover toca se n�o houver ratos
			if(numRatos == 0)
				remove();

			return;
		}

		// Incrementar a pontua��o
		getEngine().getPontuacao().addValor(numRatos);

		// Fazer crescer a cobra
		getEngine().getCobra().setTimesNotToMovePosTras( getEngine().getCobra().getTimesNotToMovePosTras()+numRatos );

		// Remover toca
		remove();
	}

	/** M�todo de adi��o */
	public void add()
	{
		// Adicionar toca a cada 3 vezes
		if( numTocaPermanenteComida%3 != 0 || numTocaPermanenteComida == 0)
			return;

		// N�mero aleat�rio de ratos
		numRatos = getAleatorio().nextInt(5) + 5;

		Integer posNewToca[] = { getAleatorio().nextInt(Engine.NUM_COLUMNS), getAleatorio().nextInt(Engine.NUM_LINES) };

		if( !isPosFree(posNewToca[0], posNewToca[1]) )
		{
			add();
			return;
		}

		setPosToca(posNewToca);
	}

	/** M�todo de remo��o */
	public void remove()
	{
		numTocaPermanenteComida = 0;
		numMovimentos = 0;
		setPosToca(null);
	}
}