package com.takinlib.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import com.takinlib.Main;
import com.takinlib.livros.GerarLivros;

public class ShowBook {

    private JTextArea pagina1;
    private JPanel inface;
    private JFrame frame;

    public ShowBook(int x, int y) {
        frame = new JFrame("livraria");
        frame.setSize(x, y);

        pagina1 = new JTextArea(15, 70);
        pagina1.setLineWrap(true);
        pagina1.setEditable(false);

        inface = new JPanel();
        inface.setLayout(new GridBagLayout());
        inface.add(pagina1); // Adicionando pagina1 ao inface
        frame.add(inface); // Adicionando inface ao frame

        frame.setVisible(true);
    }

    public void limparInterface() {
        // Remova todos os componentes do inface
        inface.removeAll();

        // Atualize o contêiner
        inface.revalidate();
        inface.repaint();
    }

    public void MostrarLivro(long livro, String pesquisa) {
        // Limpa a interface antes de mostrar o livro
        limparInterface();
        System.out.println(Thread.currentThread() + " no mostrarlivro");

        GerarLivros gerador = new GerarLivros(livro);
        String dataLivro = gerador.obterLivroEspecifico(livro)
                .replaceAll(pesquisa, "<<<<<>" + pesquisa + "<>>>>>");
        frame.setTitle("livro " + livro);
        JButton voltarButton = new JButton("Nova Pesquisa");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MostrarPesquisa();
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

    public void MostrarPesquisa() {
        // Limpa a interface antes de mostrar a pesquisa
        limparInterface();
        System.out.println(Thread.currentThread() + " no mostrar pesquisa");
        JPanel painel = new JPanel();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Digite o termo a ser buscado:");
        JTextField campoTexto = new JTextField(20);
        JButton botaoBuscar = new JButton("Buscar");
        Procurar procra = new Procurar();
        JLabel threads = new JLabel("Digite quantidade de threads:");
        JTextField campoThreads = new JTextField(20);
        campoThreads.setText("2");
        botaoBuscar.addActionListener((ActionListener) new ActionListener() {
            private String termoBusca = "";
            private long livro = 0;
            private int threads = 1;

            public void actionPerformed(ActionEvent e) {
                this.termoBusca = campoTexto.getText();
                this.threads = Integer.parseInt(campoThreads.getText());
                // Inicia uma SwingWorker para executar a pesquisa em uma thread separada
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    private long livro = 0;

                    @Override
                    protected Void doInBackground() throws Exception {

                        livro = procra.procurar(termoBusca, threads);
                        if (livro == 0) {
                            doInBackground();
                        }
                        return null;

                    }

                    @Override
                    protected void done() {

                        if (livro > 0) {
                            Main.mostrarLivro(livro, termoBusca);
                        }

                    }
                };
                worker.execute(); // Executa a SwingWorker
            }
        });

        JButton botaoParar = new JButton("Parar Pesquisa");
        botaoParar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                procra.pararThreads();
            }
        });
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.add(label);
        painel.add(campoTexto);

        panel.add(threads);
        panel.add(campoThreads);
        painel.add(botaoBuscar);
        painel.add(botaoParar);

        // Adicione o JPanel de pesquisa ao inface
        inface.add(painel);
        inface.add(panel);

        // Atualize o contêiner
        inface.revalidate();
        inface.repaint();
    }
}
