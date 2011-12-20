package orre.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class RenderUtils {
	public static final float NEAR_POINT = 0.1f;
	public static final float FAR_POINT = 10000f;
	
	public static void newFrame()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static void set3DMode(float width, float height)
	{
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, (width/height), RenderUtils.NEAR_POINT, RenderUtils.FAR_POINT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public void set2DMode(float width, float height)
	{
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, width, 0, height);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	
}