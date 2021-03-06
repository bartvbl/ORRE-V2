package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.chaining.ChainUtil;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;
import orre.gl.Shader;
import orre.gl.renderer.RenderPass;
import orre.gl.renderer.RenderState;
import orre.gl.shaders.ShaderNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;
import orre.resources.ResourceType;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneRootNode;
import orre.scripting.ScriptInterpreter;

public class GameRunning extends GameState {
	private GameWorld gameWorld;
	private ContainerNode sceneRoot;
	private ShaderNode defaultShaderNode;
	private Shader defaultShader;
	
	public GameRunning(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService, ScriptInterpreter interpreter)
	{
		super(main, eventDispatcher, resourceService, interpreter);
		
	}
	public void initialize()
	{
		
	}
	
	@Override
	public void executeFrame(long frameNumber, RenderState state) {
		this.gameWorld.tick();
		RenderPass.render(this.defaultShaderNode, state);
	}
	
	@Override
	public void set() {
		System.out.println("game has started.");
		
		this.sceneRoot = new SceneRootNode("Scene Root Node");
		
		this.defaultShader = ((Shader) resourceService.getResource(ResourceType.shader, "default"));
		defaultShaderNode = defaultShader.createSceneNode();
		defaultShaderNode.addChild(sceneRoot);
		
		this.gameWorld = new GameWorld(this.resourceService, interpreter, sceneRoot);
		
		ChainUtil.init(gameWorld);

		// Default gameObjects provided by the engine
		gameWorld.spawnGameObject(GameObjectType.CAMERA_CONTROLLER);
		gameWorld.spawnGameObject(GameObjectType.DEV_TOOLS);
		gameWorld.spawnGameObject(GameObjectType.GUI);
	}
	
	@Override
	public void unset() {
		gameWorld.services.shutdown();
	}
}
