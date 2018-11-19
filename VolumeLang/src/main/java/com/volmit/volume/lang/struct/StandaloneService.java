package com.volmit.volume.lang.struct;

public abstract class StandaloneService extends Thread implements Service
{
	private final String name;
	private ServiceStatus status;

	public StandaloneService(String name)
	{
		this.name = name;
		status = ServiceStatus.OFFLINE;
		setName(getServiceName() + " Service");
	}

	@Override
	public abstract void l(String log);

	@Override
	public abstract void w(String warn);

	@Override
	public abstract void f(String fatal);

	@Override
	public abstract void startService();

	@Override
	public abstract void stopService();

	@Override
	public String getServiceName()
	{
		return name;
	}

	@Override
	public ServiceStatus getStatus()
	{
		return status;
	}

	@Override
	public void status(ServiceStatus status)
	{
		if(!status.equals(this.status))
		{
			l("status changed " + this.status + " -> " + status);
		}

		this.status = status;
	}
}
