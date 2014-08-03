package main.games;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.utils.Keys;
import main.utils.helper.EnumHelper;
import modhandler.EnumGame;
import modhandler.Importer;

public abstract class GamePanel extends JPanel implements ActionListener
{
	private boolean isPlaying;
	private final Timer timer;
	private int ticks = 0;
	public EnumGame GAME = EnumHelper.addGame(getGameName());

	public GamePanel(int fps, int width, int height)
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
		setSize(width, height);
		init();
		Importer.throwGameEvent(GAME);
		isPlaying = true;

		timer = new Timer(fps, this);
		timer.start();
	}

	public abstract void init();

	public abstract void updateGame(int ticks);

	public abstract void drawGameScreen(Graphics2D g2d);

	public abstract String getGameName();

	@Override
	public final void actionPerformed(ActionEvent e)
	{
		System.gc();

		if (!isPlaying)
		{
			timer.stop();
		}

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
		this.isPlaying = false;
	}

	public final void start()
	{
		this.isPlaying = true;
	}

	public class TAdapter extends KeyAdapter
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

	private static final long serialVersionUID = 1L;
}
