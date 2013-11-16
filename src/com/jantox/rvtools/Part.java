package com.jantox.rvtools;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

public class Part {

	public static boolean HIGHLIGHT = true;
	
	private ArrayList<Face> faces;
	private boolean visible;
	
	public Part() {
		faces = new ArrayList<Face>();
		this.visible = true;
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void addFace(Face t) {
		faces.add(t);
	}

	public void render(Camera camera) {
		for(int i = 0; i < faces.size(); i++) {
			Face t = faces.get(i);
			
			if(t.tex > 0)
				glColor3f(1,1,1);
			else
				glColor3f(0.5f,0.5f,0.5f);
			
			if(t.isQuad()) {
				RayIntersection ri = Collisions.rayQuad(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.a, t.getNormal(), null);
				if(ri != null) {
					if(HIGHLIGHT)
						glColor4f(0, 1, 0, 0.5f);
				}
			} else {
				RayIntersection ri = Collisions.rayTriangle(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.getNormal(), null);
				if(ri != null) {
					if(HIGHLIGHT)
						glColor4f(0, 1, 0, 0.5f);
				}
			}
			
			if(t.tex > 0) {
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL_TEXTURE_2D, t.tex);
			}
			
			if(t.isQuad()) {
				glBegin(GL_QUADS);
				glTexCoord2f((float)t.ta.x * t.tx, (float)t.ta.y * t.ty);
                glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
                glTexCoord2f((float)t.tb.x * t.tx, (float)t.tb.y * t.ty);
                glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
                glTexCoord2f((float)t.tc.x * t.tx, (float)t.tc.y * t.ty);
                glVertex3f((float)t.c.x, (float)t.c.y, (float)t.c.z);
                glTexCoord2f((float)t.td.x * t.tx, (float)t.td.y * t.ty);
                glVertex3f((float)t.d.x, (float)t.d.y, (float)t.d.z);
			} else {
				glBegin(GL_TRIANGLES);
				glTexCoord2f((float)t.ta.x * t.tx, (float)t.ta.y * t.ty);
                glVertex3f((float)t.a.x, (float)t.a.y, (float)t.a.z);
                glTexCoord2f((float)t.tb.x * t.tx, (float)t.tb.y * t.ty);
                glVertex3f((float)t.b.x, (float)t.b.y, (float)t.b.z);
                glTexCoord2f((float)t.tc.x * t.tx, (float)t.tc.y * t.ty);
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
	
	public Face getIntersect(Camera camera) {
		Face f = null;
		double d = 1000000;
		
		for(int i = 0; i < faces.size(); i++) {
			Face t = faces.get(i);

			if(t.isQuad()) {
				RayIntersection ri = Collisions.rayQuad(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.d, t.getNormal(), null);
				if(ri != null) {
					if(ri.t < d) {
						d = ri.t;
						f = t;
					}
				}
			} else {
				RayIntersection ri = Collisions.rayTriangle(new Ray(camera.getPosition(), camera.getCamDirectionVector()), t.a, t.b, t.c, t.getNormal(), null);
				if(ri != null) {
					if(ri.t < d) {
						d = ri.t;
						f = t;
					}
				}
			}
		}
		
		return f;
	}

	public ArrayList<Face> getFaces() {
		return this.faces;
	}
	
}
