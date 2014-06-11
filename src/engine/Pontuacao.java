package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import util.Util;

public class Pontuacao
{
	// Objecto do motor de jogo
	private Engine e;

	// Variáveis da pontuação
	private int valor = 0;
	private int nivel = 1;

	/** Construtor */
	public Pontuacao(Engine e)
	{
		this.e = e;
	}

	/** Devolve o nível da pontuacao */
	public int getNivel()
	{
		return nivel;
	}

	/** Modifica o nível da pontuacao */
	public void setNivel(int nivel)
	{
		this.nivel = nivel;
	}

	/** Devolve o valor da pontuação */
	public int getValor()
	{
		return valor;
	}

	/** Modifica o valor da pontuação */
	public void setValor(int valor)
	{
		this.valor = valor;
	}

	/** Adiciona ao valor da pontuação */
	public void addValor(int valor)
	{
		this.valor += valor;
	}

	/** Devolve o conteúdo do ficheiro com as pontuações */
	public String toString()
	{
		String ret = new String("");

		Scanner in;
		try
		{
			in = new Scanner( new File(Engine.TOP_SCORES_FILE) );
		}
		catch (FileNotFoundException e)
		{
			return null;
		}

		while( in.hasNextLine() )
			ret += in.nextLine() + Util.EOL;

		in.close();

		return ret;
	}

	/** Adiciona uma pontuação ao ficheiro das pontuações */
	public boolean add()
	{
		// Obter pontuações
		String contents = toString();

		// Preparar para escrita
		PrintStream out;
		try
		{
			out = new PrintStream( new File(Engine.TOP_SCORES_FILE) );
		}
		catch (FileNotFoundException e)
		{
			return false;
		}

		// Escrever os conteúdos do ficheiro
		if(contents != null)
			out.print(contents);

		// Adicionar nova pontuação
		out.println(valor+"\t"+e.getNomeJogador());

		// Fechar o ficheiro
		out.close();

		// Correr a função de ordenação e Devolver o seu resultado
		return sort();
	}

	/** Organiza o ficheiro das pontuações */
	private boolean sort()
	{
		// Obter pontuações
		String contents = toString();

		if(contents == null)
			return false;

		String[] scoresArr = contents.split( Util.EOL ); // Array de pontuações
		int[] scoresVals = new int[ scoresArr.length ]; // Array com valores
		String[][] scoresNomes = new String[ scoresArr.length ][2]; // Array com valores e nomes

		// Preencher scoreVals e scoreNomes
		for(int i=0; i<scoresArr.length; i++)
		{
			String[] scoresTemp = scoresArr[i].trim().split("\t");

			if( !scoresTemp[1].isEmpty() ) // Nome
			{
				scoresVals[i] = Util.parseInt( scoresTemp[0] ); // Valor
				scoresNomes[i][0] = scoresTemp[0]; // Valor
				scoresNomes[i][1] = scoresTemp[1]; // Nome
			}
		}

		// Ordenar scoreVals
		Arrays.sort( scoresVals );

		// Preparar para escrita
		PrintStream out;
		try
		{
			out = new PrintStream( new File(Engine.TOP_SCORES_FILE) );
		}
		catch (FileNotFoundException e)
		{
			return false;
		}

		// Escrever no ficheiro
		for(int i=0; i<scoresArr.length; i++)
		{
			// Para só escrever até ao máximo de pontuações
			if( i>=Engine.MAX_TOP_SCORES )
				break;

			int pos = scoresArr.length-1-i; // Posição do valor
			int valor = scoresVals[pos]; // Valor

			// Encontrar o nome
			String nome = new String("");
			for(int j=0; j<scoresArr.length; j++)
			{
				if( scoresNomes[j][0].equals( ""+scoresVals[pos] ) )
				{
					nome = scoresNomes[j][1]; // Nome
					break;
				}
			}

			if( !nome.isEmpty() )
			{
				out.println(valor+"\t"+nome); // Escrever
			}
		}

		// Fechar o ficheiro
		out.close();

		return true;
	}
}