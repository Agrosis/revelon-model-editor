package com.jantox.rvtools;

public class Face {

	public Vector3D a, b, c, d;
	public Vector3D ta, tb, tc, td;
	
	public int tx = 1, ty = 1;
	
	int tex = 0;
	
	public Face(Vector3D[] facevtxes) {
		if(facevtxes.length == 3) {
			a = facevtxes[0];
			b = facevtxes[1];
			c = facevtxes[2];
			ta = tb = tc = new Vector3D();
			td = null;
			d = null;
		} else {
			a = facevtxes[0];
			b = facevtxes[1];
			c = facevtxes[2];
			d = facevtxes[3];
			ta = tb = tc = td = new Vector3D();
		}
	}
	
	public void setTexture(int t) {
		this.tex = t;
	}
	
	public Face(Vector3D a, Vector3D b, Vector3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		
		d = td = null;
		
		ta = tb = tc = new Vector3D();
	}
	
	public Face(Vector3D a, Vector3D b, Vector3D c, Vector3D d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		ta = tb = tc = td = new Vector3D();
	}

	public Vector3D getNormal() {
		Vector3D f = a.copy();
        f = f.subtract(d);
        
        f.normalize();
        
        Vector3D g = b.copy();
        g = g.subtract(d);
        
        g.normalize();

        Vector3D n = f.crossProduct(g);
        n.normalize();
        
        n.x = (float) n.x;
        n.y = (float) n.y;
        n.z = (float) n.z;
        
        return n;
    }
	
	public boolean isQuad() {
		return d != null;
	}

	public void setTextureCoords(Vector3D[] texs) {
		ta = texs[0];
		tb = texs[1];
		tc = texs[2];
		if(this.isQuad()) {
			td = texs[3];
		}
	}
	
}
