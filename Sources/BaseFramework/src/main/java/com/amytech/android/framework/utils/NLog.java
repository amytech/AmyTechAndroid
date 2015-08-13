package com.amytech.android.framework.utils;

import java.util.Collection;
import java.util.Map;

import android.util.Log;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月6日 上午11:40:21 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月6日 <br>
 *
 * @author marktlzhai
 */
public class NLog {
	public static int i(Object obj) {
		return i(NLog.class, obj.toString());
	}

	public static int i(Class<?> clazz, Object obj) {
		return i(clazz.getSimpleName(), obj);
	}

	public static int i(String tag, Object obj) {
		return Log.i(tag, obj.toString());
	}

	public static int d(Object obj) {
		return d(NLog.class, obj.toString());
	}

	public static int d(Class<?> clazz, Object obj) {
		return d(clazz.getSimpleName(), obj);
	}

	public static int d(String tag, Object obj) {
		return Log.d(tag, obj.toString());
	}

	public static int w(Object obj) {
		return w(NLog.class, obj.toString());
	}

	public static int w(Class<?> clazz, Object obj) {
		return w(clazz.getSimpleName(), obj);
	}

	public static int w(String tag, Object obj) {
		return Log.w(tag, obj.toString());
	}

	public static int e(Object obj) {
		return Log.e(NLog.class.getSimpleName(), obj.toString());
	}

	public static int e(Object obj, Throwable t) {
		return e(NLog.class, obj.toString(), t);
	}

	public static int e(String tag, Object obj) {
		return e(NLog.class, obj.toString(), null);
	}

	public static int e(String message, Throwable t) {
		return e(NLog.class, message, t);
	}

	public static int e(Class<?> clazz, Object obj, Throwable t) {
		return e(clazz.getSimpleName(), obj, t);
	}

	public static int e(String tag, Object obj, Throwable t) {
		if (t == null) {
			return Log.e(tag, obj.toString());
		} else {
			return Log.e(tag, obj.toString(), t);

		}
	}

	public static void printCollection(Collection<?> collection) {
		printCollection(NLog.class, collection);
	}

	public static void printCollection(String tag, Collection<?> collection) {
		Log.d(tag, "====================Collection Begin=====================");
		for (Object obj : collection) {
			Log.d(tag, obj.toString());
		}
		Log.d(tag, "====================Collection End=====================");
	}

	public static void printCollection(Class<?> clazz, Collection<?> collection) {
		printCollection(clazz.getSimpleName(), collection);
	}

	public static void printCollection(Object[] collection) {
		printCollection(NLog.class, collection);
	}

	public static void printCollection(Class<?> clazz, Object[] collection) {
		Log.d(clazz.getSimpleName(), "====================Collection Begin=====================");
		for (Object obj : collection) {
			Log.d(clazz.getSimpleName(), obj.toString());
		}
		Log.d(clazz.getSimpleName(), "====================Collection End=====================");
	}

	public static void printMap(Map<?, ?> map) {
		printMap(NLog.class, map);
	}

	public static void printMap(Class<?> clazz, Map<?, ?> map) {
		Log.d(clazz.getSimpleName(), "====================Map Begin=====================");
		for (Object obj : map.keySet()) {
			Log.d(clazz.getSimpleName(), obj.toString() + " = " + map.get(obj));
		}
		Log.d(clazz.getSimpleName(), "====================Map End=====================");
	}
}
