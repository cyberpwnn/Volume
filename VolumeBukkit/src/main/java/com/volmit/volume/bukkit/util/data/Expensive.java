package com.volmit.volume.bukkit.util.data;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;

@Retention(SOURCE)
public @interface Expensive
{
	public String value() default "";
}
