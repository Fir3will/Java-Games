package main.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modhandler.Loader;
import modhandler.ModContainer;

public class OpsMods extends OptionBar
{
	private class SharedListSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			final ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			final int minIndex = lsm.getMinSelectionIndex();
			final int maxIndex = lsm.getMaxSelectionIndex();

			for (int i = minIndex; i <= maxIndex; i++)
				if (lsm.isSelectedIndex(i))
				{
					mod = Loader.getContainerList().get(i);
					output.setText("Modid: " + mod.getName() + "\nVersion: " + mod.getVersion() + "\nDisabled: " + mod.isDisabled());
				}
			output.setCaretPosition(output.getDocument().getLength());
		}
	}

	public JTextArea output;
	public JList<ModContainer> list;
	public JTable table;
	public String newline = "\n";
	public ListSelectionModel listSelectionModel;
	public JPanel panel;
	public ModContainer mod;
	public JButton disable;

	@Override
	public void initBar()
	{
		panel = new JPanel(new BorderLayout());
		list = new JList<ModContainer>(Loader.getContainerList().toArray(new ModContainer[0]));

		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		final JScrollPane listPane = new JScrollPane(list);

		final JPanel controlPane = new JPanel();

		output = new JTextArea(1, 10);
		output.setText("Modid: \nVersion: \nDisabled: ");
		output.setEditable(false);
		final JScrollPane outputPane = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		disable = new JButton("Disable Mod");
		disable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (mod != null)
				{
					mod.switchModes();
				}
			}
		});

		final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane, BorderLayout.CENTER);
		panel.add(disable, BorderLayout.SOUTH);

		final JPanel topHalf = new JPanel();
		topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
		final JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder("All Mods"));
		listContainer.add(listPane);

		topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		topHalf.add(listContainer);

		topHalf.setMinimumSize(new Dimension(100, 50));
		topHalf.setPreferredSize(new Dimension(100, 110));
		splitPane.add(topHalf);

		final JPanel bottomHalf = new JPanel(new BorderLayout());
		bottomHalf.add(controlPane, BorderLayout.NORTH);
		bottomHalf.add(outputPane, BorderLayout.CENTER);
		bottomHalf.setPreferredSize(new Dimension(450, 135));
		splitPane.add(bottomHalf);

		final JFrame frame = new JFrame("Mods");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void performEvent(ActionEvent e)
	{
		if (mod != null)
		{
			disable.setText((mod.isDisabled() ? "Enable" : "Disable") + " Mod");
		}
	}
}