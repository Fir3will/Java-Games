package main.games;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.utils.CompoundTag;
import main.utils.Keys;
import main.utils.helper.EnumHelper;
import modhandler.EnumGame;
import modhandler.Importer;

public abstract class GamePanel extends JPanel implements ActionListener
{
	transient GameFrame parent;
	private final Timer timer;
	private int ticks = 0;
	public final EnumGame GAME = EnumHelper.addGame(getClass().isAnnotationPresent(NewGame.class) ? getClass().getAnnotation(NewGame.class).name() : "NULL");

	public GamePanel(int width, int height)
	{
		this(0, width, height);
	}

	public GamePanel(int fps, int width, int height)
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		setSize(width, height);
		init();
		loadAfterInit(new CompoundTag(this));
		Importer.throwGameEvent(GAME);

		timer = new Timer(fps, this);
		timer.start();
	}

	public abstract void init();

	public abstract void updateGame(int ticks);

	public abstract void drawGameScreen(Graphics2D g2d);

	@Override
	public final void actionPerformed(ActionEvent e)
	{
		System.gc();

		updateGame(ticks++);

		repaint();
	}

	@Override
	public final void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		drawGameScreen(g2d);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public final void stop()
	{
		timer.stop();
	}

	public final void start()
	{
		timer.start();
	}

	public final void restart()
	{
		File file = new File(CompoundTag.PLAYER_FOLDER, "/" + (this.getClass().isAnnotationPresent(NewGame.class) ? this.getClass().getAnnotation(NewGame.class).name() : "NULL") + ".dat");
		File file2 = new File(CompoundTag.PLAYER_FOLDER, "/" + (this.getClass().isAnnotationPresent(NewGame.class) ? this.getClass().getAnnotation(NewGame.class).name() : "NULL"));

		if (file.exists())
		{
			file.delete();
		}
		if (file2.exists())
		{
			file2.delete();
		}

		stop();
		parent.dispose();
	}

	private class TAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			GamePanel.this.keyPressed(Keys.getKey(e.getKeyCode()));
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			GamePanel.this.keyReleased(Keys.getKey(e.getKeyCode()));
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			GamePanel.this.keyTyped(Keys.getKey(e.getKeyCode()));
		}
	}

	public void keyPressed(Keys key)
	{}

	public void keyReleased(Keys key)
	{}

	public void keyTyped(Keys key)
	{}

	public final int getTicks()
	{
		return ticks;
	}

	public void loadAfterInit(CompoundTag tag)
	{}

	public void onGameClose(CompoundTag tag)
	{}

	private static final long serialVersionUID = 1L;
}
