package tanks;

public enum EnumFace
{
	E(90), N(360), NE(45), NW(315), S(180), SE(125), SW(225), W(270);

	private int degree;

	EnumFace(int degree)
	{
		this.degree = degree;
	}

	public int getDegree()
	{
		return degree;
	}
}
