package com.skula.androfees.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.skula.androfees.models.GraphItem;

public class GraphItemFactory {

	public static void main(String[] args) {
		Calendar cal = CalendarUtils.getCalendarFrom(3, Calendar.DAY_OF_MONTH);

		List<GraphItem> list = new ArrayList<GraphItem>();
		list.add(new GraphItem(27, 10, 2012, 1.0));
		list.add(new GraphItem(28, 10, 2012, 2.0));
		list.add(new GraphItem(29, 10, 2012, 3.0));
		System.out.println(fillList(list, cal, Calendar.DAY_OF_MONTH));
	}

	public static List<GraphItem> fillList(List<GraphItem> list, String calFrom, int timeUnit) {
		return fillList(list, CalendarUtils.string2Calendar(calFrom), timeUnit);
	}

	public static List<GraphItem> fillList(List<GraphItem> list, Calendar calFrom, int timeUnit) {
		List<GraphItem> res = new ArrayList<GraphItem>();
		Calendar calTo = Calendar.getInstance();
		if (!calFrom.before(calTo)) {
			return null;
		}

		while (!calFrom.after(calTo)) {

			double val = 0.0;
			for (GraphItem gi : list) {
				if (gi.equals(calFrom)) {
					val = gi.getValue();
				}
			}
			res.add(new GraphItem(calFrom, val));
			calFrom.add(timeUnit, 1);
		}
		return res;
	}

	private static boolean calEqual(Calendar from, Calendar to, int timeUnit) {
		switch (timeUnit) {
		case Calendar.DAY_OF_MONTH:
			return from.get(Calendar.DAY_OF_MONTH) == to.get(Calendar.DAY_OF_MONTH) && from.get(Calendar.MONTH) == to.get(Calendar.MONTH)
					&& from.get(Calendar.YEAR) == to.get(Calendar.YEAR);
		case Calendar.MONTH:
			return from.get(Calendar.MONTH) == to.get(Calendar.MONTH) && from.get(Calendar.YEAR) == to.get(Calendar.YEAR);
		case Calendar.YEAR:
			return from.get(Calendar.YEAR) == to.get(Calendar.YEAR);
		default:
			return false;
		}
	}
}
