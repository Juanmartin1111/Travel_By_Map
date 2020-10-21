/*
 Name: Juan Ramirez
 Class: Video Game Programming 1
 Professor: Raymond Klump
 */

package edu.lewisu.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class TravelByMap extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tex, img2, background1, background2;
	TextureRegion img;
	OrthographicCamera cam;
	int WIDTH;
	int HEIGHT;
	/*
	 the state of the game is the data that it manages.
	 that data controls game play and what appears on the screen.
	 in this game, the player is the picture.
	 we need to manage x and y position and the rotation
	 */
	int imgX, imgY; // state variables associated with the location
	int imgWidth, imgHeight;
	int imgAngle;

	private float backgroundSpeed = 1;
	private float backgroundX = 0;
	private float displayHeight;
	private float displayWidth;

	@Override
	public void create () {
		batch = new SpriteBatch();
		displayHeight = Gdx.graphics.getHeight();
		displayWidth = Gdx.graphics.getWidth();
		background1 = new Texture("underCity.jpg");
		background2 = new Texture("underCity.jpg");	
		img2 = new Texture("underEarth.jpg");
		tex = new Texture("earth.jpg");
		img = new TextureRegion(tex); // gives rotation abilities to the image we loaded in
		imgWidth = tex.getWidth();
		imgHeight = tex.getHeight();
		imgAngle = 0;
		imgX = 175;
		imgY = 175;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2); //move the image to the screen
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}

	/**
	 * Arrows: UP, DOWN, LEFT, RIGHT - move the camera
	 * if shift+UP - zoom in
	 * shift+DOWN - zoom out
	 * if shift+LEFT or shift+RIGHT - rotate
	 * ESCAPE - quit the program
	 * keys: A,S,W,D - Control the image
	 * shift + W and S -> rotate the image
	 * SPACE: will move the image in the direction that is looking
	 */
	public void handleInput() {
		boolean shiftHeld = false;
		boolean cameraNeedsUpdating = false;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT)) {
			shiftHeld = true;
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			if (shiftHeld) {
				//zoom
				cam.zoom -= 0.01;
			} else {
				cam.translate(0,1);
			}
			cameraNeedsUpdating = true;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			if (shiftHeld) {
				//zoom
				cam.zoom += 0.01;
			} else {
				cam.translate(0,-1);
			}
			cameraNeedsUpdating = true;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (shiftHeld) {
				//rotate
				cam.rotate(2);
			} else {
				cam.translate(-1,0);
			}
			cameraNeedsUpdating = true;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (shiftHeld) {
				//rotate
				cam.rotate(-2);
			} else {
				cam.translate(1,0);
			}
			cameraNeedsUpdating = true;
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();		// quit the game
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			imgY += 2;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			imgY -= 2;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			if (shiftHeld) {
				imgAngle += 2;
			} else {
				imgX -=2;
			}
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			if (shiftHeld) {
				imgAngle -= 2;
			} else {
				imgX +=2;
			}
		}
		//if space bar, advance in the direction I am looking
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			imgX += 5*MathUtils.cosDeg(imgAngle);
			imgY += 5*MathUtils.sinDeg(imgAngle);
		}

		if (cameraNeedsUpdating) {
			updateCamera();
		}
	}

	public void updateCamera() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput();
		batch.begin();
		batch.draw(background1, backgroundX, 0, displayWidth,displayHeight);
		batch.draw(background2, backgroundX + displayWidth, 0, displayWidth,displayHeight);
		batch.draw(img, imgX, imgY, imgWidth/2, imgHeight/2, imgWidth, imgHeight, 1, 1, imgAngle);
		//batch.draw(img, imgX, imgY);
		batch.draw(img2, imgX, imgY);
		batch.end();

		backgroundX -= backgroundSpeed;

		if (backgroundX + displayWidth == 0) {
			backgroundX = 0;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		
	}
}
