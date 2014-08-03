package main.games;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private GamePanel game;

	public GameFrame(GamePanel game, int width, int height)
	{
		this.game = game;
		add(game);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(width, height);
		addWindowListener(wl);
		setLocationRelativeTo(null);
		setTitle(game.getGameName());
		setResizable(false);
		setVisible(true);
	}

	private WindowListener wl = new WindowListener()
	{
		@Override
		public void windowOpened(WindowEvent e)
		{
			game.setVisible(true);
			game.start();
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			game.setVisible(false);
			game.stop();
		}

		@Override
		public void windowClosing(WindowEvent e)
		{}

		@Override
		public void windowIconified(WindowEvent e)
		{}

		@Override
		public void windowDeiconified(WindowEvent e)
		{}

		@Override
		public void windowActivated(WindowEvent e)
		{}

		@Override
		public void windowDeactivated(WindowEvent e)
		{}
	};
}