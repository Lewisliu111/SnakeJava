package engine;

public class TocaTemporaria extends Toca
{
	// Número de ratos na toca
	private int numRatos;

	// Número de vezes em que toca permanente foi comida sem haver toca temporária
	private int numTocaPermanenteComida;

	// Número de movimentos para tentar apanhar a toca temporária
	private int numMovimentos;

	/** Construtor */
	public TocaTemporaria(Engine e)
	{
		super(e);
	}

	/** Devolve o número de ratos na toca */
	public int getNumRatos()
	{
		return numRatos;
	}

	/** Método de acção */
	public void doAction()
	{
		// Verificar se não existe toca
		if( isTocaNull() )
		{
			// Incrementar número de vezes em que se comeu toca permanente e correr método de adição
			if( getEngine().getTocaPermanente().check() )
			{
				numTocaPermanenteComida++;
				add();
			}

			return;
		}

		// Verificar se não comeu a toca
		if( !check() )
		{
			// Incrementar número de movimentos para tentar apanhar a toca temporária
			numMovimentos++;

			// Decrescer o número de ratos a cada Engine.NUM_COLUMNS/2 movimentos
			if( numMovimentos%(Engine.NUM_COLUMNS/2) == 0 && numMovimentos != 0)
				numRatos--;

			// Remover toca se não houver ratos
			if(numRatos == 0)
				remove();

			return;
		}

		// Incrementar a pontuação
		getEngine().getPontuacao().addValor(numRatos);

		// Fazer crescer a cobra
		getEngine().getCobra().setTimesNotToMovePosTras( getEngine().getCobra().getTimesNotToMovePosTras()+numRatos );

		// Remover toca
		remove();
	}

	/** Método de adição */
	public void add()
	{
		// Adicionar toca a cada 3 vezes
		if( numTocaPermanenteComida%3 != 0 || numTocaPermanenteComida == 0)
			return;

		// Número aleatório de ratos
		numRatos = getAleatorio().nextInt(5) + 5;

		Integer posNewToca[] = { getAleatorio().nextInt(Engine.NUM_COLUMNS), getAleatorio().nextInt(Engine.NUM_LINES) };

		if( !isPosFree(posNewToca[0], posNewToca[1]) )
		{
			add();
			return;
		}

		setPosToca(posNewToca);
	}

	/** Método de remoção */
	public void remove()
	{
		numTocaPermanenteComida = 0;
		numMovimentos = 0;
		setPosToca(null);
	}
}