package com.faceless.containers;

import java.util.Hashtable;

public class PropertyContainer
{
	private Hashtable<String, String> properties = new Hashtable<>();

	public void setProperty(String path, Object propertyValue)
	{
		if (properties.containsKey(path))
			properties.replace(path, "" + propertyValue);
		else
			properties.put(path, "" + propertyValue);
	}

	public String getProperty(String path)
	{
		return properties.get(path);
	}

	public String removeProperty(String path)
	{
		return properties.remove(path);
	}

	public boolean hasProperty(String path)
	{
		return properties.containsKey(path);
	}
}
