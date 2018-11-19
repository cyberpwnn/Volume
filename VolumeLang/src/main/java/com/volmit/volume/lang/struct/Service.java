package com.volmit.volume.lang.struct;

public interface Service
{
	public String getServiceName();

	public void startService();

	public void stopService();

	public ServiceStatus getStatus();

	public void status(ServiceStatus status);

	public void l(String log);

	public void w(String warn);

	public void f(String fatal);
}
