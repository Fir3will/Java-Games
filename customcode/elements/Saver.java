package customcode.elements;

import main.utils.ReaderTag;
import main.utils.SaveManager;
import main.utils.WriterTag;

public class Saver extends SaveManager
{
	@Override
	public void readFromTag(ReaderTag tag)
	{
		System.err.println("Loading...");
	}

	@Override
	public void writeToTag(WriterTag tag)
	{
		System.err.println("Saving...");
	}
}
