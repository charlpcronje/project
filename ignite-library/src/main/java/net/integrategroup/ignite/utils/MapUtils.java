package net.integrategroup.ignite.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Tony De Buys
 *
 */
public class MapUtils {

	public Object getAsObjectOrNull(Map<String, Object> data, String key) {
		Object result = null;

		if ((data != null) && data.containsKey(key)) {
			result = data.get(key);
		}

		return result;
	}

	public Long getAsLongOrNull(Map<String, Object> data, String key) {
		Long result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Integer) {
					// get as int and convert to long
					Integer i = (Integer) value;
					result = new Long(i);
				} else if (value instanceof String) {
					String s = (String) value;
					if (!s.isEmpty()) {
						result = new Long((String) value);
					}
				} else {
					// assume long...
					result = (Long) value;
				}
			}
		}

		return result;
	}

	public Double getAsDoubleOrNull(Map<String, Object> data, String key) {
		Double result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Double) {
					// get as int and convert to long
					Double d = (Double) value;
					result = new Double(d);
				} else if (value instanceof String) {
					String s = (String) value;
					if (!s.isEmpty()) {
						result = new Double((String) value);
					}
				} else {
					// assume long...
					result = (Double) value;
				}
			}
		}

		return result;
	}

	public String getAsStringOrNull(Map<String, Object> data, String key) {
		String result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Integer) {
					value = ((Integer) value).toString();
				}
				result = (String) value;
				result = result.trim();
			}
		}

		return result;
	}

	public String getAsUCaseStringOrNull(Map<String, Object> data, String key) {
		String result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Integer) {
					value = ((Integer) value).toString();
				}
				result = (String) value;
				result = result.toUpperCase().trim();
			}
		}

		return result;
	}

	public Integer getAsIntegerOrNull(Map<String, Object> data, String key) {
		Integer result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Integer) {
					// get as int and convert to long
					result = (Integer) value;
				} else if (value instanceof Short) {
					result = ((Short) value).intValue();
				} else if (value instanceof String) {
					String s = (String) value;
					if (!s.isEmpty()) {
						result = new Integer((String) value);
					}
				} else {
					// assume long...
					result = (Integer) value;
				}
			}
		}

		return result;
	}

	public Date getAsDateOrNull(Map<String, Object> data, String key) {
		Date result = null;

		if ((data != null) && data.containsKey(key)) {
			Object value = data.get(key);

			if (value != null) {
				if (value instanceof Long) {
					result = new Date((Long) value);
				}
				if (value instanceof Date) {
					result = (Date) value;
					// Note: We cannot assume that it's a string and attempt to convert it because
					// we
					// dont know the format that the date is in, ie. dd/mm or mm/dd, etc
				}
			}
		}

		return result;
	}

	public Date getAsIntegerDateOrNull(Map<String, Object> data, String key) {
		Integer intDat = getAsIntegerOrNull(data, key);

		if (intDat == null) {
			return null;
		}

		int y = (new Long(Math.round(Math.floor(intDat / 10000)))).intValue();
		int m = (new Long(Math.round(Math.floor((intDat - (y * 10000)) / 100)))).intValue();
		int d = (new Long(Math.round(intDat - ((y * 10000) + (m * 100))))).intValue();

		Calendar c = Calendar.getInstance();
		c.set(y, m - 1, d);

		return c.getTime();
	}

}
