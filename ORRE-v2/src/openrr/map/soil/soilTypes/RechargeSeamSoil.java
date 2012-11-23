package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class RechargeSeamSoil extends Soil {

	public RechargeSeamSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.RECHARGE_SEAM, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
