package com.volmit.volume.bukkit.command;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to define the default command prefix for responses (& color codes
 * supported)
 *
 * @author cyberpwn
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandTag
{
	String value();
}
