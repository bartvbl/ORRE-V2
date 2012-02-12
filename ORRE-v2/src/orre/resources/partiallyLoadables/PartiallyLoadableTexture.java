package orre.resources.partiallyLoadables;

import orre.gl.texture.Texture;
import orre.resources.Finalizable;
import orre.resources.ResourceCache;
import orre.resources.loaders.TextureLoader;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableTexture extends Finalizable{
	private byte[] imageData;
	private final int width;
	private final int height;
	private Texture tex;

	public PartiallyLoadableTexture(byte[] imageData, int width, int height) {
		this.imageData = imageData;
		this.width = width;
		this.height = height;
	}

	public void finalizeResource() {
		Texture tex = TextureLoader.createTexture(imageData, width, height);
		this.tex = tex;
		this.imageData = null;
	}
	
	public void addToCache()
	{
		this.destinationCache.addTexture(tex);
	}
	
	public SceneNode createSceneNode()
	{
		System.out.println("WARNING: textures are not supported for scene nodes. Maybe they will ato some point, though.");
		return null;
	}
	
	public Texture getTexture()
	{
		return this.tex;
	}

}
