import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Bullet extends Agent{
	
	private int dy=-8;
	private BufferedImage image;
	public Image bulletImage;
	
	public Bullet(double x, double y, BufferedImage image){
		super(x, y);
		this.image=image;
		try {
			bulletImage = ImageIO.read(new File("pictures/bullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void move(){
		y+=dy;

	}
	

	
	public void draw(Graphics g){
		g.drawImage(bulletImage, (int)x, (int)y, null);

	}
	
}
