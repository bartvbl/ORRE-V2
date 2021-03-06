package orre.gui.elements;

import orre.gl.texture.Texture;
import orre.gui.Bounds;
import orre.gui.elementNodes.ImageNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;
import orre.resources.ResourceType;

public class ImageElement extends GUIElement {

	private final String imageName;
	private final ImageNode node;

	public static ImageElement create(Bounds bounds, String name, String imageName) {
		ImageNode node = new ImageNode(name);
		return new ImageElement(bounds, name, imageName, node);
	}
		
	private ImageElement(Bounds bounds, String name, String imageName, ImageNode node) {
		super(bounds, node, name);
		this.imageName = imageName;
		this.node = node;
	}

	@Override
	public void initGraphics(ResourceService resourceService) {
		node.setTexture((Texture) resourceService.getResource(ResourceType.texture, imageName));
	}

}
