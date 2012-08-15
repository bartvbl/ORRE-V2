package orre.resources.partiallyLoadables;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.GeometryBufferGenerator;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private float[] vertices;
	private BufferDataFormatType dataFormat;
	private int numVertices;
	private int bufferPosition = 0;
	private int numIndicesPerVertex;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = new float[vertexBufferSize];
		this.numIndicesPerVertex = bufferDataFormat.elementsPerVertex;
	}
	
	public void addVertex(float[] vertex) {
		for(int i = 0; i < numIndicesPerVertex; i++) {			
			this.vertices[bufferPosition + i] = vertex[i];
		}
		this.bufferPosition += numIndicesPerVertex;
	}

	public void finalizeResource() {}

	@Override
	public SceneNode createSceneNode() {
		return null;
	}

	public void addToCache() {}

	public float[] getVertices() {
		return this.vertices;
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		System.out.println("UPDATING DATA TYPE");
		this.dataFormat = dataType;
	}

	public GeometryBuffer convertToGeometryBuffer() {
		GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, this.vertices, this.numVertices);
		this.vertices = null;
		return null;
	}

	public int getVertexCount() {
		return this.numVertices;
	}

}