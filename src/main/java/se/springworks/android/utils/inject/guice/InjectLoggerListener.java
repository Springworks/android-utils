package se.springworks.android.utils.inject.guice;

import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.logging.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InjectLoggerListener extends CustomInjectionListener {

	public InjectLoggerListener() {
		super(InjectLogger.class);
	}

	@Override
	protected void inject(Object object, Field field, Annotation annotation) throws IllegalArgumentException, IllegalAccessException {
		field.set(object, LoggerFactory.getLogger(field.getDeclaringClass()));
	}
}
