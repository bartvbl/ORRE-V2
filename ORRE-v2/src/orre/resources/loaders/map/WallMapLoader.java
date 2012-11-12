package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import nu.xom.Element;
import nu.xom.Elements;

public class WallMapLoader {
	private static final int WHITE = 0xFF000000;

	public static boolean[][] loadWallMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		boolean[][] wallMap = new boolean[width][height];
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("wallMap");
		parseWallMapImage(mapFile, wallMap, wallMapElement);
		parseOverrideRules(wallMap, wallMapElement);
		return wallMap;
	}

	private static void parseWallMapImage(ZipFile mapFile, boolean[][] wallMap, Element wallMapElement) {
		String src = wallMapElement.getAttributeValue("src");
		BufferedImage wallMapImage = readImageFile(mapFile, src);
		for(int xCoord = 0; xCoord < wallMap.length; xCoord++) {
			int yCoord = 0;
			for(int j = wallMap[0].length - 1; j >= 0; j--) {
				int rgb = wallMapImage.getRGB(xCoord, j);
				boolean isWall = rgb == WHITE;
				wallMap[xCoord][yCoord] = isWall;
				yCoord++;
			}
		}
	}
	
	private static BufferedImage readImageFile(ZipFile mapFile, String src) {
		try {
			InputStream inStream = mapFile.getInputStream(mapFile.getEntry(src));
			return ImageIO.read(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void parseOverrideRules(boolean[][] wallMap, Element wallMapElement) {
		Elements rows = wallMapElement.getChildElements();
		for(int i = 0; i < rows.size(); i++) {
			Element row = rows.get(i);
			String rowValues = row.getValue();
			int yCoord = Integer.parseInt(row.getAttributeValue("yCoord"));
			if(yCoord < 0 || yCoord > wallMap[0].length){
				if(rowValues.length() == wallMap.length - 1) {
					for(int j = 0; j < wallMap.length - 1; j++) {
						char tile = rowValues.charAt(j);
						boolean isWall = !(tile == ' ');
						wallMap[j][yCoord] = isWall;
					}
				}
			}
		}
	}

}
