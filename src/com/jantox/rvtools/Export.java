package com.jantox.rvtools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class Export extends JFrame {
	
	Model model;
	String[] texs;

	public Export(Model model, String[] texs) {
		this.model = model;
		this.texs = texs;
		
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		this.setTitle("Export Options");
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("model.rtm", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < texs.length; i++) {
			writer.println("tex " + texs[i]);
		}
		
		VectorSet<Vector3D> vtx = new VectorSet<Vector3D>();
		VectorSet<Vector3D> uv = new VectorSet<Vector3D>();
		
		for(int i = 0; i < model.getPartSize(); i++) {
			Part p = model.getPart(i);
			
			ArrayList<Face> faces = p.getFaces();
			for(int x = 0; x < faces.size(); x++) {
				Face f = faces.get(x);
				
				if(f.isQuad()) {
					vtx.add(new Vector3D(f.a.x, f.a.y, f.a.z));
					vtx.add(new Vector3D(f.b.x, f.b.y, f.b.z));
					vtx.add(new Vector3D(f.c.x, f.c.y, f.c.z));
					vtx.add(new Vector3D(f.d.x, f.d.y, f.d.z));
				
					uv.add(new Vector3D(f.td.x, f.td.y, f.td.z));
				} else {
					vtx.add(new Vector3D(f.a.x, f.a.y, f.a.z));
					vtx.add(new Vector3D(f.b.x, f.b.y, f.b.z));
					vtx.add(new Vector3D(f.c.x, f.c.y, f.c.z));
				}
				if(f.ta != null) {
					uv.add(new Vector3D(f.ta.x, f.ta.y, f.ta.z));
					uv.add(new Vector3D(f.tb.x, f.tb.y, f.tb.z));
					uv.add(new Vector3D(f.tc.x, f.tc.y, f.tc.z));
				}
			}
		}
		
		for(int i = 0; i < vtx.size(); i++) {
			Vector3D a = vtx.get(i);
			writer.println("v " + (float)a.x + " " + (float)a.y + " " + (float)a.z);
		}
		
		for(int i = 0; i < uv.size(); i++) {
			Vector3D a = uv.get(i);
			writer.println("uv " + (float)a.x + " " + (float)a.y);
		}
		
		for(int i = 0; i < model.getPartSize(); i++) {
			writer.println("part" + i);
			Part p = model.getPart(i);
			
			ArrayList<Face> faces = p.getFaces();
			for(int x = 0; x < faces.size(); x++) {
				Face f = faces.get(x);
				
				if(f.ta == null) {
					if(f.isQuad()) {
						int a = vtx.indexOf(new Vector3D(f.a.x, f.a.y, f.a.z));
						int b = vtx.indexOf(new Vector3D(f.b.x, f.b.y, f.b.z));
						int c = vtx.indexOf(new Vector3D(f.c.x, f.c.y, f.c.z));
						int d = vtx.indexOf(new Vector3D(f.d.x, f.d.y, f.d.z));
						writer.println("f " + a + " " + b + " " + c + " " + d);
					} else {
						int a = vtx.indexOf(new Vector3D(f.a.x, f.a.y, f.a.z));
						int b = vtx.indexOf(new Vector3D(f.b.x, f.b.y, f.b.z));
						int c = vtx.indexOf(new Vector3D(f.c.x, f.c.y, f.c.z));
						writer.println("f " + a + " " + b + " " + c);
					}
				} else {
					if(f.isQuad()) {
						int a = vtx.indexOf(new Vector3D(f.a.x, f.a.y, f.a.z));
						int b = vtx.indexOf(new Vector3D(f.b.x, f.b.y, f.b.z));
						int c = vtx.indexOf(new Vector3D(f.c.x, f.c.y, f.c.z));
						int d = vtx.indexOf(new Vector3D(f.d.x, f.d.y, f.d.z));
						int ta = uv.indexOf(new Vector3D(f.ta.x, f.ta.y, f.ta.z));
						int tb = uv.indexOf(new Vector3D(f.tb.x, f.tb.y, f.tb.z));
						int tc = uv.indexOf(new Vector3D(f.tc.x, f.tc.y, f.tc.z));
						int td = uv.indexOf(new Vector3D(f.td.x, f.td.y, f.td.z));
						writer.println("f t " + f.tex + " " + a + " " + b + " " + c + " " + d + " " + ta + " " + tb + " " + tc + " " + td);
					} else {
						int a = vtx.indexOf(new Vector3D(f.a.x, f.a.y, f.a.z));
						int b = vtx.indexOf(new Vector3D(f.b.x, f.b.y, f.b.z));
						int c = vtx.indexOf(new Vector3D(f.c.x, f.c.y, f.c.z));
						int ta = uv.indexOf(new Vector3D(f.ta.x, f.ta.y, f.ta.z));
						int tb = uv.indexOf(new Vector3D(f.tb.x, f.tb.y, f.tb.z));
						int tc = uv.indexOf(new Vector3D(f.tc.x, f.tc.y, f.tc.z));
						writer.println("f t " + f.tex + " " + a + " " + b + " " + c + " " + ta + " " + tb + " " + tc);
					}
				}
			}
		}
		
		writer.close();
	}
	
}
