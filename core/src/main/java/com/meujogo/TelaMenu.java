package com.meujogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TelaMenu implements Screen {
    final Main game; // Referência ao gerente
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture imagemCapa;

    public TelaMenu(final Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        imagemCapa = new Texture("telaprincipal.png"); // Use uma imagem sua aqui!
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1); // Fundo azul escuro
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Desenha a capa no centro
        game.batch.draw(imagemCapa, 
            (1280 - imagemCapa.getWidth()) / 2, 
            (720 - imagemCapa.getHeight()) / 2);
        // (Opcional) Desenhe um texto aqui depois escrito "Clique para Iniciar"
        game.batch.end();

        // --- LÓGICA DE TROCA DE TELA ---
        // Se tocou na tela OU apertou Espaço...
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // ... Troca para a tela do jogo!
            game.setScreen(new TelaJogo(game));
            dispose(); // Joga fora a tela de menu para economizar memória
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void dispose() {
        imagemCapa.dispose();
    }
}