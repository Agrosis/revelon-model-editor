package com.jantox.rvtools;

public class Face {

	public Vector3D a, b, c, d;
	public Vector3D ta, tb, tc, td;
	
	public Face(Vector3D[] facevtxes) {
		if(facevtxes.length == 3) {
			a = facevtxes[0];
			b = facevtxes[1];
			c = facevtxes[2];
			ta = tb = tc = new Vector3D();
		} else {
			a = facevtxes[0];
			b = facevtxes[1];
			c = facevtxes[2];
			d = facevtxes[3];
			ta = tb = tc = td = new Vector3D();
		}
	}
	
	public Face(Vector3D a, Vector3D b, Vector3D c) {
		this.a = a;
		this.b = b;
		this.c = c;
		
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
        f = f.subtract(c);
        
        f.normalize();
        
        Vector3D g = b.copy();
        g = g.subtract(c);
        
        g.normalize();

        Vector3D n = f.crossProduct(g);
        n.normalize();
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
