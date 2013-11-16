package com.jantox.rvtools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TexturePanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private BufferedImage texture;
	
	Vector3D texs[];
	int sel = -1;
	
	public Model m;
	private Face f;
	
	int tex = -1;

	public TexturePanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setPreferredSize(new Dimension(256, 256));
	}
	
	public void setTexture(BufferedImage bi, int tex) {
		this.tex = tex;
		this.texture = bi;
		if(f != null) {
			f.setTexture(tex);
		}
		
		this.repaint();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 256, 256);
		
		g.drawImage(texture, 0, 0, null);
		
		g.setColor(Color.RED);
		for(int j = 0; j < 256; j+= 16) {
			for(int i = 0; i < 256; i+=16) {
				g.fillRect(i - 1, j - 1, 2, 2);
			}
		}
		
		if(f != null) {
			g.setColor(Color.YELLOW);
			for(int i = 0; i < texs.length; i++) {
				Vector3D a = texs[i];
				if(i == (texs.length - 1)) {
					Vector3D b = texs[0];
					g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
				} else {
					Vector3D b = texs[i+1];
					g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
				}
			}
			
			g.setColor(Color.CYAN);
			for(int i = 0; i < texs.length; i++) {
				Vector3D a = texs[i];
				g.fillRect((int)a.x - 2, (int)a.y - 2, 4, 4);
			}
		}
	}
	
	public void newFace(Face f) {
		this.f = f;
		if(f.isQuad()) {
			texs = new Vector3D[4];
			texs[0] = f.ta.getMultiply(128);
			texs[1] = f.tb.getMultiply(128);
			texs[2] = f.tc.getMultiply(128);
			texs[3] = f.td.getMultiply(128);
		} else {
			texs = new Vector3D[3];
			texs[0] = f.ta.getMultiply(128);
			texs[1] = f.tb.getMultiply(128);
			texs[2] = f.tc.getMultiply(128);
		}
		
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent me) {
		Vector3D m = new Vector3D(me.getX(), me.getY(), 0);
	
		double dist = 100000;
		
		if(texs != null) {
			for(int i = 0; i < texs.length; i++) {
				if(m.distance(texs[i]) < dist) {
					dist = m.distance(texs[i]);
					if(dist < 4)
						sel = i;
				}
			}
		}
	}

	public void switchCoords() {
		Vector3D temp = texs[0].copy();
		for(int i = 0; i < texs.length-1; i++) {
			texs[i] = texs[i+1];
		}
		texs[texs.length-1] = temp.copy();
		
		Vector3D[] nt = new Vector3D[texs.length];
		for(int i = 0; i < texs.length; i++) {
			nt[i] = texs[i];
			nt[i] = nt[i].divide(128);
		}
		
		f.setTextureCoords(nt);
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		sel = -1;
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if(sel != -1) {
			texs[sel] = new Vector3D(me.getX(), me.getY(), 0);
			
			if(me.getX() < 0)
				texs[sel].x = 0;
			if(me.getY() < 0)
				texs[sel].y = 0;
			
			texs[sel].round();
			
			Vector3D[] nt = new Vector3D[texs.length];
			for(int i = 0; i < texs.length; i++) {
				nt[i] = texs[i];
				nt[i] = nt[i].divide(128);
			}
			
			f.setTextureCoords(nt);
			f.setTexture(this.tex);
			
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	public void setModel(Model model) {
		this.m = model;
	}

	public void shift(int i) {
		if(texs != null) {
			if(i == 0) {
				for(int x = 0; x < texs.length; x++) {
					texs[x].y -=16;
				}
			} else if(i == 1) {
				for(int x = 0; x < texs.length; x++) {
					texs[x].x +=16;
				}
			} else if(i == 2) {
				for(int x = 0; x < texs.length; x++) {
					texs[x].y +=16;
				}
			} else if(i == 3) {
				for(int x = 0; x < texs.length; x++) {
					texs[x].x -= 16;
				}
			}
			
			Vector3D[] nt = new Vector3D[texs.length];
			for(int x = 0; x < texs.length; x++) {
				nt[x] = texs[x];
				nt[x] = nt[x].divide(128);
			}
			
			f.setTextureCoords(nt);
			
			this.repaint();
		}
	}

	public void setTextureRepeat(int i, int j) {
		f.tx = i;
		f.ty = j;
	} 
	
}
