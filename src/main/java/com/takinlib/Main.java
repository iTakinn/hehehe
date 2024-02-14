package com.takinlib;

import com.takinlib.frames.Procurar;
import com.takinlib.frames.ShowBook;
import com.takinlib.livros.hehe;

public class Main {
    public static ShowBook shw = new ShowBook(800, 700);

    public static void main(String[] args) throws InterruptedException {

        shw.MostrarPesquisa();

    }

    public static void mostrarLivro(long livro, String pesquisa) {
        shw.MostrarLivro(livro, pesquisa);
    }

    public static void pararThreads() {
        Procurar hm = new Procurar();
        hm.pararThreads();
    }

}