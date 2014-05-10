import engine.Pontuacao;

public class Teste
{
	// Constantes
	private static final String TOP_SCORES_FILE="TopScoresTest.txt";
	private static final int MAX_TOP_SCORES=10;

	// Objectos
	private static Pontuacao pontuacao = new Pontuacao(MAX_TOP_SCORES, TOP_SCORES_FILE);

	/** Método principal */
	public static void main(String[] args)
	{
		pontuacaoTeste();
	}

	/** Teste da pontuação */
	private static void pontuacaoTeste()
	{
		for( int i=100; i<200; i++)
		{
			pontuacao.add(i, "Jorge");
		}

		System.out.println( pontuacao.toString() );
	}
}