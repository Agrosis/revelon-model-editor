package com.jantox.rvtools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class TextureEditor extends JPanel implements ActionListener, ListSelectionListener, ChangeListener {
	
	private ArrayList<BufferedImage> textures;

	JList<String> data;
	DefaultListModel<String> model = new DefaultListModel<String>();
	
	TexturePanel tpanel;
	
	JButton sl, sr, su, sd;
	
	JButton rotate;
	
	JSpinner xrep, yrep;
	
	int tval = 0;
	
	public TextureEditor(Model mode, String[] texs) {
		tpanel = new TexturePanel();
		tpanel.setModel(mode);
		
		this.setLayout(new BorderLayout());
		
		textures = new ArrayList<BufferedImage>();
		for(int i = 0; i < texs.length; i++) {
			model.addElement("tex" + i);
			try {
				textures.add(ImageIO.read(new File("textures/" + texs[i])));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		data = new JList<String>(model);
		
		data.getSelectionModel().addListSelectionListener(this);
		
		JScrollPane listScroller = new JScrollPane(data);
		listScroller.setPreferredSize(new Dimension(75, 256));
		
		JPanel left = new JPanel();
		left.add(listScroller);
		
		JPanel rest = new JPanel();
		rest.add(tpanel);
		
		this.add(left, BorderLayout.WEST);
		
		rotate = new JButton("Rotate");
		rotate.setActionCommand("rotate");
		rotate.addActionListener(this);
		
		JPanel ropt = new JPanel();
		ropt.setLayout(new BoxLayout(ropt, BoxLayout.Y_AXIS));
		
		sl = new JButton("Shift Left");
		sl.addActionListener(this);
		su = new JButton("Shift Up");
		su.addActionListener(this);
		sd = new JButton("Shift Down");
		sd.addActionListener(this);
		sr = new JButton("Shift Right");
		sr.addActionListener(this);
		
		ropt.add(rotate);
		ropt.add(sr);
		ropt.add(sl);
		ropt.add(sd);
		ropt.add(su);
		ropt.add(new JCheckBox("Lock Coords"));
		
		rest.add(ropt);
		
		xrep = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		xrep.addChangeListener(this);
		yrep = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		yrep.addChangeListener(this);
		
		JPanel texop = new JPanel();
		texop.add(xrep);
		texop.add(yrep);
		
		this.add(rest, BorderLayout.CENTER);
		this.add(texop, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("rotate")) {
			if(tpanel.texs != null)
				this.tpanel.switchCoords();
		} else if(ae.getSource().equals(sr)) {
			tpanel.shift(1);
		} else if(ae.getSource().equals(sd)) {
			tpanel.shift(2);
		} else if(ae.getSource().equals(su)) {
			tpanel.shift(0);
		} else if(ae.getSource().equals(sl)) {
			tpanel.shift(3);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		int val = data.getSelectedIndex();
		tpanel.setTexture(textures.get(val), val + 1);
	}

	@Override
	public void stateChanged(ChangeEvent ce) {
		this.tpanel.setTextureRepeat((Integer)xrep.getValue(), (Integer)yrep.getValue());
	}
	
}
