package main.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import main.Vars;
import modhandler.InfoFile;

public class FVisitor implements FileVisitor<Path>
{
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
	{
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
	{
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
	{
		if (file.toString().endsWith(".zip") || file.toString().endsWith(".jar"))
		{
			File dir = new File(Vars.extension + "natives/" + file.getFileName().toString().replaceAll(".zip", "").replaceAll(".jar", ""));

			if (file.toString().endsWith(".zip"))
			{
				JavaFileHelper.unzip(file.toFile(), dir);
			}
			else if (file.toString().endsWith(".jar"))
			{
				JavaFileHelper.unjar(file.toFile(), dir);
			}

			try
			{
				File[] files = FileHelper.getFilesInFolder(dir);

				for (int i = 0; i < files.length; i++)
				{
					if (files[i].toString().endsWith("_info.doc"))
					{
						new InfoFile(files[i]);
					}
				}

				JavaFileHelper.getClassesFromFolders(dir, dir);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		else if (!file.toString().contains(".DS_Store"))
		{
			System.err.println("Random File: -" + file + "-");
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
	{
		return FileVisitResult.CONTINUE;
	}
}
