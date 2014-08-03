package tetris;

import java.util.Random;

public class ShapeT
{
	enum Tetrominoes
	{
		LineShape, LShape, MirroredLShape, NoShape, SquareShape, SShape, TShape, ZShape
	};

	private int coords[][];
	private int[][][] coordsTable;
	private Tetrominoes pieceShape;

	public ShapeT()
	{
		coords = new int[4][2];
		setShape(Tetrominoes.NoShape);
	}

	public Tetrominoes getShape()
	{
		return pieceShape;
	}

	public int minX()
	{
		int m = coords[0][0];
		for (int i = 0; i < 4; i++)
		{
			m = Math.min(m, coords[i][0]);
		}
		return m;
	}

	public int minY()
	{
		int m = coords[0][1];
		for (int i = 0; i < 4; i++)
		{
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	public ShapeT rotateLeft()
	{
		if (pieceShape == Tetrominoes.SquareShape) return this;

		ShapeT result = new ShapeT();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i)
		{
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}
		return result;
	}

	public ShapeT rotateRight()
	{
		if (pieceShape == Tetrominoes.SquareShape) return this;

		ShapeT result = new ShapeT();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i)
		{
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}
		return result;
	}

	public void setRandomShape()
	{
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}

	public void setShape(Tetrominoes shape)
	{

		coordsTable = new int[][][] { { {0, 0}, {0, 0}, {0, 0}, {0, 0}}, { {0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, { {0, -1}, {0, 0}, {1, 0}, {1, 1}}, { {0, -1}, {0, 0}, {0, 1}, {0, 2}}, { {-1, 0}, {0, 0}, {1, 0}, {0, 1}}, { {0, 0}, {1, 0}, {0, 1}, {1, 1}}, { {-1, -1}, {0, -1}, {0, 0}, {0, 1}}, { {1, -1}, {0, -1}, {0, 0}, {0, 1}}};

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 2; ++j)
			{
				coords[i][j] = coordsTable[shape.ordinal()][i][j];
			}
		}
		pieceShape = shape;

	}

	private void setX(int index, int x)
	{
		coords[index][0] = x;
	}

	private void setY(int index, int y)
	{
		coords[index][1] = y;
	}

	public int x(int index)
	{
		return coords[index][0];
	}

	public int y(int index)
	{
		return coords[index][1];
	}
}