package customcode.vectest;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Vars;
import main.utils.Rand;
import customcode.CodingMod;

public class TestFrame extends JPanel implements ActionListener, Serializable
{
	private static boolean isPlaying;
	private Timer timer;
	private static final long serialVersionUID = 1L;
	public final ArrayList<EntityVT> sprites = new ArrayList<EntityVT>();
	public PlayerVT player;
	public ArrayList<ZombieVT> entity = new ArrayList<ZombieVT>();
	public boolean isPaused;
	public Level levelObj;

	public TestFrame()
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Vars.playerColor.brighter());
		setDoubleBuffered(true);
		setSize(400, 600);

		player = new PlayerVT(this);

		if (levelObj == null)
		{
			levelObj = new Level(this);
		}
		CodingMod.instance.startLoading();

		isPlaying = true;

		timer = new Timer(25, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.gc();

		if (!isPlaying)
		{
			timer.stop();
		}

		levelObj.run();

		ArrayList<EntityVT> removes = new ArrayList<EntityVT>();

		for (EntityVT sprite : sprites)
		{
			if (!sprite.isDestroyed())
			{
				sprite.init();
			}
			else
			{
				removes.add(sprite);
			}
		}

		for (EntityVT sprite : removes)
		{
			sprites.remove(sprite);
		}

		checkCollisions();
		repaint();
	}

	public void checkCollisions()
	{
		for (EntityVT spriteA : sprites)
		{
			for (EntityVT spriteB : sprites)
			{
				if (!spriteA.equals(spriteB) && spriteA.getBounds().intersects(spriteB.getBounds()))
				{
					spriteA.collidedWith(spriteB);
					// spriteB.collidedWith(spriteA);
				}
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		if (player.health > 0)
		{
			if (isPaused)
			{
				Font defaultFont = g2d.getFont();
				Font monaco = new Font("Monaco", Font.PLAIN, 24);
				g2d.setFont(monaco);
				int toAdd = g2d.getFontMetrics().stringWidth("Paused, P to Unpause") / 2;
				g2d.drawString("Paused, P to Unpause", 200 - toAdd, 300);
				g2d.setFont(defaultFont);
			}

			for (EntityVT sprite : sprites)
			{
				sprite.drawSprite(g2d, this);
			}

			levelObj.drawGame(g2d);
		}
		else
		{
			Font defaultFont = g2d.getFont();
			Font monaco = new Font("Monaco", Font.PLAIN, 24);
			g2d.setFont(monaco);
			int toAdd = g2d.getFontMetrics().stringWidth("Game Over") / 2;
			g2d.drawString("Game Over", 200 - toAdd, 300);
			g2d.setFont(defaultFont);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public class TAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			player.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			player.keyReleased(e);
		}
	}

	private int rand = Rand.nextInt();

	public int getRand()
	{
		return rand;
	}
}
