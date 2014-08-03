package popouspelican;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PopousPelicanPP extends JFrame
{
	private static final long serialVersionUID = 1L;

	public PopousPelicanPP()
	{
		add(new StartPP());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Popous Pelican");
		setResizable(false);
		setVisible(true);
	}
}