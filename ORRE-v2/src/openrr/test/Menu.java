package openrr.test;

import java.util.ArrayList;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class Menu {
	
	public static int OPEN = 0;
	public static int CLOSED = 1;
	public static int OPENING = 2;
	public static int CLOSING = 3;
	
	private ArrayList<Container> itemContainers = new ArrayList<Container>(); 
	
	private Graphic background;
	
	private float z = .5f;
	
	private String align;
	
	private int frame;
	private int frameTotal;
	private int animState;
	private int animMod;
	private double  animX;
	
	public Menu(int x, int y, int[] screenSize, String bgFilePath, String bgAlign) {
		align = bgAlign;
		if (bgFilePath.equals("")) {
			background = null;
		}
		else {
			background = new Graphic(x, y, screenSize, bgFilePath, bgAlign);
		}
	}
	
	public void addContainer(Container container)
	{
		this.itemContainers.add(container);
	}
	
	public void draw() {
		if (background!=null) {
			background.draw();
		}
		for (Container container : itemContainers) {
			for (Button button : container.items) {
				button.draw();
			}
		}
	}
	
	public void move(int xShift, int yShift) {
		background.changeX(xShift);
		for (Container container : itemContainers) {
			container.changeX(xShift);
			System.out.println(container.getX());
			//container.changeX(yShift);
			for (Button button : container.items) {
				button.changeX(xShift);
				//button.changeY(yShift);
			}
		}
	}
	
	public boolean animate() {
		if (frame==-1) {
			animMod=1;
			if (animState==CLOSED) {
				animState=OPENING;
				if (align.equals("right")) {
					animMod=-1;
				}
			}
			else {
				animState=CLOSING;
				if (align.equals("left")) {
					animMod=-1;
				}
			}
			frame=0;
		}
		if (frame+1==frameTotal) {
			move((int)((animX*animMod)+((animX*animMod)*frameTotal)-((int)(animX*animMod)*frameTotal)),0);
		}
		else {
			move((int)(animX*animMod),0);
		}
		
		frame++;
		
		if (frame==frameTotal) {
			frame=-1;
			if (animState==OPENING) {
				animState=OPEN;
			}
			else {
				animState=CLOSED;
			}
			return true;
		}
		return false;
	}
	
	public void setAnimationVals(int inState, int inFrameTotal, int xMove) {
		animState = inState;
		frameTotal = inFrameTotal;
		animX = (double)xMove/(double)frameTotal;
		frame = -1;
	}
	
	public float getZ() {
		return z;
	}
	
	public void changeZ(float val) {
		z += val;
	}
	
	public ArrayList<Container> getContainers() {
		return itemContainers;
	}

}
