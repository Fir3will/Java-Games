package main.game;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Save;
import main.Vars;
import main.saving.CompoundTag;
import main.saving.DataTag;
import main.utils.Keys;

public abstract class Game extends JPanel implements ActionListener
{
	public GameFrame parent;
	private final Timer timer;
	protected int ticks;
	public int width, height;

	public Game()
	{
		this(-1, -1);
	}

	public Game(int width, int height)
	{
		this(0, width, height);
	}

	public Game(int fps, int width, int height)
	{
		addKeyListener(new TKeyAdapter());
		TMouseAdapter a = new TMouseAdapter();
		addMouseListener(a);
		addMouseMotionListener(a);
		addMouseWheelListener(a);
		setFocusable(true);
		setDoubleBuffered(true);
		setSize(width, height);
		init();
		DataTag tag = new DataTag();
		tag = DataTag.loadFrom(new File(Vars.SAVE_DIR + Save.PLAYER_NAME + "/" + getClass().getSimpleName() + ".dat"));
		loadGameFromTag(tag);

		this.width = width;
		this.height = height;
		timer = new Timer(fps, this);
		timer.start();
	}

	public LayoutManager getManager()
	{
		return new FlowLayout();
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
		final Graphics2D g2d = (Graphics2D) g;

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
		final File file = new File(CompoundTag.PLAYER_FOLDER, "/" + (this.getClass().isAnnotationPresent(NewGame.class) ? this.getClass().getAnnotation(NewGame.class).name() : "NULL") + ".dat");
		final File file2 = new File(CompoundTag.PLAYER_FOLDER, "/" + (this.getClass().isAnnotationPresent(NewGame.class) ? this.getClass().getAnnotation(NewGame.class).name() : "NULL"));

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

	private class TKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			Game.this.keyPressed(Keys.getKey(e.getKeyCode()));
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			Game.this.keyReleased(Keys.getKey(e.getKeyCode()));
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			Game.this.keyTyped(Keys.getKey(e.getKeyCode()));
		}
	}

	public void keyPressed(Keys key)
	{}

	public void keyReleased(Keys key)
	{}

	public void keyTyped(Keys key)
	{}

	private class TMouseAdapter extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			Game.this.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			Game.this.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			Game.this.mouseReleased(e);
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			Game.this.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			Game.this.mouseExited(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			Game.this.mouseWheelMoved(e);
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			Game.this.mouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			Game.this.mouseMoved(e);
		}
	}

	public void mouseClicked(MouseEvent e)
	{}

	public void mousePressed(MouseEvent e)
	{}

	public void mouseReleased(MouseEvent e)
	{}

	public void mouseEntered(MouseEvent e)
	{}

	public void mouseExited(MouseEvent e)
	{}

	public void mouseWheelMoved(MouseWheelEvent e)
	{}

	public void mouseDragged(MouseEvent e)
	{}

	public void mouseMoved(MouseEvent e)
	{}

	public final int getTicks()
	{
		return ticks;
	}

	public void setupFrame(GameFrame gameFrame)
	{}

	public void loadGameFromTag(DataTag tag)
	{}

	public void saveGameToTag(DataTag tag)
	{}

	private static final long serialVersionUID = 1L;
}
