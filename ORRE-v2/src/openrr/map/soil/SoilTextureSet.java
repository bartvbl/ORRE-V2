package openrr.map.soil;

import orre.gl.texture.Texture;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class SoilTextureSet {
	public final String type;
	private SoilTextureType[] soilTextureTypes;
	private PartiallyLoadableTexture[] partiallyLoadableTextures;
	private Texture[] textures;
	private boolean compiled = false;

	public SoilTextureSet(String type) {
		this.type = type;
		this.soilTextureTypes = SoilTextureType.values();
		this.partiallyLoadableTextures = new PartiallyLoadableTexture[soilTextureTypes.length];
		this.textures = new Texture[soilTextureTypes.length];
	}
	
	public void bindTexture(SoilTextureType type) {
		textures[indexOf(type)].bind();
	}
	
	public void setTexture(SoilTextureType type, PartiallyLoadableTexture texture) {
		partiallyLoadableTextures[indexOf(type)] = texture;
	}
	
	public void finalizeTextures() {
		if(compiled) return;
		for(int i = 0; i < partiallyLoadableTextures.length; i++) {
			partiallyLoadableTextures[i].finalizeResource();
			textures[i] = partiallyLoadableTextures[i].getTexture();
		}
		partiallyLoadableTextures = null;
		compiled = true;
	}
	
	public SoilTextureSet clone(String typeName) {
		SoilTextureSet newSet = new SoilTextureSet(typeName);
		for(int i = 0; i < soilTextureTypes.length; i++) {
			newSet.setTexture(soilTextureTypes[i], partiallyLoadableTextures[i]);
		}
		return newSet;
	}
	
	private int indexOf(SoilTextureType type) {
		for(int i = 0; i < soilTextureTypes.length; i++) {
			if(type == soilTextureTypes[i]) return i;
		}
		return -1;
	}
}