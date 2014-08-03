package modhandler;

import java.io.File;
import main.utils.file.FileHelper;

public class InfoFile
{
	private File file;
	private String version;
	private String modid;

	public InfoFile(File file)
	{
		this.file = file;
		setValues();

		for (int i = 0; i < Importer.modContainers.size(); i++)
		{
			if (getModid().equals(Importer.modContainers.get(i).getModid()))
			{
				Importer.modContainers.get(i).addModInfo(this);
			}
		}
	}

	public String getModid()
	{
		return modid;
	}

	public String getVersion()
	{
		return version;
	}

	private void setValues()
	{
		String[] contents = FileHelper.getFileContents(file.toPath().toString());

		for (int i = 0; i < contents.length; i++)
		{
			if (contents[i].startsWith("Version: "))
			{
				version = contents[i].replaceAll("Version: ", "");
			}
			if (contents[i].startsWith("Modid: "))
			{
				modid = contents[i].replaceAll("Modid: ", "");
			}
		}
	}
}
