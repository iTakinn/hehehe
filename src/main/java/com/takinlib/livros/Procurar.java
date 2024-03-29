package com.takinlib.livros;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

public class Procurar {
    protected String pesquisa;
    public static List<Thread> threadList = new ArrayList<>();
    public List<String> resultado = new ArrayList<>();
    private long num = -1;
    public static long tempoInicioProcura;
    public Procurar(){
        GerarLivros.setRunning(true);
    }

    @SuppressWarnings("static-access")
    public Long procurar(String procura, int threads) throws InterruptedException {
        tempoInicioProcura = System.nanoTime();
        
        threadList.clear();
        for (int i = 0; i < threads; i++) {
            long livroDaThread = i * 1000000000L;
            GerarLivros gerador = new GerarLivros(livroDaThread);
            gerador.setPesquisa(procura);

            @SuppressWarnings("rawtypes")
            Thread thread = new Thread(() -> {
                HashSet conteudo = new HashSet();
                conteudo = gerador.run(true, false);
                if (conteudo != null) {
                    gerador.achado = true;
                    gerador.setRunning(false);
                    for (Object obj : conteudo) {
                        if (obj instanceof Long) {
                            num = (long) obj;
                            System.out.println("Long: " + num);
                        }

                    }
                }
                pararThreads();
            });

            threadList.add(thread);
        }

        threadList.forEach(Thread::start);

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return num;

        /*
         * for (int i = 0; i < 10; i++) {
         * long livroDaThread = i * 1000000000L;
         * GerarLivros gerador = new GerarLivros(livroDaThread);
         * gerador.setPesquisa(procura);
         * Thread thread = new Thread(gerador);
         * threadList.add(thread);
         * }
         * 
         * threadList.forEach(Thread::start);
         */

    }

    @SuppressWarnings("deprecation")
    public void pararThreads() {
        System.out.println(Thread.currentThread() + " parando threads");
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadList.forEach(Thread::stop);
        threadList.forEach(Thread::interrupt);

    }
}

/*
 * fazer as threads iniciarem direto, e um metodo no gerar livros pra modificar
 * o rodando e resetar o livro
 * pra no swing ativar o rodando quando clicar no fazer pesquisa e quando achar
 * desativar
 */