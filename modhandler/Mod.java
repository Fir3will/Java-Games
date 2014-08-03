package modhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import main.utils.SaveManager;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mod
{
	String modid();

	String version();

	Class<? extends SaveManager> save() default SaveManager.class;
}
