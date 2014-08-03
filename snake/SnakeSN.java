package snake;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SnakeSN extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SnakeSN()
	{

		add(new BoardSN());

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(320, 340);
		setLocationRelativeTo(null);
		setTitle("Snake");

		setResizable(false);
		setVisible(true);
	}
}