package main.utils.helper;

import java.util.ArrayList;

public class Coder
{
	public enum Letter
	{
		RBRACKETS(0),
		LBRACKETS(0),
		RBRACES(0),
		LBRACES(0),
		SEMICOLON(0),
		EQUALS(0),
		NEWLINE(0),
		UNDERDASH(0),
		HYPHEN(0),
		RPARENTHESIS(0),
		LPARENTHESIS(0),
		BACKSLASH(0),
		APOSTROPHE(0),
		QUESTIONMARK(0),
		EXCLAMATIONPOINT(0),
		PERIOD(0),
		COMMA(0),
		COLON(0),
		SPACE(0),
		UNDEFINED(0),
		A(1),
		B(2),
		C(3),
		D(4),
		E(5),
		F(6),
		G(7),
		H(8),
		I(9),
		J(10),
		K(11),
		L(12),
		M(13),
		N(-13),
		O(-12),
		P(-11),
		Q(-10),
		R(-9),
		S(-8),
		T(-7),
		U(-6),
		V(-5),
		W(-4),
		X(-3),
		Y(-2),
		Z(-1),
		CpA(A.getValue() + 15),
		CpB(B.getValue() + 15),
		CpC(C.getValue() + 15),
		CpD(D.getValue() + 15),
		CpE(E.getValue() + 15),
		CpF(F.getValue() + 15),
		CpG(G.getValue() + 15),
		CpH(H.getValue() + 15),
		CpI(I.getValue() + 15),
		CpJ(J.getValue() + 15),
		CpK(K.getValue() + 15),
		CpL(L.getValue() + 15),
		CpM(M.getValue() + 15),
		CpN(N.getValue() - 15),
		CpO(O.getValue() - 15),
		CpP(P.getValue() - 15),
		CpQ(Q.getValue() - 15),
		CpR(R.getValue() - 15),
		CpS(S.getValue() - 15),
		CpT(T.getValue() - 15),
		CpU(U.getValue() - 15),
		CpV(V.getValue() - 15),
		CpW(W.getValue() - 15),
		CpX(X.getValue() - 15),
		CpY(Y.getValue() - 15),
		CpZ(Z.getValue() - 15),
		ONE(100),
		TWO(-100),
		THREE(200),
		FOUR(-200),
		FIVE(300),
		SIX(-300),
		SEVEN(400),
		EIGHT(-400),
		NINE(500),
		ZERO(-500);

		public static Letter getLetterValue(String string)
		{
			Letter letter = UNDEFINED;

			for (int i = 0; i < Letter.values().length; i++)
				if (Letter.values()[i].getStringValue().equals(string))
				{
					letter = Letter.values()[i];
				}

			return letter;
		}

		private final int value;

		private Letter(int value)
		{
			this.value = value;
		}

		public Letter getOpposite()
		{
			Letter letter = null;

			if (value == 0) return this;
			for (int i = 0; i < Letter.values().length; i++)
				if (Letter.values()[i].getValue() == -getValue())
				{
					letter = Letter.values()[i];
				}

			return letter;
		}

		public String getStringValue()
		{
			if (this == UNDEFINED) return "";
			else if (this == ONE) return "1";
			else if (this == TWO) return "2";
			else if (this == THREE) return "3";
			else if (this == FOUR) return "4";
			else if (this == FIVE) return "5";
			else if (this == SIX) return "6";
			else if (this == SEVEN) return "7";
			else if (this == EIGHT) return "8";
			else if (this == NINE) return "9";
			else if (this == ZERO) return "0";
			else if (this == SPACE) return " ";
			else if (this == APOSTROPHE) return "'";
			else if (this == COMMA) return ",";
			else if (this == COLON) return ":";
			else if (this == PERIOD) return ".";
			else if (this == EXCLAMATIONPOINT) return "!";
			else if (this == QUESTIONMARK) return "?";
			else if (this == BACKSLASH) return "/";
			else if (this == LPARENTHESIS) return "(";
			else if (this == RPARENTHESIS) return ")";
			else if (this == UNDERDASH) return "_";
			else if (this == HYPHEN) return "-";
			else if (this == NEWLINE) return "\n";
			else if (this == EQUALS) return "=";
			else if (this == SEMICOLON) return ";";
			else if (this == RBRACES) return "}";
			else if (this == LBRACES) return "{";
			else if (this == RBRACKETS) return "]";
			else if (this == LBRACKETS) return "[";
			else if (name().startsWith("Cp")) return name().substring(2);
			else return name().toLowerCase();
		}

		public int getValue()
		{
			return value;
		}
	}

	public static String decode(String string)
	{
		return encode(string);
	}

	public static String encode(String string)
	{
		String newString = "";

		final ArrayList<Letter> letters = new ArrayList<Letter>();

		for (int i = 0; i < string.length(); i++)
		{
			final char letter = string.charAt(i);
			final Letter l = Letter.getLetterValue(letter + "").getOpposite();

			letters.add(l);
		}

		for (int i = 0; i < letters.size(); i++)
		{
			newString += letters.get(i).getStringValue();
		}

		return newString;
	}

	public static boolean isCapital(char letter)
	{
		return isCapital(letter + "");
	}

	public static boolean isCapital(String let)
	{
		return !let.equals(let.toLowerCase()) && let.equals(let.toUpperCase());
	}
}
