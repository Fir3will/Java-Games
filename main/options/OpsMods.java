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
import main.Vars;
import modhandler.Importer;
import modhandler.SimpleMod;

public class OpsMods extends OptionBar
{
	private class SharedListSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();

			for (int i = minIndex; i <= maxIndex; i++)
			{
				if (lsm.isSelectedIndex(i))
				{
					mod = Importer.mods.toArray(new SimpleMod[0])[i];
					output.setText("Modid: " + mod.getModid() + "\nVersion: " + mod.getVersion() + "\nDisabled: " + mod.isDisabled());
				}
			}
			output.setCaretPosition(output.getDocument().getLength());
		}
	}

	public JTextArea output;
	public JList<SimpleMod> list;
	public JTable table;
	public String newline = "\n";
	public ListSelectionModel listSelectionModel;
	public JPanel panel;
	public SimpleMod mod;

	@Override
	public void initBar()
	{
		panel = new JPanel(new BorderLayout());
		list = new JList<SimpleMod>(Importer.mods.toArray(new SimpleMod[0]));

		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
		JScrollPane listPane = new JScrollPane(list);

		JPanel controlPane = new JPanel();

		output = new JTextArea(1, 10);
		output.setText("Modid: \nVersion: \nDisabled: ");
		output.setEditable(false);
		JScrollPane outputPane = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JButton disable = new JButton("Disable Mod");
		disable.setEnabled(false);
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

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		panel.add(splitPane, BorderLayout.CENTER);
		panel.add(disable, BorderLayout.SOUTH);

		JPanel topHalf = new JPanel();
		topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
		JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder("All Mods"));
		listContainer.add(listPane);

		topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		topHalf.add(listContainer);

		topHalf.setMinimumSize(new Dimension(100, 50));
		topHalf.setPreferredSize(new Dimension(100, 110));
		splitPane.add(topHalf);

		JPanel bottomHalf = new JPanel(new BorderLayout());
		bottomHalf.add(controlPane, BorderLayout.NORTH);
		bottomHalf.add(outputPane, BorderLayout.CENTER);
		bottomHalf.setPreferredSize(new Dimension(450, 135));
		splitPane.add(bottomHalf);

		JFrame frame = new JFrame("Mods");
		Vars.frames.add(frame);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void performEvent(ActionEvent e)
	{}
}