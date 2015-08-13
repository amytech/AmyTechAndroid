package com.amytech.android.framework.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月6日 上午11:44:15 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月6日 <br>
 *
 * @author marktlzhai
 */
public class CollectionUtils {
	public static <E> boolean isEmpty(Collection<E> c) {
		return c == null || c.size() == 0;

	}

	public static <E> boolean notEmpty(Collection<E> c) {
		return c != null && c.size() != 0;

	}

	public static <E> int size(Collection<E> c) {
		return c == null ? 0 : c.size();
	}

	public static List<? extends Object> toList(Set<? extends Object> sets) {
		List<Object> result = new ArrayList<Object>();

		if (isEmpty(sets)) {
			return result;
		}

		for (Object object : sets) {
			result.add(object);
		}

		return result;
	}
}
