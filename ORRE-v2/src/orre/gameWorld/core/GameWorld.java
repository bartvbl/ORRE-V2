package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import orre.api.PropertyTypeProvider;
import orre.gameWorld.services.WorldServices;
import orre.resources.ResourceService;
import orre.sceneGraph.ContainerNode;
import orre.scripting.ScriptInterpreter;
import orre.util.ArrayUtils;
import orre.util.Queue;

public class GameWorld {
	public final WorldServices services;
	public final ResourceService resourceService;
	public final ContainerNode sceneRoot;

	private final HashMap<Integer, GameObject> gameObjectSet;
	private final HashMap<Enum<?>, int[]> propertyMap;
	private final HashMap<Enum<?>, int[]> objectTypeMap;
	private final HashMap<MessageType, ArrayList<MessageHandler>> messageListeners;
	private final Queue<Integer> despawnQueue;
	
	public GameWorld(ResourceService resourceService, ScriptInterpreter interpreter, ContainerNode rootNode) {
		this.sceneRoot = rootNode;
		this.services = new WorldServices(this, interpreter);
		this.gameObjectSet = new HashMap<Integer, GameObject>();
		this.messageListeners = new HashMap<MessageType, ArrayList<MessageHandler>>();
		this.propertyMap = new HashMap<Enum<?>, int[]>();
		this.objectTypeMap = new HashMap<Enum<?>, int[]>();
		this.despawnQueue = new Queue<Integer>();
		this.resourceService = resourceService;
	}
	
	public int spawnGameObject(Enum<?> gameObjectType) {
		GameObject object = GameObjectBuilder.buildGameObjectByType(gameObjectType, this);
		registerGameObject(object);
		return object.id;
	}

	private void registerGameObject(GameObject object) {
		gameObjectSet.put(object.id, object);
		Enum<?>[] properties = object.getAttachedPropertyTypes();
		for(Enum<?> propertyType : properties) {
			if(!propertyMap.containsKey(propertyType)) {
				propertyMap.put(propertyType, new int[]{object.id});
			} else {
				int[] idArray = propertyMap.get(propertyType);
				int[] appendedArray = ArrayUtils.append(idArray, object.id);
				propertyMap.put(propertyType, appendedArray);
			}
		}
		if(!objectTypeMap.containsKey(object.type)) {
			objectTypeMap.put(object.type, new int[]{object.id});
		} else {
			int[] idArray = objectTypeMap.get(object.type);
			int[] appendedArray = ArrayUtils.append(idArray, object.id);
			objectTypeMap.put(object.type, appendedArray);
		}
	}
	
	public void despawnObject(int objectID) {
		despawnQueue.enqueue(objectID);
	}
	
	public int[] getAllGameObjectsByType(Enum<?> type) {
		ArrayList<Integer> objectIDs = new ArrayList<Integer>();
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject object : gameObjects) {
			if(object.type == type) {
				objectIDs.add(object.id);
			}
		}
		Integer[] idList = objectIDs.toArray(new Integer[objectIDs.size()]);
		int[] outputList = new int[idList.length];
		for(int i = 0; i < idList.length; i++) {
			outputList[i] = idList[i].intValue();
		}
		return outputList;
	}
	
	public void dispatchMessage(Message<?> message) {
		ArrayList<MessageHandler> listenerList = messageListeners.get(message.type);
		if(listenerList == null) {
			return;
		}
		for(MessageHandler listener : listenerList) {
			listener.handleMessage(message);
		}
	}
	
	public void dispatchMessage(Message<?> message, int destinationObject) {
		GameObject object = gameObjectSet.get(destinationObject);
		if(object != null) {
			object.handleMessage(message);
		} else {
			System.err.println("Attempted to send message to object " + destinationObject + ", which no longer exists.");
			System.err.println("Sent message was of type " + message.type);
		}
	}
	
	public void addMessageListener(MessageType type, MessageHandler listener) {
		if(!messageListeners.containsKey(type)) {
			ArrayList<MessageHandler> objectList = new ArrayList<MessageHandler>();
			messageListeners.put(type, objectList);
		}
		ArrayList<MessageHandler> listenerList = messageListeners.get(type);
		listenerList.add(listener);
	}

	public void tick() {
		this.services.tickServices();
		Collection<GameObject> gameObjects = gameObjectSet.values();
		for(GameObject gameObject : gameObjects) {
			gameObject.tick();
		}
		while(!despawnQueue.isEmpty()) {
			int objectID = despawnQueue.dequeue();
			GameObject object = gameObjectSet.remove(objectID);
			if(object != null) {
				System.out.println("Destroying object of type " + object.type);
				object.destroy();
			}
		}
	}
	
	public Object requestPropertyData(int target, Enum<?> propertyDataType, Object defaultValue, Class<?> expectedDataType) {
		try {
			GameObject targetObject = this.gameObjectSet.get(target);
			Object returnedData = targetObject.requestPropertyData(propertyDataType, expectedDataType);
			if(returnedData == null) {
				return defaultValue;
			}
			return returnedData;
		}
		catch(Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public Collection<GameObject> debugonly_getAllGameOjects() {
		return gameObjectSet.values();
	}

	public static void setPropertyTypeProvider(PropertyTypeProvider provider) {
		GameObjectBuilder.setPropertyTypeProvider(provider);
	}

	public int api_spawnGameObjectFromString(String gameObjectType) {
		Enum<?> type = GameObjectBuilder.getGameObjectTypeFromString(gameObjectType);
		return spawnGameObject(type);
	}

	public int getOnlyGameObject(Enum<?> gameObjectType) {
		return objectTypeMap.get(gameObjectType)[0];
	}

	public void removeMessageListener(MessageType type, MessageHandler listener) {
		if(!messageListeners.containsKey(type)) {
			return;
		}
		ArrayList<MessageHandler> listenerList = messageListeners.get(type);
		listenerList.remove(listener);
	}
}
