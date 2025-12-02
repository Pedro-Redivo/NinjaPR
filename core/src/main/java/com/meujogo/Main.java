package com.meujogo;

import com.badlogic.gdx.Game; // <-- O SEGREDO ESTÁ AQUI (Era ApplicationAdapter)
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    // O Batch é público para as telas (Menu e Jogo) poderem usar
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        // Agora que ele é um "Game", ele tem esse método setScreen!
        // Inicia mostrando o Menu
        this.setScreen(new TelaMenu(this));
    }

    @Override
    public void render() {
        // Super importante: manda desenhar a tela que estiver ativa (Menu ou Jogo)
        super.render(); 
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Limpa a tela atual se existir
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}