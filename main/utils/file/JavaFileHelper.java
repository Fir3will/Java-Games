package main.utils.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import modhandler.Initialization.State;
import modhandler.Mod;
import modhandler.ModContainer;

/**
 * Helps with Java files. This includes classes, jars, and zips.
 * 
 * <p>
 * Contains only eight static methods so far...
 * 
 * <li>{@link #zip(File, File)}
 * <li>{@link #unzip(File, File)}
 * <li>{@link #jar(File, File)}
 * <li>{@link #unjar(File, File)}
 * <li>{@link #getJarEntriesIn(File)}
 * <li>{@link #getZipEntriesIn(File)}
 * <li>{@link #getClassesFromFolders(File, File)}
 * 
 * <p>
 * This helps with handling Jar, Zip, and "Class" files easily without the
 * hassle of dealing with it yourself
 * 
 * @see java.util.jar.JarEntry
 * @see java.util.jar.JarFile
 * @see java.util.jar.JarOutputStream
 * @see java.util.zip.ZipEntry
 * @see java.util.zip.ZipFile
 * @see java.util.zip.ZipOutputStream
 * @see java.lang.ClassLoader
 * @see java.net.URLClassLoader
 */
public class JavaFileHelper
{
	public static void copy(File inF, File outF) throws IOException
	{
		InputStream in = new FileInputStream(inF);
		OutputStream out = new FileOutputStream(outF);
		try
		{
			copy(in, out);
		}
		finally
		{
			in.close();
			out.close();
		}
	}

	public static void copy(File file, OutputStream out) throws IOException
	{
		final InputStream in = new FileInputStream(file);
		try
		{
			copy(in, out);
		}
		finally
		{
			in.close();
		}
	}

	public static void copy(InputStream in, File file) throws IOException
	{
		final OutputStream out = new FileOutputStream(file);
		try
		{
			copy(in, out);
		}
		finally
		{
			out.close();
		}
	}

