import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

import java.io.*;


public class Background extends DrawingFrame{
	
	private int lives=3;
	private static final int numIce=2;
	private ArrayList<Ice> ice;
	private Weijia john;
	private Image gameOver;
	private double seconds;
	private ArrayList<Bullet> bullet;
	
	public Background(String filename, final Controller controller){
		super("Dartmouth Winter", filename);
		
		ice=new ArrayList<Ice>();
		bullet=new ArrayList<Bullet>();
		
		try{
			gameOver=ImageIO.read(new File("pictures/gameOver.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		john=new Weijia(image.getWidth()/2,image.getHeight()-72);

			

		Timer timer1 = new Timer(2000, new AbstractAction("update"){
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<numIce; i++){
					double x = (Math.random()*image.getWidth())-15;
					ice.add(new Ice(x,0,image));
				}
			}
		});
		
		timer1.start();
		
		Timer timer = new Timer(100, new AbstractAction("update") {
			public void actionPerformed(ActionEvent e) {
				for (Ice agent : ice) {
						agent.move();
				}
				int flag=0;
				Bullet removed=null;
				for(Bullet b:bullet){
					if(b.y<0){
						flag=1;
						removed=b;
						break;
					}
					else
						b.move();
				}
				if(flag==1){
					bullet.remove(removed);
				}
				checkHit();
				draw(canvas.getGraphics());
				// Need to redraw.
				canvas.repaint();
				if(checkOverlap()&&lives==0){
					canvas.getGraphics().drawImage(gameOver,0,0,null);
					try{Thread.sleep(10000);
					}catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					System.exit(0);
				}
				InteractionBox box = controller.frame().interactionBox();
//				Vector palmPosition = controller.frame().hand(controller.frame().hands().count()-1).palmPosition();
//				double myposition = palmPosition.getX()*400/box.width()+400;	
				Hand firstHand = controller.frame().hands().get(0);
				double myposition = firstHand.palmPosition().getX()*400/box.width()+400;
				john.transport(myposition,image);

				if(controller.frame().gestures().count() > 0){
					
					Gesture gesture = controller.frame().gestures().get(controller.frame().gestures().count()-1);
							
					switch (gesture.type()) {
						case TYPE_SWIPE:
//							SwipeGesture swipe = new SwipeGesture(gesture);
//							Vector swipeposition= swipe.position();
//				
						case TYPE_KEY_TAP:
							if(bullet.size()<5)
							bullet.add(new Bullet(myposition, image.getHeight()-150, image));
					}
				}
				else {
					System.out.println("count is 0");		
				}		
//				System.out.println(box.width());
				
			}
		});
		timer.start();

	}
	private void addText(Graphics g){
		seconds+=0.1;
		g.setColor(new Color(255, 0, 0));
		g.setFont(new Font( "Blackadder ITC", Font. BOLD, 30));
		g.drawString(Integer.toString((int)seconds),750,40);
	}
	
	private boolean checkOverlap(){
		for (Agent agent : ice) {
			double x1 = agent.x;
			double y1 = agent.y;
			double x2 = john.x;
			double y2 = john.y;
			
			double iceWidth = ((Ice)agent).icicleImage.getWidth(null);
			double iceHeight = ((Ice)agent).icicleImage.getHeight(null);
			double faceWidth = john.faceImage.getWidth(null);
			double faceHeight = john.faceImage.getHeight(null);
			
			if ((y2 <= y1+iceHeight) && ((x1 <= x2)&&(x2 <= x1+iceWidth)||(x1 <= x2+faceWidth)&&(x2+faceWidth<=x1+iceWidth)||(x1>=x2)&&(x1+iceWidth<=x2+faceWidth)))  {
				lives--;
				john.brighten();
				((Ice)agent).reset();
				return true;
			}
		}
		return false;
		
	}
	
	private void checkHit(){
		
		int flag=0;
		Ice removed=null;
		for(Bullet a:bullet){
			double x2 = a.x;
			double y2 = a.y;
			double bulletWidth = a.bulletImage.getWidth(null);
			double bulletHeight = a.bulletImage.getWidth(null);
			for(Ice b:ice){
				double x1 = b.x;
				double y1 = b.y;
				double iceWidth = b.icicleImage.getWidth(null);
				double iceHeight = b.icicleImage.getHeight(null);
				if ((y2 <= y1+iceHeight) && ((x1 <= x2)&&(x2 <= x1+iceWidth)||(x1 <= x2+bulletWidth)&&(x2+bulletWidth<=x1+iceWidth)||(x1>=x2)&&(x1+iceWidth<=x2+bulletWidth))){
					
					flag=1;
					removed=b;
					break;

				}
			}
			if(flag==1){
				ice.remove(removed);
				break;
			}
		}
	}
	
	
	
	public void draw(Graphics g){
		super.draw(g);
		john.draw(g);
		for(Agent agent:ice)
			agent.draw(g);
		for(Bullet b:bullet)
			b.draw(g);
		addText(g);
	}
	
	
	/**
	 * Main method for the application
	 * @param args		command-line arguments (ignored)
	 */
	public static void main(String[] args) {
		LeapListener listener = new LeapListener();
		final Controller controller = new Controller();
		controller.addListener(listener);
		
		try{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Background("pictures/baker2.jpg", controller);
				}
			});
			System.in.read();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		controller.removeListener(listener);

	}
}
