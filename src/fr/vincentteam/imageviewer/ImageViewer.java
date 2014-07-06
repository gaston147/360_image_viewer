package fr.vincentteam.imageviewer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Util;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ImageViewer {
	private boolean bContinue;
	private MovingPanel[] panels;
	private float rx, ry;
	
	public static ImageViewer instance;
	
	public void start() {
		boolean error = false;
		
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("ImageViewer");
			Display.setResizable(false);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			error = true;
		}
		if (!error) {
			panels = new MovingPanel[6];
			
			rx = 0;
			ry = 0;
			
			for (int i = 0; i < panels.length; i++) {
				panels[i] = new MovingPanel(i);
			}
			
			setEnv();
			
			bContinue = true;
			while (bContinue) {
				update();
				if (!bContinue)
					break;
				clearScreen();
				draw();
				
				Display.update();
				Display.sync(30);
			}
			Display.destroy();
		}
	}
	
	public void stop() {
		bContinue = false;
	}
	
	private void setEnv() {
		Mouse.setGrabbed(true);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(70, (float) Display.getWidth() / (float) Display.getHeight(), 1 / 1000f, 1000f);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	private void update() {
		if (Display.isCloseRequested()) {
			stop();
			return;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			stop();
			return;
		}
		rx += (float) -Mouse.getDY() / 10f;
		rx = rx > 90 ? 90 : rx < -90 ? -90 : rx;
		ry += (float) Mouse.getDX() / 10f;
		ry = ry % 360;
		for (int i = 0; i < panels.length; i++) {
			panels[i].update();
		}
		Util.checkGLError();
	}
	
	private void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private void draw() {
		for (int i = 0; i < panels.length; i++) {
			glLoadIdentity();
			glRotatef(rx, 1, 0, 0);
			glRotatef(ry, 0, 1, 0);
			panels[i].draw();
		}
	}
	
	public Texture loadTexture(String format, String path) {
		try {
			return TextureLoader.getTexture(format, new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("native/" + Utils.osAsString()).getAbsolutePath());
		instance = new ImageViewer();
		instance.start();
	}
}
