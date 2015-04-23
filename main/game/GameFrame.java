package main.game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.saving.CompoundTag;
import main.saving.DataTag;

public class GameFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Game game;

	public GameFrame(Game game)
	{
		this.game = game;
		game.parent = this;
		add(game);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(game.width, game.height);
		addWindowListener(wl);
		setLocationRelativeTo(null);
		setTitle(game.getClass().isAnnotationPresent(NewGame.class) ? game.getClass().getAnnotation(NewGame.class).name() : "NULL");
		setResizable(false);
		setVisible(true);
		game.setupFrame(this);
	}

	private final WindowListener wl = new WindowListener()
	{
		@Override
		public void windowOpened(WindowEvent e)
		{}

		@Override
		public void windowClosed(WindowEvent e)
		{
			System.out.println("Closed");
			final DataTag tag = new DataTag();
			game.saveGameToTag(tag);
			DataTag.saveTo(new File(CompoundTag.PLAYER_FOLDER, "/" + game.getClass().getSimpleName() + ".dat"), tag);
			game.setVisible(false);
			game.stop();
			Games.setPlaying(false);
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