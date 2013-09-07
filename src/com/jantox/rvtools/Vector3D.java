package com.jantox.rvtools;

import java.util.Random;

public class Vector3D {
	
	private static Random r = new Random(3423);

	public double x, y, z;
	
	public Vector3D(double a, double b, double c) {
		this.x = a;
		this.y = b;
		this.z = c;
	}
	
	public Vector3D() {
		x = y = z = 0;
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public void normalize() {
		double len = length();
		x /= len;
		y /= len;
		z /= len;
	}
	
	public double dotProduct(Vector3D b) {
		return x * b.x + y * b.y + z * b.z;
	}

	public Vector3D addition(Vector3D a) {
		return new Vector3D(x + a.x, y + a.y, z + a.z);
	}

	public void multiply(float f) {
		x *= f;
		y *= f;
		z *= f;
	}

	public Vector3D copy() {
		return new Vector3D(x,y,z);
	}

	public double distance(Vector3D b) {
		return Math.sqrt((b.z - z) * (b.z - z) + (b.y - y) * (b.y - y) + (b.x - x) * (b.x - x));
	}

	public void add(Vector3D b) {
		x += b.x;
		y += b.y;
		z += b.z;
	}

	public Vector3D getCloseTo(double d) {
		return this.copy().addition(new Vector3D(r.nextGaussian() * d, r.nextGaussian() * d, r.nextGaussian() * d));
	}
	
	public Vector3D getXZCloseTo(double d) {
		return this.copy().addition(new Vector3D(r.nextGaussian() * d, 0, r.nextGaussian() * d));
	}

	public Vector3D subtract(Vector3D b) {
		return new Vector3D(x - b.x, y - b.y, z - b.z);
	}

	public Vector3D getMultiply(float f) {
		return new Vector3D(f * x, f * y, f * z);
	}
	
	public Vector3D crossProduct(Vector3D b) {
        Vector3D n = new Vector3D();

        n.x = y * b.z - z * b.y;
        n.y = z * b.x - x * b.z;
        n.z = x * b.y - y * b.x;

        return n;
    }

	public void round() {
		x = Math.round(x/16) * 16;
		y = Math.round(y/16) * 16;
		z = Math.round(z/16) * 16;
	}

	public void debug() {
		System.out.println(x + " " + y + " " + z);
	}

	public Vector3D inverse() {
		return new Vector3D(-x, -y, -z);
	}

	public Vector3D divide(float f) {
		return new Vector3D(x / f, y / f, z / f);
	}
	
}
