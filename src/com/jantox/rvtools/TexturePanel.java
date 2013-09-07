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
	private Vector3D a, b, c;
	int sel = -1;
	
	public Model m;

	public TexturePanel() {
		try {
			this.texture = ImageIO.read(new File("texture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		a = new Vector3D();
		b = new Vector3D(64, 0, 0);
		c = new Vector3D(0, 64, 0);
		
		this.setPreferredSize(new Dimension(256, 256));
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
		
		g.setColor(Color.YELLOW);
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
		g.drawLine((int)b.x, (int)b.y, (int)c.x, (int)c.y);
		g.drawLine((int)a.x, (int)a.y, (int)c.x, (int)c.y);
		
		g.setColor(Color.CYAN);
		g.fillRect((int)a.x - 2, (int)a.y - 2, 4, 4);
		g.fillRect((int)b.x - 2, (int)b.y - 2, 4, 4);
		g.fillRect((int)c.x - 2, (int)c.y - 2, 4, 4);
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
		
		if(m.distance(a) < dist) {
			dist = m.distance(a);
			if(dist < 4)
				sel = 0;
		}
		if(m.distance(b) < dist) {
			dist = m.distance(b);
			if(dist < 4)
				sel = 1;
		}
		if(m.distance(c) < dist) {
			dist = m.distance(c);
			if(dist < 4)
				sel = 2;
		}
	}

	public void switchCoords() {
		Vector3D temp = a.copy();
		a = b.copy();
		b = c.copy();
		c = temp.copy();
		
		if(m != null) {
			m.setTriangleTextureCoords(a.divide(128), b.divide(128), c.divide(128));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		sel = -1;
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if(sel != -1) {
			if(sel == 0) {
				a = new Vector3D(me.getX(), me.getY(), 0);
				a.round();
			} else if(sel == 1) {
				b = new Vector3D(me.getX(), me.getY(), 0);
				b.round();
			} else if(sel == 2) {
				c = new Vector3D(me.getX(), me.getY(), 0);
				c.round();
			}
			
			if(m != null) {
				m.setTriangleTextureCoords(a.divide(128), b.divide(128), c.divide(128));
			}
			
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}
	
}
