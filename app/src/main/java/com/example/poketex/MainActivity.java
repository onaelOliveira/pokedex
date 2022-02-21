package com.example.poketex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    int posicao = 870;
    int qtde = 10;
    LinearLayout lnlPoketex;
    Button btnProximo;
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lnlPoketex = findViewById(R.id.lnlPoketex);

        btnProximo = new Button(getBaseContext());
        btnProximo.setText("Carregar mais");
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao += qtde;
                carregar();
            }
        });

        btnVoltar = new Button(getBaseContext());
        btnVoltar.setText("Voltar");
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao -= qtde;
                carregar();
            }
        });


        carregar();
    }
    private void carregar(){
        lnlPoketex.removeAllViews();

        if(posicao > 1) lnlPoketex.addView(btnVoltar);

        for (int i = posicao; i < posicao + qtde; i++){
            carregarImagem(i);
        }

        if (posicao < 891) lnlPoketex.addView(btnProximo);
    }
    private void carregarImagem(int indice){
        ImageView img = new ImageView(getBaseContext());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", indice) ;

                    InputStream is = new URL(path).openStream();
                    Bitmap b = BitmapFactory.decodeStream(is);

                    img.post(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(b);
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
        lnlPoketex.addView(img);
    }
}