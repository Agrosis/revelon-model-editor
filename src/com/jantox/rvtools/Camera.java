package com.jantox.rvtools;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Camera is a third person camera that focuses in on the Player
public class Camera {
	
	private float zoom;
	
	private Vector3D campos;

	private float fc_yaw, fc_pitch;
	private Vector3D focus;
	
	private float mx, my, lmx, lmy;
	private boolean mdown = false;
	
	public Camera() {
		this.campos = new Vector3D();
		this.fc_yaw = 0;
		this.fc_pitch = 0;
		this.focus = new Vector3D();
		this.zoom = 20;
	}
	
	public void update() {
		lmx = mx;
		lmy = my;
		
		mx = Mouse.getX();
		my = Mouse.getY();
		
		if(Mouse.isButtonDown(2)) {
			mdown = true;
		} else {
			mdown = false;
		}
		
		if(mdown) {
			if(mx - lmx != 0) {
				fc_yaw += (mx - lmx) * -0.75f;
			}
			if(my - lmy != 0) {
				fc_pitch += (my - lmy) * 0.75f;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			fc_yaw -=2.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			fc_yaw +=2.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			fc_pitch --;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			fc_pitch ++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.move(-(fc_yaw + 180) - 90);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.move(-(fc_yaw + 180) - 180);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.move(-(fc_yaw + 180) - 270);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.move(-(fc_yaw + 180));
		}
		
		int m = Mouse.getDWheel();
		
		if(m > 0) {
			zoom -= 1.25f;
		} else if(m < 0) {
			zoom += 1.25f;
		}
		
		this.fixCamera();
		
		Vector3D dir = this.getDirectionVector();
		dir.multiply(zoom);
		this.campos = focus.addition(dir);
	}
	
	public void move(float angle) {
		focus.x += Math.cos(Math.toRadians(angle)) * 0.2f;
		focus.z += Math.sin(Math.toRadians(angle)) * 0.2f;
	}
	
	public void translate() {
		glTranslatef((float)-campos.x,(float)-campos.y,(float)-campos.z);
	}
	
	public void rotate() {
		glRotatef(-fc_pitch, 1.0f, 0f, 0f);
        glRotatef(-(fc_yaw + 180), 0f, 1.0f, 0f);
	}
	
	public Vector3D getDirectionVector() {
		double pitchRadians = Math.toRadians(fc_pitch);
		double yawRadians = Math.toRadians(fc_yaw);

		double sinPitch = Math.sin(pitchRadians);
		double cosPitch = Math.cos(pitchRadians);
		double sinYaw = Math.sin(yawRadians);
		double cosYaw = Math.cos(yawRadians);

		return new Vector3D(-cosPitch * sinYaw, -sinPitch, -cosPitch * cosYaw);
	}
	
	public Vector3D getCamDirectionVector() {
		double pitchRadians = Math.toRadians(-fc_pitch);
		double yawRadians = Math.toRadians(fc_yaw+180);

		double sinPitch = Math.sin(pitchRadians);
		double cosPitch = Math.cos(pitchRadians);
		double sinYaw = Math.sin(yawRadians);
		double cosYaw = Math.cos(yawRadians);

		return new Vector3D(-cosPitch * sinYaw, -sinPitch, -cosPitch * cosYaw);
	}
	
	public void fixCamera() {
		if(zoom < 1) {
			zoom = 1;
		}
		if(zoom > 30) {
			zoom = 30;
		}
		if(fc_yaw >= 360) {
			fc_yaw -= 360;
		}
		if(fc_yaw < 0) {
			fc_yaw += 360;
		}
	}

	public Vector3D getPosition() {
		return campos.copy();
	}
	
}
