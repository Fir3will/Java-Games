package popouspelican;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Vars;
import main.utils.Rand;

public class StartPP extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private PlayerPP player;
	private int counter = 0;
	private int RAND = Rand.nextInt(getWidth() - 70), score;
	private boolean inGame = true;
	private boolean scoredHigher = false;

	public StartPP()
	{
		addKeyListener(this);
		setFocusable(true);
		setDoubleBuffered(true);

		player = new PlayerPP(400, 300);

		timer = new Timer(10, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		player.init();

		repaint();
	}

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

	@Override
	public void keyTyped(KeyEvent e)
	{
		player.keyPressed(e);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		counter++;
		if (Vars.difficultyLevel.equals("Normal"))
		{
			counter += 2;
		}
		if (Vars.difficultyLevel.equals("Hard"))
		{
			counter += 3;
		}

		if (counter >= 600)
		{
			RAND = Rand.nextInt(getWidth() - 70);
			counter = 0;
		}

		if (inGame)
		{
			Rectangle r1 = new Rectangle(0, counter, RAND, 20);
			Rectangle r2 = new Rectangle(RAND + 70, counter, getWidth(), 20);
			Rectangle r3 = new Rectangle(RAND + 1, counter, RAND + 69, 20);

			g2d.setColor(Color.BLACK);
			g2d.drawString("x: " + player.x, 5, 15);
			g2d.drawString("y: " + player.y, 5, 30);
			g2d.drawString("Counter: " + counter + ", 600 is a Reset :D", 5, 45);
			g2d.drawString("Score: " + score + "", 5, 60);
			g2d.drawRect(0, counter, RAND, 20);
			g2d.drawRect(10, counter - 10, RAND, 20);
			g2d.drawLine(0, counter, 10, counter - 10);
			g2d.drawLine(0, counter + 20, 10, counter + 10);
			g2d.drawLine(RAND, counter, RAND + 10, counter - 10);
			g2d.drawLine(RAND, counter + 20, RAND + 10, counter + 10);
			g2d.drawRect(RAND + 70, counter, getWidth(), 20);
			g2d.drawRect(RAND + 80, counter - 10, getWidth(), 20);
			g2d.drawLine(RAND + 70, counter, RAND + 80, counter - 10);
			g2d.drawLine(RAND + 70, counter + 20, RAND + 80, counter + 10);

			g2d.setColor(Color.BLACK);

			player.draw(g2d, this);

			if (player.getBounds().intersects(r1) || player.getBounds().intersects(r2))
			{
				inGame = false;
			}

			if (player.getBounds().intersectsLine(r3.getMinX(), r3.getCenterY(), r3.getMaxX(), r3.getCenterY()))
			{
				score++;

				if (score >= Vars.ppMaxScore)
				{
					System.out.println("New PP Highscore Set!");
					scoredHigher = true;
				}
				else
				{
					scoredHigher = false;
				}

				if (scoredHigher)
				{
					Vars.ppMaxScore = score;
				}
			}
		}
		else
		{
			String msg = "Ughh, You Stupid Popous Pelican! You're Score was " + score + "!";
			Font small = new Font("Helvetica", Font.ITALIC, 24);
			FontMetrics metr = getFontMetrics(small);

			g2d.setColor(Color.BLUE);
			g2d.setFont(small);
			g2d.drawString(msg, (getWidth() - metr.stringWidth(msg)) / 2, getHeight() / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}