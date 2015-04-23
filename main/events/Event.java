package main.events;

public abstract class Event
{
	private Result result;

	protected Event()
	{}

	public void setResult(Result result)
	{
		this.result = result;
	}

	public boolean isApproved()
	{
		return result == Result.APPROVED;
	}

	public static enum Result
	{
		APPROVED,
		CANCELED;
	}
}
