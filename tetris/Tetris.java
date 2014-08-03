package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import main.Vars;
import main.games.GamePanel;
import main.utils.Keys;
import tetris.ShapeT.Tetrominoes;

public class Tetris extends GamePanel
{
	private static final long serialVersionUID = 1L;

	private Tetrominoes[] board;
	private final int BoardHeight = 22;
	private final int BoardWidth = 10;
	private ShapeT curPiece;
	private int curX = 0;
	private int curY = 0;
	private boolean isFallingFinished = false;
	private boolean isPaused = false;
	private boolean isStarted = false;
	private int numLinesRemoved = 0;
	private JLabel statusbar;

	public Tetris()
	{
		super(400, 100, 220);
	}

	private void clearBoard()
	{
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
		{
			board[i] = Tetrominoes.NoShape;
		}
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape)
	{
		Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102), new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204), new Color(218, 170, 0)};

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);

	}

	private void dropDown()
	{
		int newY = curY;
		while (newY > 0)
		{
			if (!tryMove(curPiece, curX, newY - 1))
			{
				break;
			}
			--newY;
		}
		pieceDropped();
	}

	private void newPiece()
	{
		curPiece.setRandomShape();
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY))
		{
			curPiece.setShape(Tetrominoes.NoShape);
			stop();
			isStarted = false;
			statusbar.setText("game over");
		}
	}

	private void oneLineDown()
	{
		if (!tryMove(curPiece, curX, curY - 1))
		{
			pieceDropped();
		}
	}

	private void pause()
	{
		if (!isStarted) return;

		isPaused = !isPaused;

		if (isPaused)
		{
			stop();
			statusbar.setText("paused");
		}
		else
		{
			start();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}
		repaint();
	}

	private void pieceDropped()
	{
		for (int i = 0; i < 4; ++i)
		{
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[y * BoardWidth + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
		{
			newPiece();
		}
	}

	private void removeFullLines()
	{
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i)
		{
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j)
			{
				if (shapeAt(j, i) == Tetrominoes.NoShape)
				{
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull)
			{
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k)
				{
					for (int j = 0; j < BoardWidth; ++j)
					{
						board[k * BoardWidth + j] = shapeAt(j, k + 1);
					}
				}
			}
		}

		if (numFullLines > 0)
		{
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		}
	}

	Tetrominoes shapeAt(int x, int y)
	{
		return board[y * BoardWidth + x];
	}

	int squareHeight()
	{
		return (int) getSize().getHeight() / BoardHeight;
	}

	int squareWidth()
	{
		return (int) getSize().getWidth() / BoardWidth;
	}

	public void begin()
	{
		if (isPaused) return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
	}

	private boolean tryMove(ShapeT newPiece, int newX, int newY)
	{
		for (int i = 0; i < 4; ++i)
		{
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape) return false;
		}

		curPiece = newPiece;
		curX = newX;
		curY = newY;
		repaint();
		return true;
	}

	@Override
	public void init()
	{
		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		setFocusable(true);
		curPiece = new ShapeT();

		setBackground(Vars.playerColor);
		board = new Tetrominoes[BoardWidth * BoardHeight];
		clearBoard();
	}

	@Override
	public void updateGame(int ticks)
	{
		if (isFallingFinished)
		{
			isFallingFinished = false;
			newPiece();
		}
		else
		{
			oneLineDown();
		}
	}

	@Override
	public void drawGameScreen(Graphics2D g)
	{
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		for (int i = 0; i < BoardHeight; ++i)
		{
			for (int j = 0; j < BoardWidth; ++j)
			{
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
				{
					drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape);
				}
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape)
		{
			for (int i = 0; i < 4; ++i)
			{
				int x = curX + curPiece.x(i);
				int y = curY - curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), curPiece.getShape());
			}
		}
	}

	@Override
	public void keyPressed(Keys key)
	{
		if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) return;

		if (key == Keys.KEY_P)
		{
			pause();
			return;
		}

		if (isPaused) return;

		switch (key)
		{
			case KEY_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KEY_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KEY_DOWN:
				tryMove(curPiece.rotateRight(), curX, curY);
				break;
			case KEY_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
				break;
			case KEY_SPACE:
				dropDown();
				break;
			case KEY_D:
				oneLineDown();
				break;
			default:
				break;
		}

	}

	@Override
	public String getGameName()
	{
		return "Tetris";
	}
}