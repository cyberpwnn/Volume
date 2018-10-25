package com.volmit.volume.bundle.coupler;

import java.util.List;

import com.volmit.volume.bundle.Bundle;
import com.volmit.volume.bundle.adapter.NodeAdapter;

public interface Coupler<T>
{
	public T getHost();
	
	public <N> Coupler<T> put(String key, NodeAdapter<N> adapter, N n);
	
	public <N> Coupler<T> put(String key, NodeAdapter<N> adapter, List<N> n);
	
	public <N> N get(String key, NodeAdapter<N> adapter);
	
	public <N> List<N> getList(String key, NodeAdapter<N> adapter);
	
	public Bundle toBundle(Bundle b);
	
	public Coupler<T> fromBundle(Bundle b);
}
