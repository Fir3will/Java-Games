package spaceinvaders;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class LaunchSI extends JFrame
{
	private static final long serialVersionUID = 1L;

	public LaunchSI()
	{
		add(new StartSI());

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Shooter");
		setResizable(false);
		setVisible(true);
	}
}