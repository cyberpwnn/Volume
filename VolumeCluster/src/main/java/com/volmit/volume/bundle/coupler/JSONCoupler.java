package com.volmit.volume.bundle.coupler;

import java.util.List;

import com.volmit.volume.bundle.Bundle;
import com.volmit.volume.bundle.adapter.NodeAdapter;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.json.JSONArray;
import com.volmit.volume.lang.json.JSONObject;

public class JSONCoupler implements Coupler<JSONObject>
{
	private final JSONObject t;
	
	public JSONCoupler(JSONObject t)
	{
		this.t = t;
	}
	
	@Override
	public <N> Coupler<JSONObject> put(String key, NodeAdapter<N> adapter, N n)
	{
		JSONObject target = getHost();
		
		if(key.contains("."))
		{
			GList<String> path = key.contains(".") ? new GList<String>(key.split("\\.")) : new GList<String>().qadd(key);
			key = path.get(path.last());
			path.remove(path.last());
			
			for(String i : path)
			{
				if(!target.has(i))
				{
					target.put("i", new JSONObject());
				}
				
				target = target.getJSONObject(i);
			}
		}
		
		target.put(key, adapter.deflate(n));

		return this;
	}

	@Override
	public <N> Coupler<JSONObject> put(String key, NodeAdapter<N> adapter, List<N> n) 
	{
		JSONObject target = getHost();
		
		if(key.contains("."))
		{
			GList<String> path = key.contains(".") ? new GList<String>(key.split("\\.")) : new GList<String>().qadd(key);
			key = path.get(path.last());
			path.remove(path.last());
			
			for(String i : path)
			{
				if(!target.has(i))
				{
					target.put("i", new JSONObject());
				}
				
				target = target.getJSONObject(i);
			}
		}
		
		JSONArray a = new JSONArray();
		
		for(N i : n)
		{
			a.put(adapter.deflate(i));
		}
		
		target.put(key, a);

		return this;
	}

	@Override
	public <N> N get(String key, NodeAdapter<N> adapter)
	{
		JSONObject cursor = getHost();
		GList<String> path = key.contains(".") ? new GList<String>(key.split("\\.")) : new GList<String>().qadd(key);
		
		if(path.size() > 1)
		{
			key = path.get(path.last());
			path.remove(path.last());
			
			for(String i : path)
			{
				cursor = cursor.getJSONObject(i);
			}
		}
		
		return adapter.inflate(cursor.getString(key));
	}

	@Override
	public <N> List<N> getList(String key, NodeAdapter<N> adapter) 
	{
		JSONObject cursor = getHost();
		GList<String> path = key.contains(".") ? new GList<String>(key.split("\\.")) : new GList<String>().qadd(key);
		
		if(path.size() > 1)
		{
			key = path.get(path.last());
			path.remove(path.last());
			
			for(String i : path)
			{
				cursor = cursor.getJSONObject(i);
			}
		}
		
		JSONArray a = cursor.getJSONArray(key);
		GList<N> v = new GList<N>();
		
		for(int i = 0; i < a.length(); i++)
		{
			v.add(adapter.inflate(a.getString(i)));
		}
		
		return v;
	}

	@Override
	public JSONObject getHost() 
	{
		return t;
	}

	@Override
	public Bundle toBundle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coupler<JSONObject> fromBundle(Bundle b) {
		// TODO Auto-generated method stub
		return null;
	}
}
