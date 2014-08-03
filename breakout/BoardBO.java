package breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import main.Vars;

public class BoardBO extends JPanel implements CommonsBO
{
	class ScheduleTask extends TimerTask
	{

		@Override
		public void run()
		{

			ball.move();
			paddle.move();
			checkCollision();
			repaint();

		}
	}

	private class TAdapter extends KeyAdapter
	{

		@Override
		public void keyPressed(KeyEvent e)
		{
			paddle.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			paddle.keyReleased(e);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BallBO ball;
	BrickBO bricks[];
	Image ii;
	boolean ingame = true;
	String message = "Game Over";

	PaddleBO paddle;
	private int score = 0;

	Timer timer;

	int timerId;

	public BoardBO()
	{

		addKeyListener(new TAdapter());
		setFocusable(true);

		bricks = new BrickBO[30];
		setDoubleBuffered(true);
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);

		setBackground(Vars.playerColor.brighter().brighter());
	}

	@Override
	public void addNotify()
	{
		super.addNotify();
		gameInit();
	}

	public void checkCollision()
	{

		if (ball.getRect().getMaxY() > CommonsBO.BOTTOM)
		{
			stopGame();
		}

		for (int i = 0, j = 0; i < 30; i++)
		{
			if (bricks[i].isDestroyed())
			{
				j++;
			}
			if (j == 30)
			{
				j = 0;
				if (Vars.difficultyLevel.equals("Easy"))
				{
					score += 5;
				}
				if (Vars.difficultyLevel.equals("Normal"))
				{
					score += 3;
				}
				if (Vars.difficultyLevel.equals("Hard"))
				{
					score += 1;
				}
				gameInit();
				// message = "Victory";
				// stopGame();
			}
		}

		if (ball.getRect().intersects(paddle.getRect()))
		{

			int paddleLPos = (int) paddle.getRect().getMinX();
			int ballLPos = (int) ball.getRect().getMinX();

			int first = paddleLPos + 8;
			int second = paddleLPos + 16;
			int third = paddleLPos + 24;
			int fourth = paddleLPos + 32;

			if (ballLPos < first)
			{
				ball.setXDir(-1);
				ball.setYDir(-1);
			}

			if (ballLPos >= first && ballLPos < second)
			{
				ball.setXDir(-1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos >= second && ballLPos < third)
			{
				ball.setXDir(0);
				ball.setYDir(-1);
			}

			if (ballLPos >= third && ballLPos < fourth)
			{
				ball.setXDir(1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos > fourth)
			{
				ball.setXDir(1);
				ball.setYDir(-1);
			}

		}

		for (int i = 0; i < 30; i++)
		{
			if (ball.getRect().intersects(bricks[i].getRect()))
			{

				int ballLeft = (int) ball.getRect().getMinX();
				int ballHeight = (int) ball.getRect().getHeight();
				int ballWidth = (int) ball.getRect().getWidth();
				int ballTop = (int) ball.getRect().getMinY();

				Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

				if (!bricks[i].isDestroyed())
				{
					if (bricks[i].getRect().contains(pointRight))
					{
						ball.setXDir(-1);
					}
					else if (bricks[i].getRect().contains(pointLeft))
					{
						ball.setXDir(1);
					}

					if (bricks[i].getRect().contains(pointTop))
					{
						ball.setYDir(1);
					}
					else if (bricks[i].getRect().contains(pointBottom))
					{
						ball.setYDir(-1);
					}

					bricks[i].setDestroyed(true);
					score++;
				}
			}
		}
	}

	public void gameInit()
	{

		ball = new BallBO();
		paddle = new PaddleBO();

		int k = 0;
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				bricks[k] = new BrickBO(j * 40 + 30, i * 10 + 50);
				k++;
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if (ingame)
		{
			String SCORE = "Score: " + score;

			int widthSpot = CommonsBO.WIDTH / 2 - SCORE.length() * 2 - 7;

			g.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
			g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), this);
			g.drawString(SCORE, widthSpot, 15);

			for (int i = 0; i < 30; i++)
			{
				if (!bricks[i].isDestroyed())
				{
					g.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getWidth(), bricks[i].getHeight(), this);
				}
			}
		}
		else
		{

			Font font = new Font("Verdana", Font.BOLD, 18);
			FontMetrics metr = getFontMetrics(font);

			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(message, (CommonsBO.WIDTH - metr.stringWidth(message)) / 2, CommonsBO.WIDTH / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void stopGame()
	{
		ingame = false;
		timer.cancel();
	}
}