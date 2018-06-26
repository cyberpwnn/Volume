package com.volmit.lux;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Astronomy;
import com.github.fedy2.weather.data.Atmosphere;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Units;
import com.github.fedy2.weather.data.Wind;
import com.github.fedy2.weather.data.unit.DegreeUnit;

public class Weather
{
	private Channel channel;

	public Weather(String id, DegreeUnit u) throws JAXBException, IOException
	{
		YahooWeatherService service = new YahooWeatherService();
		channel = service.getForecast("2418117", DegreeUnit.FAHRENHEIT);
	}

	public Wind getWind()
	{
		return channel.getWind();
	}

	public Units getUnits()
	{
		return channel.getUnits();
	}

	public Atmosphere getAtmosphere()
	{
		return channel.getAtmosphere();
	}

	public Astronomy getAstronomy()
	{
		return channel.getAstronomy();
	}
}
