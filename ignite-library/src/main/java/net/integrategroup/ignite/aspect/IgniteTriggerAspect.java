package net.integrategroup.ignite.aspect;

import java.lang.reflect.Field;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.utils.SecurityUtils;

/**
 * @author Tony De Buys
 *
 */
@Aspect
@Component
public class IgniteTriggerAspect {

	@Autowired
	SecurityUtils securityUtils;

	/**
	 * Perform any actions before the object is saved. This allows us to update last
	 * updated by/on, etc dynamically
	 *
	 * @param joinPoint
	 */
	@Before("execution(* *.save(..))")
	public void logBeforeSave(JoinPoint joinPoint) {
		logBefore(joinPoint);
	}

	private void logBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}
			String canonicalClassName = arg.getClass().getCanonicalName();

			// we only want to start workflows on our own classes
			if (!canonicalClassName.startsWith("net.integrategroup.ignite")) {
				continue;
			}

			try {
				// NOTE: these fields are the fields of the Java object NOT the database field!
				Date dts = new Date();
				String username = securityUtils.getUsername();

				setField(arg, "lastUpdateTimestamp", dts);
				if (username != null) {
					// Username cannot be null, if it's being saved by a background process the
					// background process itself needs to set a username
					setField(arg, "lastUpdateUserName", username);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private void setField(Object arg, String fieldName, Object value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if (hasField(arg, fieldName)) {
			Field field = arg.getClass().getDeclaredField(fieldName);
			boolean accessible = field.isAccessible();

			field.setAccessible(true);
			field.set(arg, value);
			field.setAccessible(accessible);
		}
	}

	private boolean hasField(Object object, String attributeName) {
		boolean result = false;

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (attributeName.equalsIgnoreCase(field.getName())) {
				result = true;
				break;
			}
		}
		return result;
	}

}
