package com.meujogo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Personagem implements Disposable {
    public float x;
    public float y;
    public Texture textura;
    public float velocidade;

    public Personagem(String caminhoImagem, float inicioX, float inicioY) {
        this.textura = new Texture(caminhoImagem);
        this.x = inicioX;
        this.y = inicioY;
        this.velocidade = 200;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textura, x, y);
    }

    //Limpa memoria
    @Override
    public void dispose() {
        textura.dispose();
    }
}