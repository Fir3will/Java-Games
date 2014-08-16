package main.games;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.utils.CompoundTag;

public class GameFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private GamePanel game;

	public GameFrame(GamePanel game, int width, int height)
	{
		this.game = game;
		game.parent = this;
		add(game);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(width, height);
		addWindowListener(wl);
		setLocationRelativeTo(null);
		setTitle(game.getClass().isAnnotationPresent(NewGame.class) ? game.getClass().getAnnotation(NewGame.class).name() : "NULL");
		setResizable(false);
		setVisible(true);
	}

	private WindowListener wl = new WindowListener()
	{
		@Override
		public void windowOpened(WindowEvent e)
		{}

		@Override
		public void windowClosed(WindowEvent e)
		{
			System.out.println("Closed");
			game.onGameClose(new CompoundTag(game));
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