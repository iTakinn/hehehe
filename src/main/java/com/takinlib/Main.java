package com.takinlib;

import java.util.HashSet;

import com.takinlib.frames.ShowBookv2;
import com.takinlib.livros.Procurar;

public class Main {
    

    public static void main(String[] args) throws InterruptedException {

        ShowBookv2.launch(ShowBookv2.class, args);

    }


    public static void pararThreads() {
        Procurar hm = new Procurar();
        hm.pararThreads();
    }

    @SuppressWarnings("rawtypes")
    public static volatile HashSet livros = new HashSet();

}