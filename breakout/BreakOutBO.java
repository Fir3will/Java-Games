package breakout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class BreakOutBO extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreakOutBO()
	{
		add(new BoardBO());
		setTitle("Breakout");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(CommonsBO.WIDTH, CommonsBO.HEIGTH);
		setLocationRelativeTo(null);
		setIgnoreRepaint(true);
		setResizable(false);
		setVisible(true);
	}
}