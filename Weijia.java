import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Weijia extends Agent{
	
	private int dx=20;
	public Image faceImage;
	public BufferedImage face;
	
	public Weijia(double x, double y){
		super(x,y);
		try{
			faceImage=ImageIO.read(new File("pictures/face.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		face=toBufferedImage(faceImage);
	}
	
	public void transport(double x,BufferedImage image){
		if(x<0)
			x=0;
		else if(x>image.getWidth())
			x=image.getWidth()-face.getWidth();
		else
			this.x=x;
	}
	
	public void draw(Graphics g){
		g.drawImage(face, (int)x, (int)y, null);
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public void brighten(){
		for(int y=0; y<face.getHeight();y++){
			for(int x=0; x<face.getWidth();x++){
				Color color=new Color(face.getRGB(x, y));
				int red=Math.min(255, color.getRed()*5/4);
				int green=Math.min(255, color.getGreen()*5/4);
				int blue=Math.min(255, color.getBlue()*5/4);
				Color newColor=new Color(red,green,blue);
				face.setRGB(x, y, newColor.getRGB());
			}
		}
	}
	
}
