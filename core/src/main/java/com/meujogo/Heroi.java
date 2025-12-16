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
        estadoAtual = EstadoHeroi.PARADO;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += velocidade * delta;
            estadoAtual = EstadoHeroi.CORRENDO;
            olhandoDireita = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= velocidade * delta;
            estadoAtual = EstadoHeroi.CORRENDO;
            olhandoDireita = false;
        }
        else{ 

            if(estaNoChao) estadoAtual = EstadoHeroi.PARADO;
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            estadoAtual = EstadoHeroi.AGACHADO;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && estaNoChao) {
            velocidadeY = 500;
            estaNoChao = false;
        }

        if(!estaNoChao) {
            estadoAtual = EstadoHeroi.PULANDO;
        }

        velocidadeY += gravidade * delta; 
        y += velocidadeY * delta;  

        hitbox.setPosition(x + 10, y);

        if (y < 179) {
            y = 179;
            velocidadeY = 0;
            estaNoChao = true;
        }
        
        if (x < 0) x = 0;
        if (x > 1280 - textura.getWidth()) x = 1280 - textura.getWidth();
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
