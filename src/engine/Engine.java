package engine;

public class Engine
{
	// Constantes das pontuações
	public static final String TOP_SCORES_FILE="TopScores.txt";
	public static final int MAX_TOP_SCORES=10;

	// Constantes do tabuleiro
	public static final int NUM_LINES=35;
	public static final int NUM_COLUMNS=40;

	// Objectos do jogo
	private Cobra cobra;
	private Obstaculos obstaculos;
	private Pontuacao pontuacao;
	private TocaPermanente tocaPermanente;
	private TocaTemporaria tocaTemporaria;

	// Variáveis do jogo
	private int nivelDeDificuldade;
	private boolean gameOver = false;

	// Nome do jogador
	private String nomeJogador = new String("");

	/** Construtor */
	public Engine(int nivelDeDificuldade)
	{
		this.nivelDeDificuldade = nivelDeDificuldade;

		// Incializar objectos do jogo
		cobra = new Cobra(this);
		obstaculos = new Obstaculos(this);
		pontuacao = new Pontuacao(this);
		tocaPermanente = new TocaPermanente(this);
		tocaTemporaria = new TocaTemporaria(this);
	}

	/** Devolve o nome do jogador */
	public String getNomeJogador()
	{
		return nomeJogador;
	}

	/** Define o nome do jogador */
	public void setNomeJogador(String nomeJogador)
	{
		this.nomeJogador = nomeJogador;
	}

	/** Devolve o objecto da cobra */
	public Cobra getCobra()
	{
		return cobra;
	}

	/** Devolve o objecto dos obstaculos */
	public Obstaculos getObstaculos()
	{
		return obstaculos;
	}

	/** Devolve o objecto da pontuacao */
	public Pontuacao getPontuacao()
	{
		return pontuacao;
	}

	/** Devolve o objecto da toca permananente */
	public TocaPermanente getTocaPermanente()
	{
		return tocaPermanente;
	}

	/** Devolve o objecto da toca temporaria */
	public TocaTemporaria getTocaTemporaria()
	{
		return tocaTemporaria;
	}

	/** Devolve se o jogo terminou */
	public boolean isGameOver()
	{
		return gameOver;
	}

	/** Define o estado de jogo terminado */
	public void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
	}

	/** Devolve o nivel de dificuldade */
	public int getNivelDeDificuldade()
	{
		return nivelDeDificuldade;
	}

	/** Inicia o jogo */
	public void init()
	{
		getCobra().add();
		getObstaculos().add();
		getTocaTemporaria().add();
		getTocaPermanente().add();
	}

	/** Executa o jogo */
	public void run()
	{
		getObstaculos().doAction();
		getTocaTemporaria().doAction();
		getTocaPermanente().doAction();
		getCobra().doAction();
	}
}