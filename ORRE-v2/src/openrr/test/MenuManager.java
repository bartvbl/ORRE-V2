package openrr.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


import org.dom4j.Node;

import orre.core.GameWindow;
import orre.util.XMLDocument;

import openrr.test.Image;


public class MenuManager {
	
	ArrayList<Menu> menus = new ArrayList<Menu>();
	ArrayList<Menu> activeMenus = new ArrayList<Menu>();
	ArrayList<Menu> inactiveMenus = new ArrayList<Menu>();
	EventDispatcher guiEventManager;
	ArrayList<GUIElement> children  = new ArrayList<GUIElement>();
	HoverText x;
	
	public MenuManager(int[] screenSize) {
		guiEventManager = new EventDispatcher();
		Menu testFrame = new Menu (new int[] {0,0,60,184});
		testFrame.addChild(new Image(new int[] {0,0,60,184}, "res/images/menus/slot4wo.png", null));
		ImageButton b = new ImageButton(new int[] {10,15,40,40}, "n", guiEventManager, "res/images/menus/main/raider.bmp", "res/images/menus/hover.png", "Teleport Rock Raider to Planet", "TELEPORT_ROCK_RAIDER", testFrame);
		//b.
		testFrame.addChild(b);
		testFrame.addChild(new ImageButton(new int[] {10,55,40,40}, "n", guiEventManager, "res/images/menus/main/building.bmp", "res/images/menus/hover.png", "Buildings", "BUILDINGS", testFrame));
		testFrame.addChild(new ImageButton(new int[] {10,95,40,40}, "n", guiEventManager, "res/images/menus/main/svehicle.bmp", "res/images/menus/hover.png", "Small Vehicles", "SMALL_VEHICLES", testFrame));
		testFrame.addChild(new ImageButton(new int[] {10,135,40,40}, "n", guiEventManager, "res/images/menus/main/lvehicle.bmp", "res/images/menus/hover.png", "Large Vehicles", "LARGE_VEHICLES", testFrame));
		children.add(testFrame);
		//x = new HoverText(new int[] {30,300,0,0}, "Ice Monster", "Arial", new Color(255, 255, 255), 12, null);
	}
	
	
	public void draw() {
		for (GUIElement child : children) {
			if (child instanceof DrawableElement) {
				((DrawableElement) child).draw();
			}
		}
		//x.draw();
	}
	
	public EventDispatcher getDispatcher() {
		return guiEventManager;
	}
	
	public Button getButton(int[] coords) {
		for (GUIElement child : children) {
			if (child instanceof Button && child.inCoords(coords)) {
				return (Button) child;
			}
			else {
				if (child instanceof Frame && child.inCoords(coords)) {
					GUIElement frameChild = ((Frame) child).findChild(coords);
					while (frameChild instanceof Frame) {
						frameChild = ((Frame) frameChild).findChild(coords);
					}
					if (frameChild!=null && frameChild instanceof Button) {
						return (Button) frameChild;
					}
					else {
						return null;
					}
				}
			}
		}
		return null;
	}

	public void loadGUI(int[] screenSize) {
		XMLDocument doc = new XMLDocument("res/defaultGUI.xml");
		this.menus = MenuDefinitionFileLoader.getMenuNodes(doc, screenSize);
		this.activeMenus.add(menus.get(1));
		this.activeMenus.add(menus.get(2));
	}
	
	public void loadButtonEvents() {
		

	}


}
