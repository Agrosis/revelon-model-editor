package com.jantox.rvtools;

public class Ray {

	public Vector3D pos, dir;
	
	public Ray(Vector3D pos, Vector3D dir) {
		this.pos = pos;
		this.dir = dir;
	}
	
	public Vector3D getPointAt(float f) {
		return pos.addition(dir.getMultiply(f));
	}
	
}
