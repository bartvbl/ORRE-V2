package orre.sceneGraph;

import java.util.ArrayList;

import orre.gl.renderer.RenderState;

import static org.lwjgl.opengl.GL11.*;

public class ContainerNode implements SceneNode {
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	protected boolean parentHasBeenRegistered = false;
	protected String name;
	
	public ContainerNode() {
		this.name = "";
	}
	
	public ContainerNode(String name) {
		this.name = name;
	}
	
	@Override
	public float getRenderRadius()
	{
		return this.renderRadius;
	}

	@Override
	public void addChild(SceneNode node) 
	{
		this.children.add(node);
	}

	@Override
	public void removeChild(SceneNode node) 
	{
		this.children.remove(node);
	}

	@Override
	public void setVisibility(boolean isVisible) 
	{
		this.visible = isVisible;
	}

	@Override
	public void render(RenderState state) {}

	@Override
	public void destroy() {}

	@Override
	public ArrayList<SceneNode> getChildren() {
		return this.children;
	}

	public boolean hasParent() {
		return this.parentHasBeenRegistered;
	}

	@Override
	public String toString() {
		return "Container " + name;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public void preRender(RenderState state) {
		
	}

	@Override
	public void postRender(RenderState state) {
		
	}
}

