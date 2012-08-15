package orre.resources.partiallyLoadables;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.StoredModelPart;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableModelPart extends Finalizable {
	private BlueprintMaterial material;
	private final UnpackedGeometryBuffer geometryBuffer;
	
	public final String name;
	private StoredModelPart destinationPart;

	public PartiallyLoadableModelPart(String name, int numVertices, BufferDataFormatType bufferDataFormatType) {
		System.out.println("creating model part: " + name);
		this.geometryBuffer = new UnpackedGeometryBuffer(bufferDataFormatType, numVertices);
		this.name = name;
	}
		
	public void setMaterial(BlueprintMaterial newMaterial) {
		this.material = newMaterial;
	}
	public void addVertex(float[] vertex) {
		this.geometryBuffer.addVertex(vertex); 
	}
	
	public void finalizeResource() {
		
		this.material.finalizeResource();
		this.geometryBuffer.finalizeResource();
		this.destinationPart.addBufferCombo(this.material, this.geometryBuffer.convertToGeometryBuffer());
	}

	public SceneNode createSceneNode() {return null;}
	public void addToCache() {}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		geometryBuffer.setBufferDataFormat(dataType);
	}

	public void setDestinationPart(StoredModelPart part) {
		System.out.println("destination part: " + part.name);
		this.destinationPart = part;
	}

}
