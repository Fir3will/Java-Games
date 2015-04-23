package modhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Initialization
{
	State value();

	public static enum State
	{
		INIT,
		PRE_INIT,
		POST_INIT
	}
}
