package com.jantox.rvtools;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class ModelTexturer extends JFrame implements ActionListener {
	
	private JPanel view, options;
	private Canvas canvas;
	
	private ViewInstance vinstance;
	private TexturePanel tpanel;
	
	// view options
	JTextField planew, planeh;
	JTextField psnapx, psnapy;
	
	JRadioButton first;
	JRadioButton third;
	
	JButton rotate;
	
	public ModelTexturer() {
		this.createGUI();
	}
	
	public void createGUI() {
		this.setTitle("Model Texturer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(470*2+30, 540+20));
		
		this.getContentPane().setLayout(new BorderLayout());
		
		view = new JPanel();
		
		Border optb = BorderFactory.createTitledBorder("Workspace");
		
		options = new JPanel();
		options.setBorder(optb);
		options.setLayout(new BorderLayout());
		
		JPanel planepanel = new JPanel();
		
		Border planesnap = BorderFactory.createTitledBorder("Plane Snap Dimensions");
		planepanel.setBorder(planesnap);
		
		planew = new JTextField(7);
		planeh = new JTextField(7);
		
		psnapx = new JTextField(7);
		psnapy = new JTextField(7);
		
		planepanel.setLayout(new BorderLayout());
		
		JPanel a = new JPanel();
		a.add(new JLabel("Plane Width: "));
		a.add(planew);
		a.add(new JLabel("Plane Height: "));
		a.add(planeh);
		
		JPanel b = new JPanel();
		b.add(new JLabel("Plane Snap X: "));
		b.add(psnapx);
		b.add(new JLabel("Plane Snap Y: "));
		b.add(psnapy);
		
		planepanel.add(a, BorderLayout.NORTH);
		planepanel.add(b, BorderLayout.SOUTH);
		
		JPanel campanel = new JPanel();
		campanel.setLayout(new BorderLayout());
		Border camborder = BorderFactory.createTitledBorder("Camera Options");
		campanel.setBorder(camborder);
		
		first = new JRadioButton("First Person Camera");
		third = new JRadioButton("Third Person Camera");
		
		campanel.add(first, BorderLayout.NORTH);
		campanel.add(third, BorderLayout.CENTER);
		
		JPanel toplayer = new JPanel();
		toplayer.setLayout(new BorderLayout());
		
		toplayer.add(planepanel, BorderLayout.EAST);
		toplayer.add(campanel, BorderLayout.WEST);
		
		JPanel tpp = new JPanel();
		
		tpanel = new TexturePanel();
		Border tborder = BorderFactory.createTitledBorder("Texture Options");
		tpp.setBorder(tborder);
		tpp.add(tpanel);
		
		JButton rotate = new JButton("Rotate");
		rotate.setActionCommand("rotate");
		rotate.addActionListener(this);
		tpp.add(rotate);
		
		options.add(toplayer, BorderLayout.NORTH);
		options.add(tpp, BorderLayout.CENTER);
		
		this.canvas = new Canvas();
		this.canvas.setSize(new Dimension(470, 470));
		this.canvas.setVisible(true);
		
		Border viewb = BorderFactory.createTitledBorder("View");
		
		view.setBorder(viewb);
		this.view.add(canvas);
		
		this.getContentPane().add(view, BorderLayout.WEST);
		this.getContentPane().add(options, BorderLayout.CENTER);
		this.pack();
		
		JMenuBar jmb = new JMenuBar();
		
		JMenu model = new JMenu("Model");
		JMenuItem imdl = new JMenuItem("Load OBJ Model");
		imdl.setActionCommand("loadobj");
		imdl.addActionListener(this);
		imdl.setAccelerator(KeyStroke.getKeyStroke("control O"));
		
		JMenu txm = new JMenu("Texture");
		JMenuItem optx = new JMenuItem("Open PNG Texture");
		optx.setActionCommand("loadtex");
		optx.addActionListener(this);
		optx.setAccelerator(KeyStroke.getKeyStroke("control T"));
		
		model.add(imdl);
		
		txm.add(optx);
		
		jmb.add(model);
		jmb.add(txm);
		
		this.setJMenuBar(jmb);
		
		vinstance = new ViewInstance(canvas, tpanel);
		Thread t = new Thread(vinstance);
		t.start();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new ModelTexturer().setVisible(true);
	}
	
	final JFileChooser fc = new JFileChooser();

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("rotate")) {
			this.tpanel.switchCoords();
		} else {
			int returnVal = fc.showOpenDialog(this);
	
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            vinstance.loadModel(file);
	        }
		}
	}
	
}
