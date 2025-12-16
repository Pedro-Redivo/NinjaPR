package com.meujogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Heroi extends Personagem {
    
    private Texture imgParado;
    private Texture imgCorrendo;
    private Texture imgPulando;
    private Texture imgAgachado;
    private EstadoHeroi estadoAtual;
    public Rectangle hitbox;
    private boolean olhandoDireita = true;
    
    private float velocidadeY = 0; 
    private float gravidade = -1000; 
    private boolean estaNoChao = false;

    public Heroi(float inicioX, float inicioY) {
        super("ninja.png", inicioX, inicioY);
        imgParado = new Texture("ninja.png");
        imgCorrendo = new Texture("ninjaRun1.png");
        imgPulando = new Texture("ninjaPulando1.png");
        imgAgachado = new Texture("ninjaAgachado1.png");
        hitbox = new Rectangle(x, y, 20, textura.getHeight());
        this.velocidade = 300;
        this.estadoAtual = EstadoHeroi.PARADO;
    }

    public void atualizar(float delta, Array<Rectangle> plataformas) {
        if (estaNoChao) {
            estadoAtual = EstadoHeroi.PARADO;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += velocidade * delta;
            if (estaNoChao) estadoAtual = EstadoHeroi.CORRENDO;
            olhandoDireita = true;
        } 
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= velocidade * delta;
            if (estaNoChao) estadoAtual = EstadoHeroi.CORRENDO;
            olhandoDireita = false;
        }

        velocidadeY += gravidade * delta;
        y += velocidadeY * delta;

        hitbox.setPosition(x + 10, y);
        
        estaNoChao = false;

        for (Rectangle chao : plataformas) {
            if (hitbox.overlaps(chao)) {
                if (velocidadeY < 0 && y + 10 > chao.y + chao.height / 2) {
                    y = chao.y + chao.height;
                    velocidadeY = 0;
                    estaNoChao = true;
                    hitbox.setPosition(x + 10, y);
                }
            }
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && estaNoChao) {
            velocidadeY = 600; 
            estaNoChao = false;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) && estaNoChao) {
            estadoAtual = EstadoHeroi.AGACHADO;
        }

        if (!estaNoChao) {
            estadoAtual = EstadoHeroi.PULANDO;
        }

        if (x < 0) x = 0;
        if (x > 1280 - textura.getWidth()) x = 1280 - textura.getWidth();
        
        if (y < -100) {
            x = 100;
            y = 300;
            velocidadeY = 0;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Texture texturaAtual;

        switch (estadoAtual) {
            case CORRENDO:
                texturaAtual = imgCorrendo;
                break;
            case PULANDO:
                texturaAtual = imgPulando;
                break;
            case AGACHADO:
                texturaAtual = imgAgachado;
                break;
            default:
                texturaAtual = imgParado;
                break;
        }


        batch.draw(
            texturaAtual, 
            x, y,                        
            texturaAtual.getWidth(),      
            texturaAtual.getHeight(),     
            0, 0,                        
            texturaAtual.getWidth(),     
            texturaAtual.getHeight(),
            !olhandoDireita,          
            false                         
        );
    }
    
    @Override
    public void dispose() {
        imgParado.dispose();
        imgCorrendo.dispose();
        imgPulando.dispose();
    }
}
