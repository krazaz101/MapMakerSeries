package main.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import main.components.ProjectHandler;
import main.graphics.CustomGraphics;

/**
 * Episode 9: "MouseListener & MotionListener"  
 * 
 * @author krazaz
 *
 */
public class Engine extends Canvas implements Runnable{
 
	public static final int WIDTH = 1688,
					  HEIGHT = 911;
	
	private boolean isActive;
	
	
	private String stage = "ImageLoader Class Ep.6";
	private String version = "version 1.0";
	private String title_version = "Map Maker " + version + " - " + stage;
	private String frame_title = title_version;
	
	private JFrame frame;
	private Container container;
	
	private Thread thread;
	
	//ADDED : episode 2
	private CustomGraphics cGraphics;
	private BufferStrategy bStrategy;
	
	
	//ADDED : episode 3
	private ProjectHandler handler; 

	public Engine() {
	
		setup();
		
		initialize();
	}
	
	private void setup() {
		frame = new JFrame(frame_title);
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = new Container();
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		 
		container.setBackground(Color.BLACK);
		container.add(this);
		
		//ADDED : episode 2
		addMouseListener(new MouseInput());
		addMouseMotionListener(new MouseMove());
					 
		
		frame.pack();
		
		//ADDED : episode 2
		createBufferStrategy(3);
		
	}
	//ADDED : episode 3
	private void initialize() {
		cGraphics = new CustomGraphics(getWidth(), getHeight());
		
		handler = new ProjectHandler(this);
		
	}
	//ADDED : episode 2
	private void render() {
		bStrategy = getBufferStrategy();
		Graphics g = bStrategy.getDrawGraphics();
		super.paint(g);
		
		//*******************************
		
		
		//cGraphics.test(g);
		//ADDED : episode 3
		handler.render(cGraphics);
		
		cGraphics.render(g);
		
		//*******************************
		
		g.dispose();
		bStrategy.show();
		 
		
		
		
	}
	
	public void run() {
		 
		while(isActive) {
			render();
			//System.out.println("inside run method");
		}
		
	}
	
	private synchronized void start() {
		
		if(isActive) return;
		
		isActive = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public static void main(String[] args) {
		  
		new Engine().start();
		
		//test the frame
		//new Engine().setup();
	}

	//Inner private class
	//ADDED : episode 9
	private class MouseInput extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			 
			handler.getComponentInitializer().getComponentHandler().mousePressed(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			handler.getComponentInitializer().getComponentHandler().mouseReleased(e);
		}
		
		public void mouseClicked(MouseEvent e) {
			handler.getComponentInitializer().getComponentHandler().mouseClicked(e);
		}
	}
	
	private class MouseMove extends MouseAdapter{
		
		public void mouseMoved(MouseEvent e) {
			try {
				handler.getComponentInitializer().getComponentHandler().mouseMoved(e);
			}catch(Exception error) {}
		}
		
		public void mouseDragged(MouseEvent e) {
			handler.getComponentInitializer().getComponentHandler().mouseDragged(e);
		}
	}
	
	
}
