package main.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import main.Vars;

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
			final File dir = new File(Vars.HOME_DIR + "natives/" + file.getFileName().toString().replaceAll(".zip", "").replaceAll(".jar", ""));

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
				JavaFileHelper.getModsFrom(dir, dir);
			}
			catch (final Exception e1)
			{
				e1.printStackTrace();
			}
		}
		else if (!file.toFile().isHidden())
		{
			System.err.println("Random File: \"" + file + "\"");
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
	{
		return FileVisitResult.CONTINUE;
	}
}
