package com.jantox.rvtools;

import java.awt.Canvas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;

public class ViewInstance implements Runnable {
	
	private Canvas canvas;
	private Camera camera;
	
	private Model model;
	private int tid;
	
	private TexturePanel tpanel;
	
	public ViewInstance(Canvas canvas, TexturePanel tpanel) {
		this.canvas = canvas;
		this.camera = new Camera();
		
		this.tpanel = tpanel;
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
		
		try {
			this.tid = TextureLoader.getTexture("PNG", new FileInputStream("texture.png")).getTextureID();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			this.update();
			render();
			
			Display.update();
			Display.sync(60);
		}
	}
	
	public void update() {
		camera.update();
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
	
	public void loadModel(File file) {
		this.model = Model.loadModel(file, tpanel);
		this.model.setTexture(tid);
		tpanel.m = this.model;
	}

}
