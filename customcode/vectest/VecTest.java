package customcode.vectest;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class VecTest extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static TestFrame main;
	public static boolean started;
	public static ArrayList<EntityVT> held = new ArrayList<EntityVT>();

	public VecTest()
	{
		started = true;
		add(main = new TestFrame());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setLocationRelativeTo(null);
		setTitle("Vec Test");
		setResizable(false);
		setVisible(true);
	}
}