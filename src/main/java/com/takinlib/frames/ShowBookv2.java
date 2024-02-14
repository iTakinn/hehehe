package com.takinlib.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.takinlib.Main;
import com.takinlib.livros.GerarLivros;
import com.takinlib.livros.Procurar;

import java.awt.*;

public class ShowBookv2 {
    private JTextArea pagina1;
    private JPanel inface;
    private JFrame frame;
    private long livro;
    private String pesquisa;

    public ShowBookv2(int x, int y) {
        frame = new JFrame("livraria");
        frame.setSize(x, y);

        pagina1 = new JTextArea(15, 70);
        pagina1.setLineWrap(true);
        pagina1.setEditable(false);

        inface = new JPanel();
        inface.setLayout(new GridBagLayout());
        inface.add(pagina1);
        frame.add(inface);

        frame.setVisible(true);
    }

    private void clearInterface() {
        inface.removeAll();
        inface.revalidate();
        inface.repaint();
    }
    public void principal() {
        clearInterface();

        // Botão para fazer pesquisa
        JButton searchButton = new JButton("Fazer Pesquisa");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayContent(0,"search");
            }
        });

        // Botão para pesquisar por livro
        JButton findBookButton = new JButton("Pesquisar por Livro");
        findBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayContent(0, "unique");
            }
        });

        // Botão para carregar biblioteca
        JButton loadLibraryButton = new JButton("Carregar Biblioteca");
        loadLibraryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayContent(0, "list");
            }
        });

        // Adicione os botões ao inface
        inface.add(searchButton);
        inface.add(findBookButton);
        inface.add(loadLibraryButton);

        // Atualize o contêiner
        inface.revalidate();
        inface.repaint();
    }

    public void displayContent(long livro, String oq) {
        clearInterface();

        if (oq.equals("search")) {
            displaySearch();
        } else if (oq.equals("show")) {
            displayBook(livro);
        }else if (oq.equals("list")){

        }else if(oq.equals("unique")){

        }
    }

    private void displayBook(long livro) {
        final long duracao = System.nanoTime() - Procurar.tempoInicioProcura;
        double tempoProcura = (duracao / 1000000000);
        // Limpa a interface antes de mostrar o livro
        clearInterface();
        System.out.println(Thread.currentThread() + " no mostrarlivro");

        GerarLivros gerador = new GerarLivros(livro);
        String dataLivro = gerador.obterLivroEspecifico(livro)
                .replaceAll(pesquisa, "<<<<<>" + pesquisa + "<>>>>>");
        frame.setTitle("livro " + livro + "  |||  " + Main.livros.size() +
                " livros em " + tempoProcura + "s, " + (Main.livros.size()/tempoProcura) + "liv/s.");
        JButton voltarButton = new JButton("Nova Pesquisa");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLivro(0);
                setPesquisa("");
                displayContent(0, "search");
            }
        });
        frame.add(voltarButton, BorderLayout.SOUTH);
        pagina1.setText(dataLivro);
        pagina1.setFont(new Font("Ubuntu", Font.ITALIC, 13));

        // Adicione o JTextArea atualizado de volta ao inface
        inface.add(pagina1);

        // Atualize o contêiner
        inface.revalidate();
        inface.repaint();
    }

    private void displaySearch() {
        clearInterface();
        JPanel searchPanel = new JPanel();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Digite o termo a ser buscado:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        Procurar searcher = new Procurar();

        JLabel threadLabel = new JLabel("Digite quantidade de threads:");
        JTextField threadField = new JTextField(1);
        threadField.setText("2");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String term = searchField.getText();
                setPesquisa(term);
                int threads = Integer.parseInt(threadField.getText());

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    private long bookId = 0;

                    @Override
                    protected Void doInBackground() throws Exception {
                        bookId = searcher.procurar(term, threads);
                        if (bookId == 0) {
                            doInBackground();
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        if (bookId > 0) {
                            displayContent(bookId, "show");
                        }
                    }
                };
                worker.execute();
            }
        });

        JButton stopButton = new JButton("Parar Pesquisa");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searcher.pararThreads();
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.add(label);
        searchPanel.add(searchField);

        panel.add(threadLabel);
        panel.add(threadField);
        searchPanel.add(searchButton);
        searchPanel.add(stopButton);

        inface.add(searchPanel);
        inface.add(panel);
        inface.revalidate();
        inface.repaint();
    }

    public void setLivro(long livro) {
        this.livro = livro;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public String getPesquisa() {
        return this.pesquisa;
    }

    public long getLivro() {
        return this.livro;
    }


}
