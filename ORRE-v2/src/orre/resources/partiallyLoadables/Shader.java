package orre.resources.partiallyLoadables;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import orre.gl.shaders.ShaderNode;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceType;
import orre.sceneGraph.SceneNode;

public class Shader implements Finalizable {

	private final String name;
	private final String vertSource;
	private final String fragSource;
	private int programID = -1;

	public Shader(String name, String vertSource, String fragSource) {
		this.name = name;
		this.vertSource = vertSource;
		this.fragSource = fragSource;
	}

	@Override
	public Resource finalizeResource() {
		this.compile();
		return new Resource(ResourceType.shader, this.name, Shader.class, this);
	}
	
	private int createShader(String vertexSource, int type) {
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, vertexSource);
		glCompileShader(shaderID);
		String infoLog = glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH));
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new RuntimeException("Shader compilation failed: " + infoLog);
		}
		return shaderID;
	}

	private void compile() {
		int vertexShaderID = createShader(vertSource, GL_VERTEX_SHADER);
		int fragmentShaderID = createShader(fragSource, GL_FRAGMENT_SHADER);
		int programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		glLinkProgram(programID);
		String infoLog = glGetProgramInfoLog(programID, glGetProgrami(programID, GL_INFO_LOG_LENGTH));
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
			throw new RuntimeException("Failed to link shader: " + infoLog);
		}
		this.programID = programID;
	}
	
	public ShaderNode createSceneNode() {
		return new ShaderNode(programID);
	}

}