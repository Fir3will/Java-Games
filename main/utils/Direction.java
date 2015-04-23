package main.utils;

import main.saving.DataTag;

public enum Direction
{
	DOWN(0, -1, 0),
	UP(0, 1, 0),
	NORTH(0, 0, -1),
	SOUTH(0, 0, 1),
	WEST(-1, 0, 0),
	EAST(1, 0, 0),
	UNKNOWN(0, 0, 0);

	public final int offsetX;
	public final int offsetY;
	public final int offsetZ;
	public final int flag;

	private Direction(int x, int y, int z)
	{
		offsetX = x;
		offsetY = y;
		offsetZ = z;
		flag = 1 << ordinal();
	}

	public static Direction getOrientation(int id)
	{
		if (id >= 0 && id < VALID_DIRECTIONS.length) return VALID_DIRECTIONS[id];
		return UNKNOWN;
	}

	public Direction getOpposite()
	{
		return getOrientation(OPPOSITES[ordinal()]);
	}

	public Direction getRotation(Direction axis)
	{
		return getOrientation(ROTATION_MATRIX[axis.ordinal()][ordinal()]);
	}

	public static Direction loadFromTag(DataTag tag)
	{
		return getOrientation(tag.getInteger("Ordinal", 0));
	}

	public static void saveToTag(DataTag tag, Direction direction)
	{
		tag.setInteger("Ordinal", direction.ordinal());
	}

	public static final Direction[] VALID_DIRECTIONS = { DOWN, UP, NORTH, SOUTH, WEST, EAST };
	public static final int[] OPPOSITES = { 1, 0, 3, 2, 5, 4, 6 };
	public static final int[][] ROTATION_MATRIX = { { 0, 1, 4, 5, 3, 2, 6 }, { 0, 1, 5, 4, 2, 3, 6 }, { 5, 4, 2, 3, 0, 1, 6 }, { 4, 5, 2, 3, 1, 0, 6 }, { 2, 3, 1, 0, 4, 5, 6 }, { 3, 2, 0, 1, 4, 5, 6 }, { 0, 1, 2, 3, 4, 5, 6 }, };
}