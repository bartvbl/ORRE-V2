package orre.input.devices;

import org.lwjgl.input.Mouse;

import orre.input.CommandDispatcher;
import orre.input.InputEvent;
import orre.input.KeyType;

public class MouseDevice {

	private final CommandDispatcher dispatcher;
	private final boolean[] mouseButtonStates;
	private int mouseWheelPosition = 0;

	public MouseDevice(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
		this.mouseButtonStates = new boolean[Mouse.getButtonCount()];
	}

	public void update() {
		updateMousePosition();
		updateMouseButtons();
		updateMouseWheel();
	}

	private void updateMousePosition() {
		final double mouseDX = Mouse.getDX();
		final double mouseDY = Mouse.getDY();
		final double mouseX = Mouse.getX();
		final double mouseY = Mouse.getY();
		
		if(mouseDX != 0) {
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_X, mouseX, mouseDX));
		}
		if(mouseDY != 0) {
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_Y, mouseY, mouseDY));
		}
	}

	private void dispatchButtonChange(int buttonIndex, boolean buttonState) {
		double buttonDelta = buttonState ? 1.0 : -1.0;
		double buttonValue = buttonState ? 1.0 : 0.0;
		switch(buttonIndex) {
		case 0:
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_LEFT_BUTTON, buttonValue, buttonDelta));
			return;
		case 1:
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_RIGHT_BUTTON, buttonValue, buttonDelta));
			return;
		case 2:
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_MIDDLE_BUTTON, buttonValue, buttonDelta));
			return;
		}
	}
	
	private void updateMouseButtons() {
		while(Mouse.next()) {
			int buttonIndex = Mouse.getEventButton();
			boolean buttonState = Mouse.getEventButtonState();
			
			if(buttonIndex != -1) {
				mouseButtonStates[buttonIndex] = buttonState;
				dispatchButtonChange(buttonIndex, buttonState);
			}
			
		}
	}

	private void updateMouseWheel() {
		final int mouseWheelDelta = Mouse.getDWheel();
		if(mouseWheelDelta != 0) {
			mouseWheelPosition += mouseWheelDelta;
			dispatcher.dispatchCommand(new InputEvent(KeyType.MOUSE_WHEEL, mouseWheelPosition, mouseWheelDelta));
		}
	}
}
