package com.sanilk.sat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer renderer;

	Polygon p1;
	Polygon p2;

	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		renderer=new ShapeRenderer();
		renderer.setAutoShapeType(true);

		font=new BitmapFont();

		float[] vertices1={
				180, 200, 220, 240, 280, 200, 240, 160
		};
		float[] vertices2={
				220, 220, 220, 260, 160, 260, 160, 180
		};

		p1=new Polygon(vertices1);
		p2=new Polygon(vertices2);

		p2.setOrigin(vertices2[3], vertices2[4]);

		font.setColor(Color.WHITE);
	}

	boolean goingRight=true;

	public void update(){
		float[] vertices1=p1.getTransformedVertices();

		for(int i=0;i<vertices1.length;i+=2) {

			if(vertices1[0]>200) {
				goingRight = false;
			}
			if(vertices1[0]<0){
				goingRight=true;
			}

			if(goingRight) {
				vertices1[i] += 3;
			}else{
				vertices1[i] -= 3;
			}

			p1.setVertices(vertices1);

			p2.rotate(0.3f);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.begin();

		renderer.setColor(new Color(1, 1, 0, 0));
		renderer.polygon(p1.getTransformedVertices());

		renderer.setColor(new Color(1, 0, 1, 0));
		renderer.polygon(p2.getTransformedVertices());

		renderer.end();

		batch.begin();

		String s;
		if(SATCollisionDetector.isColliding(p1, p2)){
			s="COLLISION";
		}else{
			s="NOT COLLIDING";
		}
		font.draw(batch, s, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

		batch.end();

		update();
	}
	
	@Override
	public void dispose () {
		renderer.dispose();
		batch.dispose();
//		img.dispose();
	}
}
