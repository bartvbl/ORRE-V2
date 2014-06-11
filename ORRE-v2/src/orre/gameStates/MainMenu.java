package orre.gameStates;

import java.io.File;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.resources.UnloadedResource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.scripting.ScriptInterpreter;

public class MainMenu extends GameState {

	public MainMenu(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ScriptInterpreter interpreter) {
		super(main, eventDispatcher, cache, interpreter);
		UnloadedResource mainCache = new UnloadedResource(ResourceType.resourceList, new File("res/reslist.xml"), "mainCacheList");
		eventDispatcher.dispatchEvent(new GlobalEvent<UnloadedResource>(GlobalEventType.ENQUEUE_LOADING_ITEM, mainCache));
		
	}
	
	public void executeFrame(long frameNumber) {
		
	}

	public void set() {
		this.globalEventDispatcher.dispatchEvent(new GlobalEvent<GameStateName>(GlobalEventType.CHANGE_GAME_STATE, GameStateName.GAME_LOADING));
	}
	public void unset() {}
}
