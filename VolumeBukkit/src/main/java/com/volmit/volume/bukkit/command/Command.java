package com.volmit.volume.bukkit.command;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Represents a command pointer
 *
 * @author cyberpwn
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Command
{
	String value() default "";
}
