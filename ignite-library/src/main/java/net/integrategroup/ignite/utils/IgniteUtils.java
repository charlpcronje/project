package net.integrategroup.ignite.utils;

import java.io.Closeable;
import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.RuntimeMBeanException;
import javax.management.RuntimeOperationsException;

/**
 * @author Tony De Buys
 *
 */
public class IgniteUtils {

	// Return a string of the same length as the password, but made up entirely of *'s
	public static String obfuscatePassword(String password) {
		if (password != null) {
			String bar = "";
			for (int foo = 0; foo < password.length(); foo++) {
				bar += "*";
			}

			password = bar;
		}
		return password;
	}

	// Has the password been obfuscated?
	public static boolean isValidPassword(String password) {
		boolean result = false;

		if (password != null) {
			password = password.replace("*", "");
			if (!"".equals(password.trim())) {
				// This means that the password is not just a string of *'s
				result = true;
			}
		}
		return result;
	}

	// Close a stream without throwing an exception
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			// Shhh
		}
	}

	public static String getTerminatedPath(String path) {
		if (path == null) {
			return null;
		}

		if ((!path.endsWith("/")) && (!path.endsWith("\\"))) {
			path += "/";  						// Linux file path fix, we may have to add a parameter variable to get the correct path
												// terminator.  Windows vs Linux
		}

		return path;
	}

	public static String getFileExtension(String filename) {
		String ext = "";

		if (filename.indexOf(".") > -1) {
			ext = filename.substring(filename.lastIndexOf("."));
		}

		return ext;
	}

	public static Long endOfDay(Long dateMs) {
		return  dateMs + (24*60*60*1000) - 1; // 1 ms before midnight
	}

	// pad a number with leading zeros to the specified length
	// Ingrid added this on 25 Sep 2023
	public static String leadingZeroPad(Long num, int len) {

		String numAsString = "" + num;

		if (numAsString != null) {
			String bar = "";
			for (int foo = numAsString.length(); foo < len; foo++) {
				bar = "0" + numAsString;
			}

			numAsString = bar;
		}
		return numAsString;
	}

	// From: https://github.com/frohoff/jdk8u-jdk/blob/master/src/share/classes/sun/tools/jconsole/inspector/Utils.java
    public static Throwable getActualException(Throwable e) {
        if (e instanceof ExecutionException) {
            e = e.getCause();
        }
        if (e instanceof MBeanException ||
                e instanceof RuntimeMBeanException ||
                e instanceof RuntimeOperationsException ||
                e instanceof ReflectionException) {
            Throwable t = e.getCause();
            if (t != null) {
                return t;
            }
        }
        return e;
    }

	public static void deleteFile(String filename) {
		File f = new File(filename);
		
		if (f.exists()) {
			f.delete();
		}
	}

	public static Double toDouble(String value) {
		Double result = null;
		
		if ((value != null) && (!value.isEmpty())) {
			try {
				result = Double.parseDouble(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Date toDate(String value) {
		Date result = null;
		
		if ((value != null) && (!value.isEmpty())) {
			try {
				Long l = Long.parseLong(value);
				result = new Date(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}

