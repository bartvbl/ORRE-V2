package orre.gl.renderer;

public enum ShaderProperty {
	TEXTURE0(3), 
	TEXTURE1(40),
	
	MATERIAL_AMBIENT(19), 
	MATERIAL_DIFFUSE(20), 
	MATERIAL_SPECULAR(21), 
	MATERIAL_EMISSION(22), 
	MATERIAL_SHININESS(23), 
	
	LIGHT_POSITION(14), 
	LIGHT_DIFFUSE(15), 
	LIGHT_SPECULAR(16), 
	LIGHT_AMBIENT(17), 
	LIGHT_SPECULAR_STRENGTH(18),
	
	CAMERA_POSITION(12),

	TEXTURES_ENABLED(5), 
	
	MV_MATRIX(2), 
	MVP_MATRIX(6),
	MV_NORMAL_MATRIX(10), 
	
	ATTENUATION_CONSTANT(24),
	ATTENUATION_LINEAR(25),
	ATTENUATION_QUADRATIC(26), LIGHT_MVP(35), 
	;
	
	
	public final int uniformID;

	private ShaderProperty(int uniformID) {
		this.uniformID = uniformID;
	}
}
