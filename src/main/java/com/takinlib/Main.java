package com.takinlib;

import java.util.HashSet;

import com.takinlib.frames.Procurar;
import com.takinlib.frames.ShowBook;

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

    @SuppressWarnings("rawtypes")
    public static volatile HashSet livros = new HashSet();

}