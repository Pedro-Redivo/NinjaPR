package com.meujogo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Heroi player;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture imgFundo;

    @Override
    public void create() {
        batch = new SpriteBatch();
        imgFundo = new Texture("BackGroundTest.jpg");
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        player = new Heroi(0, 0);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {

        player.atualizar(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(0f, 0f, 0f, 1f);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(imgFundo, 0, 0);
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }
}