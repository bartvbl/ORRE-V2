package orre.gl.shaders;

import orre.sceneGraph.ContainerNode;
import static org.lwjgl.opengl.GL20.*;

public class ShaderNode extends ContainerNode {

	private int programID;

	public ShaderNode(int programID) {
		this.programID = programID;
	}
	
	public Uniform createUniform(String variableName) {
		return new Uniform(variableName, programID);
	}

	@Override
	public void preRender() {
		glUseProgram(programID);
		ActiveShader.setActiveShader(this);
	}
	
	@Override
	public void render() {
	}
	
	@Override
	public void postRender() {
		glUseProgram(0);
		ActiveShader.setActiveShader(null);
	}
	
	@Override
	public String toString() {
		return "Shader node";
	}
}
