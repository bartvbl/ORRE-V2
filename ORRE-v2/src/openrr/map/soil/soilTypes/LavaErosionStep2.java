package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class LavaErosionStep2 extends Soil {

	public LavaErosionStep2(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.LAVA_EROSION_STEP2, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}


}
