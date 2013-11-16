package com.jantox.rvtools;

public class Collisions {
	
	public static RayIntersection rayQuad(Ray r, Vector3D a, Vector3D b, Vector3D c, Vector3D d, Vector3D n, Vector3D col) {
        RayIntersection ri = rayPlane(r, a, n, col);
        if(ri != null) {
            if(pointTriangle(ri.r, a, b, c)) {
                return ri;
            }
            if(pointTriangle(ri.r, b, c, d)) {
                return ri;
            }
        }

        return null;
    }
	
	public static RayIntersection rayTriangle(Ray r, Vector3D a, Vector3D b, Vector3D c, Vector3D n, Vector3D col) {
        RayIntersection ri = rayPlane(r, a, n, col);
        if(ri != null) {
            if(pointTriangle(ri.r, a, b, c)) {
                return ri;
            }
        }

        return null;
    }

    // this function checks if a ray intersects with a plane
    public static RayIntersection rayPlane(Ray r, Vector3D a, Vector3D n, Vector3D col) {
        double d = a.dotProduct(n);
        
        if(n.dotProduct(r.dir) == 0)
            return null;
        
        double t = (d - n.dotProduct(r.pos)) / (n.dotProduct(r.dir));
        if(t < 0)
            return null;

        if(col != null) {
            col.x = t;
        }

        return new RayIntersection(r.getPointAt((float) t), t);
    }

	public static boolean pointTriangle(Vector3D p, Vector3D a, Vector3D b, Vector3D c) {
        double ab = a.distance(b);
        double bc = b.distance(c);
        double ac = c.distance(a);

        float total_area = areaTriangle(new Vector3D(ab, bc, ac));

        double pa = p.distance(a);
        double pb = p.distance(b);
        double pc = p.distance(c);

        float at1 = areaTriangle(new Vector3D(ab, pb, pa));
        float at2 = areaTriangle(new Vector3D(pb, pc, bc));
        float at3 = areaTriangle(new Vector3D(pa, ac, pc));

        if(Math.abs(total_area - at1 - at2 - at3) <= 0.5)
            return true;

        //System.out.println((at1 + at2 + at3) + " : " + total_area);

        /*if(at1 + at2 + at3 == total_area)
            return true;
*/
        return false;
    }

    // area of a triangle using herons formula
    public static float areaTriangle(Vector3D sides) {
        double smp = (sides.x + sides.y + sides.z) / 2;

        return (float) Math.sqrt(smp * (smp - sides.x) * (smp - sides.y) * (smp - sides.z));
    }
	
}
