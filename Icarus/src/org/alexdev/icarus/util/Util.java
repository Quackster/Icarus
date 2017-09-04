package org.alexdev.icarus.util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Map;

import org.alexdev.icarus.Icarus;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import com.google.common.collect.Maps;

public class Util {

	private static Wini configuration;
	private static SecureRandom secureRandom;
	private static Wini habboConfig;
	private static DecimalFormat decimalFormatter;
	private static String language;
	private static Wini locale;

	private static Map<String, String> composerPaths;

	public static void load() throws InvalidFileFormatException, IOException {
		configuration = new Wini(new File("icarus.properties"));
		habboConfig =  new Wini(new File("habbohotel.properties"));
		locale =  new Wini(new File("locale.ini"));
		secureRandom = new SecureRandom();
		decimalFormatter = new DecimalFormat("#.#"); // round to 1 decimal place
		language = locale.get("Locale", "language", String.class);
		composerPaths = Maps.newHashMap();
	}

	public static void createComposerLookup() throws Exception {

		for (String packages : Icarus.getServer().getMessageHandler().getComposerPackages()) {
			for (Class<?> clazz : ClassFinder.getClasses(packages)) {
				composerPaths.put(clazz.getSimpleName(), clazz.getName());
			}
		}
	}

	public static String getComposer(String className) {

		if (composerPaths.containsKey(className)) {
			return composerPaths.get(className);
		}

		return null;
	}

	public static int getCurrentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static String getLocale(String entry) {
		return locale.get(language, entry, String.class);
	}

	public boolean isNullOrEmpty(String param) { 
		return param == null || param.trim().length() == 0;
	}

	public static String filterInput(String input) {
		input = input.replace((char)10, ' ');
		input = input.replace((char)11, ' ');
		input = input.replace((char)12, ' ');
		input = input.replace((char)13, ' ');
		input = input.replace((char)14, ' ');

		return input;
	}

	public static boolean isNumber(Object object) {

		try {
			Integer.valueOf(object.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Wini getConfiguration() {
		return configuration;
	}

	public static int randomInt(int from, int to) {
		return randomInt(from, to, false);
	}

	public static int randomInt(int from, int to, boolean inclusive) {

		if (inclusive) {
			return from + secureRandom.nextInt(to + 1);	
		} else {
			return from + secureRandom.nextInt(to);
		}
	}

	public static SecureRandom getRandom() {
		return secureRandom;
	}

	public static Wini getGameConfig() {
		return habboConfig;
	}

	public static DecimalFormat getDecimalFormatter() {
		return decimalFormatter;
	}

}
