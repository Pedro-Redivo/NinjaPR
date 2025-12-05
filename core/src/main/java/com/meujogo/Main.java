package com.meujogo;

import com.badlogic.gdx.Game; // <-- O SEGREDO ESTÁ AQUI (Era ApplicationAdapter)
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    // O Batch é público para as telas (Menu e Jogo) poderem usar
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        

        this.setScreen(new TelaMenu(this));
    }

    @Override
    public void render() {
        super.render(); 
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}