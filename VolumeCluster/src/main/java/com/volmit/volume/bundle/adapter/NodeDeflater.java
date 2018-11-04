package com.volmit.volume.bundle.adapter;

@FunctionalInterface
public interface NodeDeflater<T> 
{
	public T inflate(String k);
}
