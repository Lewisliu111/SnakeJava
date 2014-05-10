package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings({ "serial" })
public class GuiFrame extends JFrame implements KeyListener
{
	// Objecto do GUI
	private GuiPanel guiPanel = new GuiPanel();

	/** Constructor p�blico */
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

	/** Cria uma nova inst�ncia da frame */
	private void init()
	{
		Container pane=getContentPane();
		pane.add(guiPanel);
		addKeyListener(this);
	}

	/** Interface Gr�fica */
	private void gui()
	{
		// Criar janela
		GuiFrame frame = new GuiFrame("Jogo da cobra");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(frame);

		// Criar nova inst�ncia
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

	/** Captura ac��o de press�o de tecla */
	public void keyPressed(KeyEvent e)
	{
		move( e.getKeyCode() );
	}

	@Override
	public void keyReleased(KeyEvent e){ /* N�o implementado */ }

	@Override
	public void keyTyped(KeyEvent e){ /* N�o implementado */ }
}