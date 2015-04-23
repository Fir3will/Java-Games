package main.events;

public class EventException extends Exception
{
	private static final long serialVersionUID = 1L;

	public EventException()
	{
		super();
	}

	public EventException(String message)
	{
		super(message);
	}

	public EventException(Throwable cause)
	{
		super(cause);
	}

	public EventException(String message, Throwable e)
	{
		super(message, e);
	}
}
