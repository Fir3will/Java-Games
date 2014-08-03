package puzzle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import main.Vars;

public class PuzzleP extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton button;
	private JPanel centerPanel;
	private Image image;
	private JLabel label;
	int[][] pos;
	private Image source;
	int width, height;

	public PuzzleP()
	{

		pos = new int[][] { {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {9, 10, 11}};

		centerPanel = new JPanel();
		centerPanel.setBackground(Vars.playerColor);
		centerPanel.setLayout(new GridLayout(4, 4, 0, 0));

		ImageIcon sid = new ImageIcon(PuzzleP.class.getResource("/Images/Puzzle/iceSid.jpg"));
		source = sid.getImage();

		width = sid.getIconWidth();
		height = sid.getIconHeight();

		add(Box.createRigidArea(new Dimension(0, 5)), BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (j == 2 && i == 3)
				{
					label = new JLabel("");
					centerPanel.add(label);
				}
				else
				{
					button = new JButton();
					button.addActionListener(this);
					centerPanel.add(button);
					image = createImage(new FilteredImageSource(source.getSource(), new CropImageFilter(j * width / 3, i * height / 4, width / 3 + 1, height / 4)));
					button.setIcon(new ImageIcon(image));
				}
			}
		}

		setSize(325, 275);
		setTitle("Puzzle");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		Dimension size = button.getSize();

		int labelX = label.getX();
		int labelY = label.getY();
		int buttonX = button.getX();
		int buttonY = button.getY();
		int buttonPosX = buttonX / size.width;
		int buttonPosY = buttonY / size.height;
		int buttonIndex = pos[buttonPosY][buttonPosX];

		if (labelX == buttonX && labelY - buttonY == size.height)
		{

			int labelIndex = buttonIndex + 3;

			centerPanel.remove(buttonIndex);
			centerPanel.add(label, buttonIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();
		}

		if (labelX == buttonX && labelY - buttonY == -size.height)
		{

			int labelIndex = buttonIndex - 3;
			centerPanel.remove(labelIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.add(label, buttonIndex);
			centerPanel.validate();
		}

		if (labelY == buttonY && labelX - buttonX == size.width)
		{

			int labelIndex = buttonIndex + 1;

			centerPanel.remove(buttonIndex);
			centerPanel.add(label, buttonIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();
		}

		if (labelY == buttonY && labelX - buttonX == -size.width)
		{

			int labelIndex = buttonIndex - 1;

			centerPanel.remove(buttonIndex);
			centerPanel.add(label, labelIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();
		}
	}
}