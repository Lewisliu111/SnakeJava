package engine;

public class TocaPermanente extends Toca
{
	/** Construtor */
	public TocaPermanente(Engine e)
	{
		super(e);
	}

	/** Adiciona a toca */
	public void add()
	{
		Integer posNewToca[] = { getAleatorio().nextInt(Engine.NUM_COLUMNS), getAleatorio().nextInt(Engine.NUM_LINES) };

		if( !isPosFree(posNewToca[0], posNewToca[1]) )
		{
			add();
			return;
		}

		setPosToca(posNewToca);
	}

	/** Método de acção */
	public void doAction()
	{
		if( !check() )
			return;

		// Incrementar a pontuação
		getEngine().getPontuacao().addValor(1);

		// Fazer crescer a cobra
		getEngine().getCobra().setTimesNotToMovePosTras( getEngine().getCobra().getTimesNotToMovePosTras()+1 );

		// Adicionar toca novamente
		add();
	}
}