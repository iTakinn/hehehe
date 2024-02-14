package com.takinlib;

import java.util.HashSet;

import com.takinlib.frames.ShowBookv2;
import com.takinlib.livros.Procurar;

public class Main {
    public static ShowBookv2 shw = new ShowBookv2(800, 700);

    public static void main(String[] args) throws InterruptedException {

        shw.displayContent(0, "search");;

    }

    public static void mostrarLivro(long livro, String pesquisa) {
        shw.displayContent(livro, "show");
    }

    public static void pararThreads() {
        Procurar hm = new Procurar();
        hm.pararThreads();
    }

    @SuppressWarnings("rawtypes")
    public static volatile HashSet livros = new HashSet();

}