	public static void copy(InputStream in, OutputStream out) throws IOException
	{
		final byte[] buffer = new byte[1024];
		while (true)
		{
			final int readCount = in.read(buffer);
			if (readCount < 0)
			{
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	/**
	 * Jars the given Jar file for you.
	 * 
	 * @param directory
	 *            Where the files are
	 * @param jarfile
	 *            Where to send to
	 */
	public static void jar(File directory, File jarfile)
	{
		try
		{
			final URI base = directory.toURI();
			final Deque<File> queue = new LinkedList<File>();
			queue.push(directory);
			final OutputStream out = new FileOutputStream(jarfile);
			Closeable res = null;
			final JarOutputStream zout = new JarOutputStream(out);
			res = zout;

			while (!queue.isEmpty())
			{
				directory = queue.pop();

				for (final File kid : directory.listFiles())
				{
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory())
					{
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new JarEntry(name));
					}
					else
					{
						zout.putNextEntry(new JarEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
			res.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Unjars the given Jar file for you.
	 * 
	 * @param jarFile
	 *            Where the jar is
	 * @param directory
	 *            Where to send to
	 */
	public static void unjar(File jarFile, File directory)
	{
		JarFile jfile;
		try
		{
			jfile = new JarFile(jarFile);

			final Enumeration<? extends JarEntry> entries = jfile.entries();

			while (entries.hasMoreElements())
			{
				final JarEntry entry = entries.nextElement();
				final File file = new File(directory, entry.getName());
				if (entry.isDirectory())
				{
					file.mkdirs();
				}
				else
				{
					file.getParentFile().mkdirs();
					final InputStream in = jfile.getInputStream(entry);
					try
					{
						copy(in, file);
					}
					finally
					{
						in.close();
					}
				}
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Unzips the given Zip file for you.
	 * 
	 * @param zipFile
	 *            Where the files are
	 * @param directory
	 *            Where to send to
	 */
	public static void unzip(File zipfile, File directory)
	{
		ZipFile zfile;
		try
		{
			zfile = new ZipFile(zipfile);
			final Enumeration<? extends ZipEntry> entries = zfile.entries();
			while (entries.hasMoreElements())
			{
				final ZipEntry entry = entries.nextElement();
				final File file = new File(directory, entry.getName());
				if (entry.isDirectory())
				{
					file.mkdirs();
				}
				else
				{
					file.getParentFile().mkdirs();
					final InputStream in = zfile.getInputStream(entry);
					try
					{
						copy(in, file);
					}
					finally
					{
						in.close();
					}
				}
			}
		}
		catch (final IOException e)
		{
			if (e.getMessage().equals("zip file is empty"))
			{
				System.err.println("ERROR: " + e.getMessage());
			}
			else
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Zips the given Zip file for you.
	 * 
	 * @param directory
	 *            Where the files are
	 * @param zipfile
	 *            Where to send to
	 */
	public static void zip(File directory, File zipfile)
	{
		try
		{
			final URI base = directory.toURI();
			final Deque<File> queue = new LinkedList<File>();
			queue.push(directory);
			final OutputStream out = new FileOutputStream(zipfile);
			Closeable res = null;
			final ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;

			while (!queue.isEmpty())
			{
				directory = queue.pop();

				for (final File kid : directory.listFiles())
				{
					String name = base.relativize(kid.toURI()).getPath();
					if (kid.isDirectory())
					{
						queue.push(kid);
						name = name.endsWith("/") ? name : name + "/";
						zout.putNextEntry(new ZipEntry(name));
					}
					else
					{
						zout.putNextEntry(new ZipEntry(name));
						copy(kid, zout);
						zout.closeEntry();
					}
				}
			}
			res.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the {@link JarEntry}s
	 * 
	 * @param jarFile
	 *            Where the JarFile is
	 */
	@SuppressWarnings("resource")
	public static JarEntry[] getJarEntriesIn(File jarFile)
	{
		final ArrayList<JarEntry> entries = new ArrayList<JarEntry>();

		if (!jarFile.toString().endsWith(".jar")) throw new IllegalArgumentException("File isn't a Jar! " + jarFile.toString());

		try
		{
			final JarFile jar = new JarFile(jarFile);
			final Enumeration<? extends JarEntry> jes = jar.entries();

			while (jes.hasMoreElements())
			{
				entries.add(jes.nextElement());
			}
		}
		catch (final IOException e)
		{
			System.err.println("Error Loading Jar Entries from " + jarFile.toString());
			e.printStackTrace();
		}

		return entries.toArray(new JarEntry[0]);
	}

	/**
	 * Retrieves the {@link ZipEntry}s
	 * 
	 * @param zipFile
	 *            Where the ZipFile is
	 */
	@SuppressWarnings("resource")
	public static ZipEntry[] getZipEntriesIn(File zipFile)
	{
		final ArrayList<ZipEntry> entries = new ArrayList<ZipEntry>();

		if (!zipFile.toString().endsWith(".zip")) throw new IllegalArgumentException("File isn't a Zip! " + zipFile.toString());

		try
		{
			final ZipFile zip = new ZipFile(zipFile);
			final Enumeration<? extends ZipEntry> zes = zip.entries();

			while (zes.hasMoreElements())
			{
				entries.add(zes.nextElement());
			}
		}
		catch (final IOException e)
		{
			System.err.println("Error Loading Zip Entries from " + zipFile.toString());
			e.printStackTrace();
		}

		return entries.toArray(new ZipEntry[0]);
	}

	/**
	 * Searches through the given File (folder) for any class files to return.
	 * 
	 * @param dir
	 *            the current parent file too really...
	 * @param parentFile
	 *            The parent file where the package starts
	 * 
	 * @return An array list of classes that were found in the parent folder.
	 *         Includes all the folders in this folder too!
	 * @throws ClassNotFoundException 
	 * @throws MalformedURLException
	 */
	public static ArrayList<Class<?>> getClassesFromFolders(File dir, File parentFile) throws ClassNotFoundException, MalformedURLException
	{
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		if (dir.isDirectory())
		{
			for (int i = 0; i < dir.listFiles().length; i++)
			{
				if (dir.listFiles()[i].isDirectory())
				{
					classes.addAll(getClassesFromFolders(dir.listFiles()[i], parentFile));
				}
				else if (dir.listFiles()[i].toString().endsWith(".class"))
				{
					final URLClassLoader cl = URLClassLoader.newInstance(new URL[] { parentFile.toURI().toURL() });
					final Class<?> clazz = cl.loadClass(dir.listFiles()[i].toString().split(parentFile.getName() + "/")[1].replaceAll(".class", "").replaceAll("/", "."));

					classes.add(clazz);
				}
			}
		}
		return classes;
	}

	public static void getModsFrom(File dir, File parentFile) throws Exception
	{
		if (dir.isDirectory())
		{
			for (int i = 0; i < dir.listFiles().length; i++)
			{
				if (dir.listFiles()[i].isDirectory())
				{
					getModsFrom(dir.listFiles()[i], parentFile);
				}
				else if (dir.listFiles()[i].toString().endsWith(".class"))
				{
					final URLClassLoader cl = URLClassLoader.newInstance(new URL[] { parentFile.toURI().toURL() });
					final Class<?> clazz = cl.loadClass(dir.listFiles()[i].toString().split(parentFile.getName() + "/")[1].replaceAll(".class", "").replaceAll("/", "."));

					if (clazz.isAnnotationPresent(Mod.class))
					{
						final Object mod = clazz.newInstance();
						final ModContainer mc = new ModContainer(mod);
						mc.initMethod(State.PRE_INIT);
					}
				}
			}
		}
	}

	/**
	 * Gets the Calling Class
	 * 
	 * @return The Class that called this Method
	 */
	public static Class<?> classThatCalled()
	{
		return classThatCalledAt(2);
	}

	/**
	 * Gets the Class <code>levels</code> before this was called
	 * 
	 * @return The Class that called at the level
	 */
	public static Class<?> classThatCalledAt(int level)
	{
		try
		{
			return Class.forName(new RuntimeException().getStackTrace()[level].getClassName());
		}
		catch (final ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the Method Name <code>levels</code> before this was called
	 * 
	 * @return The Class that called at the level
	 */
	public static String methodNameThatCalled()
	{
		return methodNameThatCalledAt(2);
	}

	/**
	 * Gets the Calling Method Name
	 * 
	 * @return The Method Name that called this Method
	 */
	public static String methodNameThatCalledAt(int level)
	{
		return new RuntimeException().getStackTrace()[level].getMethodName();
	}

	/**
	 * Retrieve the file at the website and copies it to the given file
	 * 
	 * @param website
	 *            the website where the file is
	 * @param saveTo
	 *            where the downloaded file will be held!
	 */
	public static void saveOnlineFile(String website, File saveTo)
	{
		try
		{
			final InputStream in = new URL(website).openConnection().getInputStream();
			final FileOutputStream fos = new FileOutputStream(saveTo);
			final byte[] buf = new byte[512];
			while (true)
			{
				final int len = in.read(buf);

				if (len == -1)
				{
					break;
				}
				fos.write(buf, 0, len);
			}
			in.close();
			fos.close();
		}
		catch (final IOException e)
		{
			System.err.println("Error Saving Online File!");
			System.err.println("File: " + saveTo.toString());
			System.err.println("Website: " + website);
			e.printStackTrace();
		}
	}
}