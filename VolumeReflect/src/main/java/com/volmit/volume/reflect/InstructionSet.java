package com.volmit.volume.reflect;

import com.volmit.volume.lang.collections.GList;

public class InstructionSet
{
	private GList<String> importStrings;
	private GList<String> invocations;
	private String className;
	private String packageName;
	private static int ivd = 0;

	public InstructionSet()
	{
		this.packageName = "com.volmit.violator.violated.instruction.set";
		this.className = "InstructionSet" + ivd++;
		importStrings = new GList<String>();
		invocations = new GList<String>();
	}

	public InstructionSet imprt(String clazz)
	{
		importStrings.add(clazz);
		return this;
	}

	public InstructionSet imprt(Class<?> clazz)
	{
		importStrings.add(clazz.getCanonicalName());
		return this;
	}

	public InstructionSet invoke(String src)
	{
		invocations.add(src);
		return this;
	}

	public Runnable compileAndRun()
	{
		Runnable rx = compile();
		rx.run();

		return rx;
	}

	public Runnable compile()
	{
		String src = "";
		src += "package " + packageName + smc();

		for(String i : importStrings)
		{
			src += "import " + i + smc();
		}

		src += "public class " + className + " implements java.lang.Runnable {";
		src += "public void run(){";

		for(String i : invocations)
		{
			src += i;
		}

		src += "}}";
		System.out.println(src);
		try
		{
			Class<?> r = Violator.dynamicCompile(packageName + "." + className, src);
			Runnable rx = Violator.construct(r);

			return rx;
		}

		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private String smc()
	{
		return ";";
	}
}
