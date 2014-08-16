package main.utils;

public class InfoLocker
{
	private int intCounter, booleanCounter, objectCounter;
	private int[] intBytes = new int[500];
	private boolean[] booleanBytes = new boolean[500];
	private Object[] objectBytes = new Object[250];
	private String[] intByteLabel = new String[500];
	private String[] booleanByteLabel = new String[500];
	private String[] objectByteLabel = new String[250];

	public void addBooleanByte(String label, boolean arg)
	{
		booleanBytes[booleanCounter] = arg;
		booleanByteLabel[booleanCounter] = label;
		booleanCounter++;
	}

	public void addIntByte(String label, int arg)
	{
		intBytes[intCounter] = arg;
		intByteLabel[intCounter] = label;
		intCounter++;
	}

	public void addObjectByte(String label, Object arg)
	{
		objectBytes[objectCounter] = arg;
		objectByteLabel[objectCounter] = label;
		objectCounter++;
	}

	public boolean getBooleanByteI(int index)
	{
		return booleanBytes[index];
	}

	public boolean getBooleanByteL(String label)
	{
		for (int i = 0; i < booleanBytes.length; i++)
		{
			if (booleanByteLabel[i].equals(label)) return booleanBytes[i];
		}
		return false;
	}

	public boolean[] getBooleanBytes()
	{
		return booleanBytes;
	}

	public String getBooleanLabel(int index)
	{
		return booleanByteLabel[index];
	}

	public int getIntByteI(int index)
	{
		return intBytes[index];
	}

	public int getIntByteL(String label)
	{
		for (int i = 0; i < intBytes.length; i++)
		{
			if (intByteLabel[i].equals(label)) return intBytes[i];
		}
		return 0;
	}

	public int[] getIntBytes()
	{
		return intBytes;
	}

	public String getIntLabel(int index)
	{
		return intByteLabel[index];
	}

	public Object getObjectByteI(int index)
	{
		return objectBytes[index];
	}

	public Object getObjectByteL(String label)
	{
		for (int i = 0; i < objectBytes.length; i++)
		{
			if (objectByteLabel[i].equals(label)) return objectBytes[i];
		}
		return 0;
	}

	public Object[] getObjectBytes()
	{
		return objectBytes;
	}

	public String getObjectLabel(int index)
	{
		return objectByteLabel[index];
	}

	public void setBooleanByte(String label, int index, boolean arg)
	{
		booleanBytes[index] = arg;
		booleanByteLabel[index] = label;
	}

	public void setBooleanByteI(int index, boolean arg)
	{
		booleanBytes[index] = arg;
	}

	public void setBooleanByteL(String label, boolean arg)
	{
		for (int i = 0; i < booleanBytes.length; i++)
		{
			if (booleanByteLabel[i] != null)
			{
				if (booleanByteLabel[i].equals(label))
				{
					booleanBytes[i] = arg;
				}
			}
		}
	}

	public void setBooleanBytes(boolean[] booleanBytes)
	{
		this.booleanBytes = booleanBytes;
	}

	public void setIntByte(String label, int index, int arg)
	{
		intBytes[index] = arg;
		intByteLabel[index] = label;
	}

	public void setIntByteI(int index, int arg)
	{
		intBytes[index] = arg;
	}

	public void setIntByteL(String label, int arg)
	{
		for (int i = 0; i < intBytes.length; i++)
		{
			if (intByteLabel[i] != null)
			{
				if (intByteLabel[i].equals(label))
				{
					intBytes[i] = arg;
				}
			}
		}
	}

	public void setIntBytes(int[] intBytes)
	{
		this.intBytes = intBytes;
	}

	public void setObjectByte(String label, int index, Object arg)
	{
		objectBytes[index] = arg;
		objectByteLabel[index] = label;
	}

	public void setObjectByteI(int index, Object arg)
	{
		objectBytes[index] = arg;
	}

	public void setObjectByteL(String label, Object arg)
	{
		for (int i = 0; i < objectBytes.length; i++)
		{
			if (objectByteLabel[i] != null)
			{
				if (objectByteLabel[i].equals(label))
				{
					objectBytes[i] = arg;
				}
			}
		}
	}

	public void setObjectBytes(Object[] objectBytes)
	{
		this.objectBytes = objectBytes;
	}
}
