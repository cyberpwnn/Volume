package com.volmit.volume.bundle;

import com.volmit.volume.lang.collections.GList;

public interface Bundle {	
	public <T> T get(String k);
	
	public Class<?> getType(String k);
	
	public boolean is(String k, Class<?> c);
	
	public Bundle put(String k, Object t);
	
	public GList<String> k();
	
	public Bundle crop(String prefix);
	
	public Bundle put(Bundle b);
	
	public Bundle copy();
	
	public int size();
}
