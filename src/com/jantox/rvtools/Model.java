package com.jantox.rvtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Model {

	private ArrayList<Part> parts;
	
	public Model() {
		this.parts = new ArrayList<Part>();
	}
	
	public void addPart(Part p) {
		parts.add(p);
	}
	
	public void render(Camera camera) {
		for(int i = 0; i < parts.size(); i++) {
			if(parts.get(i).isVisible())
				parts.get(i).render(camera);
		}
		
		glColor3f(0,1,1);
		glBegin(GL_LINES);
		glVertex3f(-16, 5, 0);
		glVertex3f(16, 5, 0);
		glEnd();
	}
	
	@SuppressWarnings("resource")
	public static Part loadPart(File file) {
		Part p = new Part();

        InputStream fis = null;
        BufferedReader br = null;
        String line = "";

        ArrayList<String> defs = new ArrayList<String>();

        try {
            fis = new FileInputStream(file.getAbsolutePath());
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            while ((line = br.readLine()) != null) {
                defs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ArrayList<Vector3D> vertexes = new ArrayList<Vector3D>();

        for(int i = 0; i < defs.size(); i++) {
            String data = defs.get(i);
            String[] params = data.split(" ");
            if(data.startsWith("v ")) {
                float x = Float.valueOf(params[1]);
                float y = Float.valueOf(params[2]);
                float z = Float.valueOf(params[3]);
                vertexes.add(new Vector3D(x, y, z));
            } else if(data.startsWith("f ")) {
                Vector3D[] facevtxes = null;
                
                if(params.length == 4)
                	facevtxes = new Vector3D[3];
                else if(params.length == 5)
                	facevtxes = new Vector3D[4];

                for(int f = 0; f < params.length-1; f++) {
                	facevtxes[f] = vertexes.get(Integer.valueOf(params[f+1])-1);
                }
                
                p.addFace(new Face(facevtxes));
            }
        }
        
	    return p;
	}
	
	public Face getIntersectedFace(Camera camera) {
		Face best = null;
		double d = 1000000;
		
		for(int i = 0; i < parts.size(); i++) {
			Face t = parts.get(i).getIntersect(camera);
			
			if(t != null) {
				if(t.isQuad()) {
					RayIntersection ri = Collisions.rayQuad(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.d, t.getNormal(), null);
					if(ri != null) {
						if(ri.t < d) {
							d = ri.t;
							best = t;
						}
					}
				} else {
					RayIntersection ri = Collisions.rayTriangle(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.getNormal(), null);
					if(ri != null) {
						if(ri.t < d) {
							d = ri.t;
							best = t;
						}
					}
				}
			}
		}
		
		return best;
	}

	public void visibility(int i) {
		parts.get(i).setVisible(!parts.get(i).isVisible());
	}

	public Part getPart(int index) {
		return parts.get(index);
	}

	public void remove(int i) {
		parts.remove(i);
	}

	public int getPartSize() {
		return parts.size();
	}
	
}
