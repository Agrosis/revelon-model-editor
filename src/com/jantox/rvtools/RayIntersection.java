package com.jantox.rvtools;

public class RayIntersection {

	public Vector3D r;
	public double t;
	
	public RayIntersection(Vector3D r, double t) {
		this.r = r.copy();
		this.t = t;
	}
	
}
