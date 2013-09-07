package com.jantox.rvtools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Model {
	
	private ArrayList<Triangle> faces;
	private Triangle close;
	
	private int tid = -1;

	public Model() {
		faces = new ArrayList<Triangle>();
		close = null;
	}
	
	public void addFace(Triangle t) {
		faces.add(t);
	}
	
	public void render(Camera camera) {
		double cd = 1000000;
		
		for(int i = 0; i < faces.size(); i++) {
			Triangle t = faces.get(i);

			RayIntersection ri = Collisions.rayTriangle(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.getNormal(), null);
			if(ri != null) {
				if(ri.t < cd) {
					close = t;
					cd = ri.t;
				}
				glColor4f(0f, 1f, 0f, 0.9f);
			}
		}
		
		for(int i = 0; i < faces.size(); i++) {
			Triangle t = faces.get(i);

			if(t == close) {
				glColor4f(0, 1, 0, 0.9f);
			} else {
				glColor3f(0.5f, 0.5f, 0.5f);
			}
			
			glEnable(GL_TEXTURE_2D);
			glBegin(GL_TRIANGLES);
			
			if(tid != -1) {
				glBindTexture(GL_TEXTURE_2D, tid);
				
				glTexCoord2f((float)t.ta.x, (float)t.ta.y);
				glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
				glTexCoord2f((float)t.tb.x, (float)t.tb.y);
				glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
				glTexCoord2f((float)t.tc.x, (float)t.tc.y);
				glVertex3f((float)t.c.x, (float)t.c.y, (float)t.c.z);
			} else {
				glDisable(GL_TEXTURE_2D);
				glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
				glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
				glVertex3f((float)t.c.x, (float)t.c.y, (float)t.c.z);
			}
			
			glEnd();
			glDisable(GL_TEXTURE_2D);
			
			glColor3f(0,0,0);
			glBegin(GL_LINES);
			glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
			glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
			glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
			glVertex3f((float)t.c.x, (float)t.c.y, (float)t.c.z);
			glVertex3f((float)t.c.x, (float)t.c.y, (float)t.c.z);
			glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
			glEnd();
		}
	}
	
	@SuppressWarnings("resource")
	public static Model loadModel(File file) {
		Model m = new Model();

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
                Vector3D[] facevtxes = new Vector3D[3];

                for(int f = 0; f < 3; f++) {
                	facevtxes[f] = vertexes.get(Integer.valueOf(params[f+1])-1);
                }
                
                m.addFace(new Triangle(facevtxes[0], facevtxes[1], facevtxes[2]));
            }
        }
		
		return m;
	}

	public void setTexture(int tid2) {
		this.tid = tid2;
	}
	
	public void setTriangleTextureCoords(Vector3D a, Vector3D b, Vector3D c) {
		close.ta = a;
		close.tb = b;
		close.tc = c;
	}
	
}
