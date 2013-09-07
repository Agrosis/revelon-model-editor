package com.jantox.rvtools;

public class Triangle {

	public Vector3D a, b, c;
	public Vector3D ta, tb, tc;
	
	public Triangle() {
		a = b = c = new Vector3D();
		ta = tb = tc = new Vector3D();
	}
	
	public Triangle(Vector3D a, Vector3D b, Vector3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		
		ta = tb = tc = new Vector3D();
	}

	public Vector3D getNormal() {
		Vector3D f = a.copy();
        f = f.subtract(c);
        
        //f.debug();
        
        f.normalize();
        
        Vector3D g = b.copy();
        g = g.subtract(c);
        
        //g.debug();
        
        g.normalize();

        Vector3D n = f.crossProduct(g);
        n.normalize();
        return n;
    }
	
}
