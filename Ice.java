import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Ice extends Agent{
	
	private int dy=8;
	private BufferedImage image;
	public Image icicleImage;
	
	public Ice(double x, double y, BufferedImage image){
		super(x, y);
		this.image=image;
		try {
			icicleImage = ImageIO.read(new File("pictures/icicle2.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void move(){
		y+=dy;
		if(y>image.getHeight()){
			reset();
		}
	}
	
	public void reset(){
		y=0;
		x=(Math.random()*image.getWidth())-15;
	}
	
	public void draw(Graphics g){
		g.drawImage(icicleImage, (int)x, (int)y, null);

	}
	
}
