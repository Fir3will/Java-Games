package main.utils.helper;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger extends PrintStream
{
	private final DateFormat dateFormat = new SimpleDateFormat();
	private Date cachedDate = new Date();
	private final SimpleTimer refreshTimer = new SimpleTimer();

	public Logger(PrintStream out)
	{
		super(out);
	}

	private String getPrefix()
	{
		if (refreshTimer.elapsed() > 1000)
		{
			refreshTimer.reset();
			cachedDate = new Date();
		}
		return dateFormat.format(cachedDate).replaceAll(" PM", ":").replaceAll(" AM", ":") + getSecs();
	}

	private String getSecs()
	{
		String secs = "" + Calendar.getInstance().get(Calendar.SECOND);

		if (secs.length() == 1)
		{
			secs = "0" + secs;
		}

		return secs;
	}

	@Override
	public void print(String str)
	{
		if (str.startsWith("::"))
		{
			super.print(str.substring(2));
		}
		else
		{
			super.print("[" + getPrefix() + "]: " + str);
		}
	}
}