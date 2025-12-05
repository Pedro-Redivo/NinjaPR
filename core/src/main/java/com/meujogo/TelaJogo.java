package com.meujogo;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TelaJogo implements Screen {
    final Main game; 

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Heroi player;
    private Texture imgFundo;

    public TelaJogo(final Main game) {
        this.game = game;

        // 1. Configura a câmera
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // 2. Carrega os assets
        imgFundo = new Texture("BackGroundTest.jpg");
        player = new Heroi(100, 0);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // --- Lógica ---
        player.atualizar(delta);
        camera.update();

        // --- Desenho ---
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            game.batch.draw(imgFundo, 0, 0, 1280, 720);
            player.render(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        imgFundo.dispose();
        player.dispose();
    }
}