package openrr.map.soil;

import orre.gl.materials.Material;
import orre.gl.texture.Texture;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class MapTexture {
	public final String name;
	public final int widthInTextures;
	public final int heightInTextures;
	private PartiallyLoadableTexture partiallyLoadedTexture;
	private Texture texture;

	public MapTexture(String name, PartiallyLoadableTexture texture, int widthInTextures, int heightInTextures) {
		this.name = name;
		this.partiallyLoadedTexture = texture;
		this.widthInTextures = widthInTextures;
		this.heightInTextures = heightInTextures;
	}
	
	public void finalizeTexture() {
		this.partiallyLoadedTexture.finalizeResource();
		this.texture = this.partiallyLoadedTexture.getTexture();
	}
	
	public void bind() {
		this.texture.bind();
	}

	public Material generateTextureMaterial() {
		Material material = new Material("map texture material");
		material.setAmbientTexture(texture);
		return material;
	}
}
