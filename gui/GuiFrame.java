package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings({ "serial" })
public class GuiFrame extends JFrame implements KeyListener
{
	// Objecto do GUI
	private GuiPanel guiPanel = new GuiPanel();

	/** Constructor público */
	public GuiFrame()
	{
		javax.swing.SwingUtilities.invokeLater(
			new Runnable(){ public void run(){ gui(); } }
		);
	}

	/** Constructor privado da frame */
	private GuiFrame(String name)
	{
		super(name);
	}

	/** Cria uma nova instância da frame */
	private void init()
	{
		Container pane=getContentPane();
		pane.add(guiPanel);
		addKeyListener(this);
	}

	/** Interface Gráfica */
	private void gui()
	{
		// Criar janela
		GuiFrame frame = new GuiFrame("Jogo da cobra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(frame);

		// Criar nova instância
		frame.init();

		// Mostrar janela
		frame.pack();
		frame.setVisible(true);
	}

	/** Move a cobra */
	private void move(int keyCode)
	{
		guiPanel.move(keyCode);
	}

	/** Captura acção de pressão de tecla */
	public void keyPressed(KeyEvent e)
	{
		move( e.getKeyCode() );
	}

	@Override
	public void keyReleased(KeyEvent e){ /* Não implementado */ }

	@Override
	public void keyTyped(KeyEvent e){ /* Não implementado */ }
}