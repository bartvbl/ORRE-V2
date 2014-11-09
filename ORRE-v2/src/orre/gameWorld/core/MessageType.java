package orre.gameWorld.core;

import orre.ai.tasks.Action;
import orre.ai.tasks.Assignment;
import orre.ai.tasks.Task;
import orre.gui.AnimateMenuCommand;
import orre.input.InputEvent;
import orre.sceneGraph.Camera;

public enum MessageType implements EnforcedClassEnum {
	OBJECT_CONTROL_RELEASED(GraphicsObject.class), 
	OBJECT_CONTROL_GAINED(GraphicsObject.class), ASSUME_CAMERA_CONTROL(Camera.class), 
	ASSIGN_TASK(Assignment.class), 
	RUN_ACTION(Action.class), 
	INPUT_EVENT(InputEvent.class), 
	SHOW_MENU(String.class),
	HIDE_MENU(String.class),
	ANIMATE_MENU(AnimateMenuCommand.class), 
	ANIMATION_END_HANDLED(String.class), 
	ANIMATION_ENDED(Integer.class), 
	;

	public final Class<?> requiredPayloadDataType;

	private MessageType(final Class<?> dataType) {
		this.requiredPayloadDataType = dataType;
	}

	@Override
	public Class<?> getClassValue() {
		return requiredPayloadDataType;
	}

}
