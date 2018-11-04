package com.volmit.volume.bundle.adapter;

public interface NodeAdapter<T> extends NodeInflater<T>, NodeDeflater<T>
{
	public Class<?> getManagedClass();
}
