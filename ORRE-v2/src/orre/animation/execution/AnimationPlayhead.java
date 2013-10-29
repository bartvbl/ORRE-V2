package orre.animation.execution;

import org.lwjgl.util.Timer;

import orre.animation.Animation;
import orre.geom.mesh.Mesh3D;

public class AnimationPlayhead {
	private final Animation animation;
	private final Timer timer;
	private final Mesh3D target;
	private float previousFrameStartTime = 0;
	private float keyFrameStartTime = 0;
	private int currentKeyFrame = 0;

	public AnimationPlayhead(Animation animation, Mesh3D target) {
		this.animation = animation;
		this.timer = new Timer();
		this.target = target;
	}
	
	public void updateAnimation() {
		float currentTime = timer.getTime();
		double elapsedTime = currentTime - previousFrameStartTime;
		animation.keyFrames[currentKeyFrame].update(target, elapsedTime);
		previousFrameStartTime = currentTime;
		
	}
	
	public void reset() {
		this.timer.reset();
	}

	public boolean isFinished() {
		return false;
	}

}
