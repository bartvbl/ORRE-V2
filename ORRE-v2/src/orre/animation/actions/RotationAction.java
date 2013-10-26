package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;

public class RotationAction extends AnimationAction {

	private final String partName;
	private final double rotation;
	private final Axis axis;

	public RotationAction(String partName, double rotation, Axis axis) {
		super(AnimationActionType.ROTATE);
		this.partName = partName;
		this.rotation = rotation;
		this.axis = axis;
	}

	public void update(Mesh3D target, double percentElapsed, double timeSinceLastUpdate) {
		
	}

}