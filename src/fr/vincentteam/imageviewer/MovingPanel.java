package fr.vincentteam.imageviewer;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

public class MovingPanel {
	private float px, py, pz;
	private float width, height;
	private Texture tex;
	private float position;
	
	public MovingPanel(int position) {
		tex = ImageViewer.instance.loadTexture("PNG", "image/000" + position + ".png");
		width = 512f;
		height = 512f;
		px = -256f;
		py = -256f;
		pz = 256f;
		this.position = position;
		glGenTextures();
	}
	
	public void update() {
	}
	
	public void draw() {
		tex.bind();
		if (position < 4)
			glRotatef(90 * position, 0, 1, 0);
		else {
			if (position == 4) {
				glRotatef(-90, 0, 1, 0);
				glRotatef(-90, 1, 0, 0);
			} else {
				glRotatef(90, 0, 1, 0);
				glRotatef(90, 1, 0, 0);
			}
			glRotatef(90, 0, 0, 1);
		}
		glTranslatef(px, py, -pz);
		glBegin(GL_QUADS);
		glTexCoord2f(0.25f, 0.50f); glVertex3f(0, 0, 0);
		glTexCoord2f(0.50f, 0.50f); glVertex3f(width, 0, 0);
		glTexCoord2f(0.50f, 0.25f); glVertex3f(width, height, 0);
		glTexCoord2f(0.25f, 0.25f); glVertex3f(0, height, 0);
		glEnd();
	}
}
