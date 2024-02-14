package com.takinlib.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.takinlib.Main;
import com.takinlib.livros.GerarLivros;
import com.takinlib.livros.Procurar;



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
        criarBancoDeDadosSeNaoExistir();
        clearInterface();

        // Botão para fazer pesquisa
        JButton searchButton = new JButton("Fazer Pesquisa");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayContent(0, "search");
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
        } else if (oq.equals("list")) {
            criarBiblioteca();
        } else if (oq.equals("unique")) {

        }
    }

    private void criarBancoDeDadosSeNaoExistir() {
        // Nome do arquivo do banco de dados
        String nomeArquivo = "livros.db";

        // Verificar se o arquivo do banco de dados existe
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            // Se o arquivo não existe, criar o banco de dados e a tabela
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + nomeArquivo);
                Statement statement = connection.createStatement();

                // Criação da tabela 'livros' com a coluna 'id' e 'esta_na_tabela'
                String sql = "CREATE TABLE IF NOT EXISTS livros (" +
                        "id INTEGER PRIMARY KEY, " +
                        "esta_na_tabela BOOLEAN)";
                statement.execute(sql);

                statement.close();
                connection.close();

                System.out.println("Banco de dados criado com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O banco de dados já existe.");
        }
    }

    private void criarBiblioteca() {
        clearInterface();

        JPanel bibliotecaPanel = new JPanel();
        bibliotecaPanel.setLayout(new GridLayout(0, 5)); // 5 livros por linha

        try {
            // Conexão com o banco de dados SQLite
            Connection connection = DriverManager.getConnection("jdbc:sqlite:livros.db");

            // Consulta SQL para selecionar todos os livros
            String sql = "SELECT * FROM livros";

            // Preparar a declaração
            PreparedStatement statement = connection.prepareStatement(sql);

            // Executar a consulta
            ResultSet resultSet = statement.executeQuery();

            // Loop sobre o resultado da consulta
            while (resultSet.next()) {
                long livroId = resultSet.getLong("id");

                JPanel livroPanel = new JPanel();
                livroPanel.setPreferredSize(new Dimension(100, 100)); // Tamanho do quadradinho do livro

                JLabel idLabel = new JLabel("ID: " + livroId);
                livroPanel.add(idLabel);

                // Verificar se o livro está na tabela com background azul claro
                if (resultSet.getBoolean("esta_na_tabela")) {
                    livroPanel.setBackground(new Color(173, 216, 230)); // Azul claro
                }

                bibliotecaPanel.add(livroPanel);
            }

            // Fechar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(bibliotecaPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane);
        frame.revalidate();
        frame.repaint();
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
                " livros em " + tempoProcura + "s, " + (Main.livros.size() / tempoProcura) + "liv/s.");
        JButton voltarButton = new JButton("Nova Pesquisa");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLivro(0);
                setPesquisa("");
                displayContent(0, "search");
            }
        });
        inserirLivroNoBancoDeDados(livro);
        frame.add(voltarButton, BorderLayout.SOUTH);
        pagina1.setText(dataLivro);
        pagina1.setFont(new Font("Ubuntu", Font.ITALIC, 13));

        // Adicione o JTextArea atualizado de volta ao inface
        inface.add(pagina1);

        // Atualize o contêiner
        inface.revalidate();
        inface.repaint();
    }
    private void inserirLivroNoBancoDeDados(long idLivro) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:livros.db");
    
            // SQL para inserir um ID de livro na tabela
            String sql = "INSERT INTO livros (id, esta_na_tabela) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, idLivro);
            statement.setBoolean(2, true); // Definir como true para indicar que está na tabela
            statement.executeUpdate();
    
            statement.close();
            connection.close();
    
            System.out.println("Livro inserido no banco de dados com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
