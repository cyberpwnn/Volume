package com.volmit.volume.bundle.adapter;

@FunctionalInterface
public interface NodeInflater<T> 
{
	public String deflate(T t);
}
