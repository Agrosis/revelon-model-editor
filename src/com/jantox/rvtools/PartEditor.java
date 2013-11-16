package com.jantox.rvtools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class PartEditor extends JPanel implements ActionListener, ListSelectionListener {

	JList<String> data;
	DefaultListModel<String> model = new DefaultListModel<String>();
	JButton newpart, delpart, copypart, renamepart;
	JButton simplify, restex;
	
	JCheckBox visible;
	
	JTextField tx, ty, tz;
	JTextField rx, ry, rz;
	
	int part = 0;
	
	private Model m;
	
	public PartEditor(Model mode) {
		this.m = mode;
		
		this.setLayout(new BorderLayout());
		
		data = new JList<String>(model);
		
		data.getSelectionModel().addListSelectionListener(this);
		
		newpart = new JButton("New");
		newpart.addActionListener(this);
		delpart = new JButton("Remove");
		delpart.addActionListener(this);
		copypart = new JButton("Copy");
		renamepart = new JButton("Rename");
		renamepart.addActionListener(this);
		
		JPanel tpbt = new JPanel();
		tpbt.add(newpart);
		tpbt.add(delpart);
		
		JPanel pbt = new JPanel();
		pbt.add(copypart);
		pbt.add(renamepart);
		
		JScrollPane listScroller = new JScrollPane(data);
		listScroller.setPreferredSize(new Dimension(150, 300));
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		
		left.add(tpbt, BorderLayout.NORTH);
		left.add(listScroller, BorderLayout.CENTER);
		left.add(pbt, BorderLayout.SOUTH);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		
		visible = new JCheckBox("Visible");
		visible.setSelected(true);
		visible.addActionListener(this);
		
		simplify = new JButton("Simplify PART");
		restex = new JButton("Reset Options");
		
		JPanel options = new JPanel();
		Border b = BorderFactory.createTitledBorder("PART Options");
		options.setBorder(b);
		options.add(visible);
		options.add(simplify);
		options.add(restex);
		
		JPanel initial = new JPanel();
		initial.setLayout(new BorderLayout());
		
		JPanel tr = new JPanel();
		Border trb = BorderFactory.createTitledBorder("Translation");
		tr.setBorder(trb);
		
		tx = new JTextField(8);
		ty = new JTextField(8);
		tz = new JTextField(8);
		tx.setText("0");
		ty.setText("0");
		tz.setText("0");
		
		tr.add(new JLabel("X:"));
		tr.add(tx);
		tr.add(new JLabel("Y:"));
		tr.add(ty);
		tr.add(new JLabel("Z:"));
		tr.add(tz);
		
		JPanel rr = new JPanel();
		Border rrb = BorderFactory.createTitledBorder("Rotation");
		rr.setBorder(rrb);
		
		rx = new JTextField(8);
		ry = new JTextField(8);
		rz = new JTextField(8);
		rx.setText("0");
		ry.setText("0");
		rz.setText("0");
		
		rr.add(new JLabel("X:"));
		rr.add(rx);
		rr.add(new JLabel("Y:"));
		rr.add(ry);
		rr.add(new JLabel("Z:"));
		rr.add(rz);
		
		initial.add(tr, BorderLayout.NORTH);
		initial.add(rr, BorderLayout.CENTER);
		
		JPanel ti = new JPanel();
		ti.add(initial);
		
		center.add(options, BorderLayout.NORTH);
		center.add(ti, BorderLayout.CENTER);
		
		main.add(left, BorderLayout.WEST);
		main.add(center, BorderLayout.CENTER);
		
		this.add(main, BorderLayout.WEST);
	}
	
	final JFileChooser fc = new JFileChooser();

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(renamepart)) {
			if(data.getSelectedValue() != null) {
				String s = (String) data.getSelectedValue();
			}
		} else if(ae.getSource().equals(newpart)) {
			int returnVal = fc.showOpenDialog(this);
			
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            
	            this.newPart(Model.loadPart(file));
	        }
		} else if(ae.getSource().equals(delpart)) {
			if(data.getSelectedValue() != null) {
				m.remove(data.getSelectedIndex());
				model.remove(data.getSelectedIndex());
				data.setSelectedIndex(0);
			}
		} else if(ae.getSource().equals(visible)) {
			if(data.getSelectedValue() != null) {
				m.visibility(data.getSelectedIndex());
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		int val = data.getSelectedIndex();
		
		if(model.getSize() > 0) {
			Part p = m.getPart(val);
			this.visible.setSelected(p.isVisible());
		}
	}
	
	public void newPart(Part p) {
		m.addPart(p);
		model.addElement("part" + (part++));
	}
	
}
