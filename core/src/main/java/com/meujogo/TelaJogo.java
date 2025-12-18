package com.meujogo;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TelaJogo implements Screen {
    final Main game; 

    private OrthographicCamera camera;
    private FitViewport viewport;
    private Heroi player;
    private Texture imgFundo;
    private Array<Rectangle> chaoSolido;
    private ShapeRenderer debugRenderer;

    public TelaJogo(final Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        imgFundo = new Texture("fundo.png");
        player = new Heroi(100, 0);

        chaoSolido = new Array<>();
        debugRenderer = new ShapeRenderer();
        chaoSolido.add(new Rectangle(0, 0, 1280, 220));
        chaoSolido.add(new Rectangle(500, 220, 200, 110));

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // --- Lógica ---
        player.atualizar(delta, chaoSolido);
        camera.update();

        // --- Desenho ---
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(imgFundo, 0, 0, 1280, 720);
        player.render(game.batch);
        game.batch.end();

        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(0, 1, 0, 1);

        for (Rectangle retangulo : chaoSolido) {
            debugRenderer.rect(retangulo.x, retangulo.y, retangulo.width, retangulo.height);
        }

        debugRenderer.setColor(1, 0, 0, 1);
        debugRenderer.rect(player.hitbox.x, player.hitbox.y, player.hitbox.width, player.hitbox.height);
        debugRenderer.end();
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
        debugRenderer.dispose();
    }
}