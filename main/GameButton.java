package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComponent;
import main.utils.helper.Sound;

public class GameButton extends JComponent implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private final Dimension size;
	public Dimension dot, arc;
	private final ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private final String name;
	private boolean mouseEntered = false;
	private Color back = Save.PLAYER_COLOR.brighter();
	private String actionCommand;

	public GameButton(String name, ActionListener... actionListeners)
	{
		super();
		size = new Dimension(20 + name.length() * 10, 36);
		dot = new Dimension(size.width / 3, size.height / 3);
		arc = new Dimension((int) Math.sqrt(size.width), (int) Math.sqrt(size.height));
		enableInputMethods(true);
		addMouseListener(this);
		setSize(size.width, size.height);
		setFocusable(true);
		this.name = name;
		listeners.addAll(Arrays.asList(actionListeners));
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D antiAlias = (Graphics2D) g;
		antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(back);
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc.width, arc.height);
		g.setColor(mouseEntered ? Save.PLAYER_COLOR.brighter().brighter() : Save.PLAYER_COLOR.darker().darker());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc.width, arc.height);
		g.setColor(Color.BLACK);
		g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc.width, arc.height);

		int center = getWidth() * 2 / 3 - dot.width / 2 - dot.height * 2 / 3 * 1 / 2;
		int middle = getHeight() * 2 / 3 - dot.height / 2 - dot.height * 2 / 3 * 1 / 2;

		g.setColor(Color.BLACK);
		g.drawString(name, center + 5 - g.getFontMetrics().stringWidth(name) / 2, middle + g.getFontMetrics().getHeight() / 2);
	}

	public String getText()
	{
		return name;
	}

	public void doClick()
	{
		mousePressed(null);
		mouseReleased(null);
	}

	public void setActionCommand(String actionCommand)
	{
		this.actionCommand = actionCommand;
	}

	public String getActionCommand()
	{
		return actionCommand;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		mouseEntered = true;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		mouseEntered = false;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		notifyListeners(e);
		repaint();
		back = Save.PLAYER_COLOR.darker();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		back = Save.PLAYER_COLOR.brighter();
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
		Sound.playSound(Sound.BUTTON_CLICK, false, 0.0F);
	}

	public void addActionListener(ActionListener listener)
	{
		listeners.add(listener);
	}

	private void notifyListeners(MouseEvent e)
	{
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand, e.getWhen(), e.getModifiers());
		synchronized (listeners)
		{
			for (int i = 0; i < listeners.size(); i++)
			{
				listeners.get(i).actionPerformed(evt);
			}
		}
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(), getHeight());
	}

	@Override
	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize()
	{
		return getPreferredSize();
	}
}
