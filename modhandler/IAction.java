package modhandler;

import javax.swing.JButton;

public interface IAction
{
	public String getActionCommand();

	public JButton getButton();

	public void performAction();
}
