package com.volmit.volume.cluster;

import java.util.List;

import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.lang.json.JSONArray;
import com.volmit.volume.lang.json.JSONObject;

public class JSONClusterPort implements IClusterPort<JSONObject>
{
	private GMap<String, Object> map;

	@Override
	public DataCluster toCluster(JSONObject t) throws Exception
	{
		map = new GMap<String, Object>();
		DataCluster cc = new DataCluster();
		mapIn(t, "");

		for(String i : map.k())
		{
			cc.set(i, map.get(i));
		}

		return cc;
	}

	@Override
	public JSONObject fromCluster(DataCluster c) throws Exception
	{
		JSONObject j = new JSONObject();

		for(String i : c.k())
		{
			mapOut(j, i, c.get(i));
		}

		return j;
	}

	@SuppressWarnings("unchecked")
	private void mapOut(JSONObject j, String k, Object object)
	{
		if(k.contains("."))
		{
			JSONObject jn = new JSONObject();
			j.put(k.split("\\.")[0], jn);
			mapOut(jn, k.replaceFirst("\\Q" + k.split("\\.")[0] + ".\\E", ""), object);
		}

		else if(object instanceof List)
		{
			JSONArray ja = new GList<String>((List<String>) object).toJSONStringArray();
			j.put(k, ja);
		}

		else
		{
			j.put(k, object);
		}
	}

	private void mapIn(JSONObject j, String pre)
	{
		for(String i : j.keySet())
		{
			Object o = j.get(i);

			if(o instanceof JSONObject)
			{
				mapIn((JSONObject) o, i + ".");
			}

			else if(o instanceof JSONArray)
			{
				map.put(pre + i, GList.from((JSONArray) o));
			}

			else
			{
				map.put(pre + i, o);
			}
		}
	}
}
