package main.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OptionBar implements ActionListener
{
	public OptionBar()
	{
		initBar();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		performEvent(e);
	}

	public abstract void initBar();

	public abstract void performEvent(ActionEvent e);
}
