package com.volmit.volume.bukkit.util.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

public class Frame
{
	private int width;
	private int height;
	private BufferedImage image;
	private Graphics2D gg;

	public Frame(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		gg = (Graphics2D) image.createGraphics();
	}

	public void drawFrame(Frame frame, int x, int y)
	{

	}

	public void drawPolygon(Polygon polygon, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawPolygon(polygon);
	}

	public void drawLines(Polygon polygon, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}

	public void drawFillPolygon(Polygon polygon, Color color)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(0.5f));
		gg.fillPolygon(polygon);
	}

	public void drawElipse(int x, int y, int width, int height, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawOval(x, y, width, height);
	}

	public void drawFillElipse(int x, int y, int width, int height, Color color)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(0.5f));
		gg.drawOval(x, y, width, height);
	}

	public void translate(int x, int y)
	{
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		Graphics2D g = copy.createGraphics();
		at.translate(x, y);
		g.drawImage(image, at, null);
		g.dispose();
		gg.dispose();
		image = copy;
		gg = image.createGraphics();
	}

	public void scale(double x, double y)
	{
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		Graphics2D g = copy.createGraphics();
		at.scale(x, y);
		g.drawImage(image, at, null);
		g.dispose();
		gg.dispose();
		image = copy;
		gg = image.createGraphics();
	}

	public void rotateVector(double x, double y)
	{
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		Graphics2D g = copy.createGraphics();
		at.translate(getWidth() / 2, getHeight() / 2);
		at.rotate(x, y);
		at.translate(-getWidth(), -getHeight());
		g.drawImage(image, at, null);
		g.dispose();
		gg.dispose();
		image = copy;
		gg = image.createGraphics();
	}

	public void rotateDegrees(double deg)
	{
		rotateRadians(Math.toRadians(deg));
	}

	public void rotateRadians(double theta)
	{
		BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		Graphics2D g = copy.createGraphics();
		at.translate(getWidth() / 2, getHeight() / 2);
		at.rotate(theta);
		at.translate(-getWidth(), -getHeight());
		g.drawImage(image, at, null);
		g.dispose();
		gg.dispose();
		image = copy;
		gg = image.createGraphics();
	}

	public void drawArc(int x, int y, int w, int h, double startDeg, double lengthDeg, Color color, float thickness)
	{
		Arc2D arc = new Arc2D.Double(x, y, w, h, startDeg, lengthDeg, Arc2D.OPEN);
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.draw(arc);
	}

	public void drawFillArc(int x, int y, int w, int h, double startDeg, double lengthDeg, Color color)
	{
		Arc2D arc = new Arc2D.Double(x, y, w, h, startDeg, lengthDeg, Arc2D.PIE);
		gg.setColor(color);
		gg.setStroke(new BasicStroke(0.5f));
		gg.draw(arc);
	}

	public void drawLine(int x1, int y1, int x2, int y2, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawLine(x1, y1, x2, y2);
	}

	public void drawRect(int x1, int y1, int x2, int y2, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawRect(x1, y1, x2 - x1, y2 - y1);
	}

	public void drawFillRect(int x1, int y1, int x2, int y2, Color color)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(0.5f));
		gg.fillRect(x1, y1, x2 - x1, y2 - y1);
	}

	public void drawRoundRect(int x1, int y1, int x2, int y2, int arcX, int arcY, Color color, float thickness)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(thickness));
		gg.drawRoundRect(x1, y1, x2 - x1, y2 - y1, arcX, arcY);
	}

	public void drawFillRoundRect(int x1, int y1, int x2, int y2, int arcX, int arcY, Color color)
	{
		gg.setColor(color);
		gg.setStroke(new BasicStroke(0.5f));
		gg.drawRoundRect(x1, y1, x2 - x1, y2 - y1, arcX, arcY);
	}

	public void clear(Color color)
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				set(i, j, color);
			}
		}
	}

	public void set(int x, int y, Color color)
	{
		image.setRGB(x, y, color.getRGB());
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
