package main.utils.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import modhandler.Mod;
import modhandler.ModContainer;
import modhandler.SimpleMod;

/**
 * Helps with Java files. This includes
 * classes, jars, and zips.
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
 * <li>{@link #getClassFromFile(String)}
 * 
 * <p>
 * This helps with handling Jar, Zip, and 
 * "Class" files easily without the hassle
 *  of dealing with it yourself 
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
	private static void copy(File file, OutputStream out) throws IOException
	{
		InputStream in = new FileInputStream(file);
		try
		{
			copy(in, out);
		}
		finally
		{
			in.close();
		}
	}

	private static void copy(InputStream in, File file) throws IOException
	{
		OutputStream out = new FileOutputStream(file);
		try
		{
			copy(in, out);
		}
		finally
		{
			out.close();
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		while (true)
		{
			int readCount = in.read(buffer);
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
	 * @param directory Where the files are
	 * @param jarfile Where to send to
	 */
	public static void jar(File directory, File jarfile)
	{
		try
		{
			URI base = directory.toURI();
			Deque<File> queue = new LinkedList<File>();
			queue.push(directory);
			OutputStream out = new FileOutputStream(jarfile);
			Closeable res = null;
			JarOutputStream zout = new JarOutputStream(out);
			res = zout;

			while (!queue.isEmpty())
			{
				directory = queue.pop();

				for (File kid : directory.listFiles())
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Unjars the given Jar file for you.
	 * 
	 * @param jarFile Where the jar is
	 * @param directory Where to send to
	 */
	public static void unjar(File jarFile, File directory)
	{
		JarFile jfile;
		try
		{
			jfile = new JarFile(jarFile);

			Enumeration<? extends JarEntry> entries = jfile.entries();

			while (entries.hasMoreElements())
			{
				JarEntry entry = entries.nextElement();
				File file = new File(directory, entry.getName());
				if (entry.isDirectory())
				{
					file.mkdirs();
				}
				else
				{
					file.getParentFile().mkdirs();
					InputStream in = jfile.getInputStream(entry);
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Unzips the given Zip file for you.
	 * 
	 * @param zipFile Where the files are
	 * @param directory Where to send to
	 */
	public static void unzip(File zipfile, File directory)
	{
		ZipFile zfile;
		try
		{
			zfile = new ZipFile(zipfile);
			Enumeration<? extends ZipEntry> entries = zfile.entries();
			while (entries.hasMoreElements())
			{
				ZipEntry entry = entries.nextElement();
				File file = new File(directory, entry.getName());
				if (entry.isDirectory())
				{
					file.mkdirs();
				}
				else
				{
					file.getParentFile().mkdirs();
					InputStream in = zfile.getInputStream(entry);
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
		catch (IOException e)
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
	 * @param directory Where the files are
	 * @param zipfile Where to send to
	 */
	public static void zip(File directory, File zipfile)
	{
		try
		{
			URI base = directory.toURI();
			Deque<File> queue = new LinkedList<File>();
			queue.push(directory);
			OutputStream out = new FileOutputStream(zipfile);
			Closeable res = null;
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;

			while (!queue.isEmpty())
			{
				directory = queue.pop();

				for (File kid : directory.listFiles())
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the {@link JarEntry}s
	 * 
	 * @param jarFile Where the JarFile is
	 */
	@SuppressWarnings("resource")
	public static JarEntry[] getJarEntriesIn(File jarFile)
	{
		ArrayList<JarEntry> entries = new ArrayList<JarEntry>();

		if (!jarFile.toString().endsWith(".jar")) throw new IllegalArgumentException("File isn't a Jar! " + jarFile.toString());

		try
		{
			JarFile jar = new JarFile(jarFile);
			Enumeration<? extends JarEntry> jes = jar.entries();

			while (jes.hasMoreElements())
			{
				entries.add(jes.nextElement());
			}
		}
		catch (IOException e)
		{
			System.err.println("Error Loading Jar Entries from " + jarFile.toString());
			e.printStackTrace();
		}

		return entries.toArray(new JarEntry[0]);
	}

	/**
	 * Retrieves the {@link ZipEntry}s
	 * 
	 * @param zipFile Where the ZipFile is
	 */
	@SuppressWarnings("resource")
	public static ZipEntry[] getZipEntriesIn(File zipFile)
	{
		ArrayList<ZipEntry> entries = new ArrayList<ZipEntry>();

		if (!zipFile.toString().endsWith(".zip")) throw new IllegalArgumentException("File isn't a Zip! " + zipFile.toString());

		try
		{
			ZipFile zip = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> zes = zip.entries();

			while (zes.hasMoreElements())
			{
				entries.add(zes.nextElement());
			}
		}
		catch (IOException e)
		{
			System.err.println("Error Loading Zip Entries from " + zipFile.toString());
			e.printStackTrace();
		}

		return entries.toArray(new ZipEntry[0]);
	}

	/**
	 * Searches through the given File (folder) for
	 * any class files to return.
	 * 
	 * @param dir the current parent file too really...
	 * @param parentFile The parent fil where the package starts
	 * 
	 * @return An arraylist of classes that were found 
	 * 			in the parent folder. Includes all the 
	 * 			folders in this folder too!
	 * 
	 * @throws Exception If any (IO) exception occurs!
	 */
	public static ArrayList<Class<?>> getClassesFromFolders(File dir, File parentFile) throws Exception
	{
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

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
					URLClassLoader cl = URLClassLoader.newInstance(new URL[] {parentFile.toURI().toURL()});
					Class<?> clazz = cl.loadClass(dir.listFiles()[i].toString().split(parentFile.getName() + "/")[1].replaceAll(".class", "").replaceAll("/", "."));

					if (clazz.getSuperclass() == SimpleMod.class)
					{
						if (clazz.isAnnotationPresent(Mod.class))
						{
							classes.add(clazz);
							SimpleMod mod = (SimpleMod) clazz.newInstance();
							ModContainer mc = ModContainer.addMod(mod, clazz.getAnnotation(Mod.class).modid());
							mod.addContainer(mc);
							mod.preInit();
						}
						else
						{
							System.err.println("[ADDING MOD CANCELED] Mod: " + clazz.getName() + ", DOES NOT HAVE THE 'Mod.class' ANNOTATION!");
						}
					}
				}
			}
		}
		return classes;
	}

	/**
	 * Returns a Class from the given jarfile path
	 * 
	 * <p>
	 * <b><i>WARNING VERY IFFY!</i></b>
	 * 
	 * @param lol The JarFile path
	 * @return a class object of the class file...
	 */
	@SuppressWarnings("resource")
	public static Class<?> getClassFromFile(String lol)
	{
		System.out.println("LOL: " + lol);
		try
		{
			JarFile jarFile = new JarFile(lol);
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = {new URL("jar:file:" + lol + "!/")};
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			while (e.hasMoreElements())
			{
				JarEntry je = e.nextElement();
				System.out.println("File: " + je.getName());

				if (je.getName().endsWith(".class"))
				{
					String className = je.getName().substring(0, je.getName().length() - 6).replace('/', '.');
					Class<?> c = cl.loadClass(className);

					if (c.getSuperclass() != SimpleMod.class)
					{
						continue;
					}

					return c;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
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
		catch (ClassNotFoundException e)
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
	 * @param website the website where the file is
	 * @param saveTo where the downloaded file will be held!
	 */
	public static void saveOnlineFile(String website, File saveTo)
	{
		try
		{
			InputStream in = new URL(website).openConnection().getInputStream();
			FileOutputStream fos = new FileOutputStream(saveTo);
			byte[] buf = new byte[512];
			while (true)
			{
				int len = in.read(buf);

				if (len == -1)
				{
					break;
				}
				fos.write(buf, 0, len);
			}
			in.close();
			fos.close();
		}
		catch (IOException e)
		{
			System.err.println("Error Saving Online File!");
			System.err.println("File: " + saveTo.toString());
			System.err.println("Website: " + website);
			e.printStackTrace();
		}
	}
}