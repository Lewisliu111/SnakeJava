package engine;

import java.util.Random;

public class Aleatorio
{
	// Objecto Random
	private static Random random = new Random();

	/** Constructor deixado vazio intencionalmente */
	public Aleatorio(){}

	/** Gera um inteiro aleat�rio entre 0 (inclusiv�) e n (exculsiv�) */
	public int nextInt(int n)
	{
		return random.nextInt(n);
	}
}