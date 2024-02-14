package com.takinlib.livros;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.takinlib.Main;

public class GerarLivros {
    private static volatile boolean running = true;
    private boolean achou = false;
    public String livroGerado;
    private String pesquisa;
    private long livro;

    public GerarLivros(long threadIndex) {
        this.livro = threadIndex;
    }

    public void setLivro(long livro) {
        this.livro = livro;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    StringBuilder sb = new StringBuilder();
    public int tamanho = 3600;

    public boolean achado = false;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HashSet run(boolean running, boolean achado) {

        System.out.println(Thread.currentThread() + "no gerarlivro");
        while (running) {
            if (running) {
                Random aa = new Random();
                Random seed = new Random();

                for (int i = 0; i <= tamanho; i++) {
                    seed.setSeed(livro * i);
                    int sed = seed.nextInt();
                    aa.setSeed(sed);
                    int gen = aa.nextInt(100, 1000000);

                    int index = (gen - 1) % 28;
                    int asciiValue = index + 97;
                    char letra;
                    if (asciiValue > 122) {
                        letra = ' ';
                    } else {
                        letra = (char) asciiValue;
                    }
                    sb.append(letra);
                }
                livroGerado = this.sb.toString();
                if (livroGerado.contains(pesquisa)) {
                    System.out.println(livroGerado);
                    System.out.println(Thread.currentThread() + "  ACHOU no livro " + livro + "   "
                            + new SimpleDateFormat("HH:mm:ss")
                                    .format(Calendar.getInstance().getTime()));
                    achou = true;
                    running = false;
                    HashSet conteudo = new HashSet();
                    conteudo.add(livroGerado);
                    conteudo.add(livro);
                    return conteudo;
                } else {

                    livro++;
                    this.sb.delete(0, tamanho + 1);
                }
            } else if (running == false) {
                System.out.println("nao rodando, parando " + Thread.currentThread());
                Thread.currentThread().stop();
            }

        }
        return null;
    }

    public boolean checarPesquisa() throws IOException, InterruptedException {
        while (!achou && running) {
            if (this.getLivroGerado().toString().contains(pesquisa)) {
                System.out.println(getLivroGerado());
                System.out.println(Thread.currentThread() + "  ACHOU no livro " + livro + "   "
                        + new SimpleDateFormat("HH:mm:ss" + livroGerado)
                                .format(Calendar.getInstance().getTime()));
                achou = true;
                running = false;
            } else {
                livro++;
                System.out.println(livro);
                this.sb.delete(0, tamanho + 1);
            }
        }
        return achou;
    }

    public String getLivroGerado() {
        return this.livroGerado;
    }

    public long getLivro() {
        return this.livro;
    }

    public String obterLivroEspecifico(long livroo) {

        Random aa = new Random();
        Random seed = new Random();

        for (int i = 0; i <= tamanho; i++) {
            seed.setSeed(livroo * i);
            int sed = seed.nextInt();
            aa.setSeed(sed);
            int gen = aa.nextInt(100, 1000000);

            int index = (gen - 1) % 28;
            int asciiValue = index + 97;
            char letra;
            if (asciiValue > 122) {
                letra = ' ';
            } else {
                letra = (char) asciiValue;
            }
            sb.append(letra);
        }
        return sb.toString();
    }

    public static void setRunning(boolean b) {
        running = b;
        System.out.println(running);
    }
}
