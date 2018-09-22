package com.volmit.volume.lang.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VIO
{
	/**
	 * Transfers the length of the buffer amount of data from the input stream to
	 * the output stream
	 *
	 * @param in
	 *            the input
	 * @param out
	 *            the output
	 * @param amount
	 *            the buffer and size to use
	 * @return the actual transfered amount
	 * @throws IOException
	 *             shit happens
	 */
	public static int transfer(InputStream in, OutputStream out, byte[] buffer) throws IOException
	{
		int r = in.read(buffer);

		if(r != -1)
		{
			out.write(buffer, 0, r);
		}

		return r;
	}

	/**
	 * Transfers the length of the buffer amount of data from the input stream to
	 * the output stream
	 *
	 * @param in
	 *            the input
	 * @param out
	 *            the output
	 * @param targetBuffer
	 *            the buffer and size to use
	 * @param totalSize
	 *            the total amount to transfer
	 * @return the actual transfered amount
	 * @throws IOException
	 *             shit happens
	 */
	public static long transfer(InputStream in, OutputStream out, int targetBuffer, long totalSize) throws IOException
	{
		long total = totalSize;
		long wrote = 0;
		byte[] buf = new byte[targetBuffer];
		int r = 0;

		while((r = in.read(buf, 0, (int) (total < targetBuffer ? total : targetBuffer))) != -1)
		{
			total -= r;
			out.write(buf, 0, r);
			wrote += r;

			if(total <= 0)
			{
				break;
			}
		}

		return wrote;
	}

	/**
	 * Fully move data from a finite inputstream to an output stream using a buffer
	 * size of 8192. This does NOT close streams.
	 *
	 * @param in
	 * @param out
	 * @return total size transfered
	 * @throws IOException
	 */
	public static long fillTransfer(InputStream in, OutputStream out) throws IOException
	{
		return fullTransfer(in, out, 8192);
	}

	/**
	 * Fully move data from a finite inputstream to an output stream using a given
	 * buffer size. This does NOT close streams.
	 *
	 * @param in
	 *            the input stream to read from
	 * @param out
	 *            the output stream to write to
	 * @param bufferSize
	 *            the target buffer size
	 * @return total size transfered
	 * @throws IOException
	 *             shit happens
	 */
	public static long fullTransfer(InputStream in, OutputStream out, int bufferSize) throws IOException
	{
		long wrote = 0;
		byte[] buf = new byte[bufferSize];
		int r = 0;

		while((r = in.read(buf)) != -1)
		{
			out.write(buf, 0, r);
			wrote += r;
		}

		return wrote;
	}

	/**
	 * Recursive delete (deleting folders)
	 *
	 * @param f
	 *            the file to delete (and subfiles if folder)
	 */
	public static void delete(File f)
	{
		if(f == null || !f.exists())
		{
			return;
		}

		if(f.isDirectory())
		{
			for(File i : f.listFiles())
			{
				delete(i);
			}
		}

		f.delete();
	}

	public static long transfer(InputStream in, OutputStream out, byte[] buf, int totalSize) throws IOException {
		long total = totalSize;
		long wrote = 0;
		int r = 0;

		while((r = in.read(buf, 0, (int) (total < buf.length ? total : buf.length))) != -1)
		{
			total -= r;
			out.write(buf, 0, r);
			wrote += r;

			if(total <= 0)
			{
				break;
			}
		}

		return wrote;
	}
}
