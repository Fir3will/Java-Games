package tanks;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TanksTN extends JFrame
{
	private static final long serialVersionUID = 1L;

	public TanksTN()
	{
		add(new StartTN());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setLocationRelativeTo(null);
		setTitle("Tanks");
		setResizable(false);
		setVisible(true);
	}
}