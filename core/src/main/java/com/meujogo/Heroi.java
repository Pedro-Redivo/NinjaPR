package com.meujogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Heroi extends Personagem {
    
    // Variáveis de Física
    private Texture imgParado;
    private Texture imgCorrendo;
    private Texture imgPulando;
    private Texture imgAgachado;
    private EstadoHeroi estadoAtual;
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
        this.velocidade = 300;
        this.estadoAtual = EstadoHeroi.PARADO;
    }

    public void atualizar(float delta) {
        estadoAtual = EstadoHeroi.PARADO;

        // --- 1. Movimento Horizontal ---
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
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            estadoAtual = EstadoHeroi.AGACHADO;
        }

        // --- 2. PULO ---
        // Se apertar ESPAÇO e estiver no chão -> PULA
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && estaNoChao) {
            velocidadeY = 500; // Força do pulo
            estaNoChao = false;
        }

        if(!estaNoChao) {
            estadoAtual = EstadoHeroi.PULANDO;
        }

        // --- 3. GRAVIDADE ---
        velocidadeY += gravidade * delta; // A gravidade puxa a velocidade para baixo
        y += velocidadeY * delta;         // A velocidade move o personagem

        // --- 4. COLISÃO COM O CHÃO ---
        if (y < 179) {
            y = 179;
            velocidadeY = 0;
            estaNoChao = true;
        }
        
        // Limites laterais
        if (x < 0) x = 0;
        if (x > 1280 - textura.getWidth()) x = 1280 - textura.getWidth();
    }

    @Override
    public void render(SpriteBatch batch) {
        // 1. Escolhe qual imagem desenhar baseada no ESTADO
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

        // 2. Desenho Inteligente (Com Flip/Espelhamento)
        // O método draw completo permite inverter a imagem
        batch.draw(
            texturaAtual, 
            x, y,                         // Posição X, Y
            texturaAtual.getWidth(),      // Largura
            texturaAtual.getHeight(),     // Altura
            0, 0,                         // Origem do recorte (0,0)
            texturaAtual.getWidth(),      // Tamanho do recorte
            texturaAtual.getHeight(),
            !olhandoDireita,          // Flip X: Se NÃO olha pra direita, inverte!
            false                         // Flip Y: Nunca inverte de ponta cabeça
        );
    }
    
    @Override
    public void dispose() {
        // Limpa tudo da memória
        imgParado.dispose();
        imgCorrendo.dispose();
        imgPulando.dispose();
    }
}
