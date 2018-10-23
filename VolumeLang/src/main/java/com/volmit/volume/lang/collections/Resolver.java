package com.volmit.volume.lang.collections;

@FunctionalInterface
public interface Resolver<K, V>
{
	public V resolve(K k);
}
