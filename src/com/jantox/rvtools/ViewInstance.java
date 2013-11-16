package com.jantox.rvtools;

import java.awt.Canvas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;

public class ViewInstance implements Runnable {
	
	private Canvas canvas;
	private Camera camera;
	
	private Model model;
	
	private TexturePanel tpanel;
	
	String[] texs;
	
	public ViewInstance(Canvas canvas, TexturePanel tpanel, Model m, String[] texs) {
		this.canvas = canvas;
		this.camera = new Camera();
		
		this.texs = texs;
		
		this.tpanel = tpanel;
		
		this.model = m;
		tpanel.setModel(model);
	}

	public void initGL() {
		glViewport(0, 0, 470, 470);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(68, (float) 470 / (float) 470, 0.01f, 2000.0f);
        glMatrixMode(GL_MODELVIEW);

        glClearDepth(1);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        

		for(int i = 0; i < texs.length; i++) {
			try {
				TextureLoader.getTexture("PNG", new FileInputStream("textures/" + texs[i])).getTextureID();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			Display.setParent(this.canvas);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		this.initGL();
		
		while(true) {
			this.update();
			render();
			
			Display.update();
			Display.sync(60);
		}
	}
	
	Face cf = null;
	
	public void update() {
		camera.update();
		
		Face f = model.getIntersectedFace(camera);
		if(f != cf && f != null) {
			cf = f;
			tpanel.newFace(f);
		}
	}
	
	public void render() {
		glClearColor(0.82f,0.8f,0.8f,0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	    glLoadIdentity();
	    
	    glPushMatrix();
	    
	    camera.rotate();
	    camera.translate();
	    
	    if(model != null)
	    	model.render(camera);
	    
	    glColor3f(1, 0, 0);
	    glBegin(GL_LINES);
	    
	    for(int z = -15; z <= 15; z++) {
	    	glVertex3f(15, 0, z);
	    	glVertex3f(-15, 0, z);
	    }
	    
	    for(int x = -15; x <= 15; x++) {
	    	glVertex3f(x, 0, -15);
	    	glVertex3f(x, 0, 15);
    	}
	    
	    glEnd();
	    
	    glPopMatrix();
	}

}
