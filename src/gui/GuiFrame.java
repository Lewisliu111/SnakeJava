package gui;

import javax.swing.JFrame;

@SuppressWarnings({ "serial" })
public class GuiFrame extends JFrame
{
	/** Construtor público */
	public GuiFrame()
	{
		javax.swing.SwingUtilities.invokeLater(
			new Runnable(){ public void run(){ gui(); } }
		);
	}

	/** Construtor privado da frame */
	private GuiFrame(String name)
	{
		super(name);
	}

	/** Interface Gráfica */
	private void gui()
	{
		// Criar janela
		GuiFrame frame = new GuiFrame("Jogo da cobra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Adiciona o JPanel do jogo
		frame.add(new GuiPanelGame(this));

		// Mostrar janela
		frame.pack();
		frame.setVisible(true);
	}
}