package com.takinlib.frames;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.takinlib.Main;
import com.takinlib.livros.GerarLivros;
import com.takinlib.livros.Procurar;

public class ShowBookv2 extends Application {
    private TextArea pagina1;
    private BorderPane inface;
    private Stage stage;
    private long livro;
    private String pesquisa;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("livraria");

        pagina1 = new TextArea();
        pagina1.setWrapText(true);
        pagina1.setEditable(false);

        inface = new BorderPane();
        inface.setCenter(pagina1);
        Scene scene = new Scene(inface, 1200, 600);
        stage.setScene(scene);
        stage.show();

        principal();
    }

    private void clearInterface() {
        inface.getChildren().clear();
    }

    public void principal() {
        clearInterface();
        criarBancoDeDadosSeNaoExistir();

        Button searchButton = new Button("Fazer Pesquisa");
        searchButton.setOnAction(e -> displayContent(0, "search"));

        Button findBookButton = new Button("Pesquisar por Livro");
        findBookButton.setOnAction(e -> displayContent(0, "unique"));

        Button loadLibraryButton = new Button("Carregar Biblioteca");
        loadLibraryButton.setOnAction(e -> displayContent(0, "list"));

        VBox buttonsBox = new VBox(10, searchButton, findBookButton, loadLibraryButton);
        buttonsBox.setAlignment(Pos.CENTER);

        inface.setBottom(buttonsBox);
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
            principal(); // TODO pesquisa por livro
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
                        "id BIGINT, " +
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

        VBox bibliotecaPanel = new VBox(5);
        bibliotecaPanel.setPadding(new Insets(10));

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:livros.db");
            String sql = "SELECT * FROM livros";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long livroId = resultSet.getLong("id");
                Button livroButton = new Button("ID: " + livroId);
                livroButton.setPrefWidth(130);
                livroButton.setOnAction(e -> {
                    displayContent(livroId);
                });

                if (resultSet.getBoolean("esta_na_tabela")) {
                    livroButton.setBackground(
                            new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                bibliotecaPanel.getChildren().add(livroButton);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button voltarPrincipal = new Button("Voltar");
        voltarPrincipal.setOnAction(e -> principal());

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(bibliotecaPanel);
        contentPane.setTop(voltarPrincipal);

        inface.setCenter(contentPane);
    }

    private void displayContent(long livro) {
        clearInterface();

        GerarLivros gerador = new GerarLivros(livro);
        String dataLivro = gerador.obterLivroEspecifico(livro);

        Label titleLabel = new Label("livro " + livro);
        titleLabel.setFont(Font.font("Ubuntu", 13));

        TextArea bookTextArea = new TextArea(dataLivro);
        bookTextArea.setEditable(false);
        bookTextArea.setWrapText(true);

        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> principal());

        VBox buttonsBox = new VBox(10, voltarButton);
        buttonsBox.setAlignment(Pos.CENTER);

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(bookTextArea);
        contentPane.setTop(titleLabel);
        contentPane.setBottom(buttonsBox);

        inface.setCenter(contentPane);
    }

    private void displayBook(long livro) {
        final long duracao = System.nanoTime() - Procurar.tempoInicioProcura;
        double tempoProcura = (duracao / 1000000000);
        // Limpa a interface antes de mostrar o livro
        clearInterface();

        GerarLivros gerador = new GerarLivros(livro);
        String dataLivro = gerador.obterLivroEspecifico(livro)
                .replaceAll(pesquisa, "<<<<<>" + pesquisa + "<>>>>>");

        inserirLivroNoBancoDeDados(livro);

        Label titleLabel = new Label("livro " + livro + "  |||  " + Main.livros.size() +
                " livros em " + tempoProcura + "s, " + (Main.livros.size() / tempoProcura) + "liv/s.");
        titleLabel.setFont(Font.font("Ubuntu", 13));

        TextArea bookTextArea = new TextArea(dataLivro);
        bookTextArea.setEditable(false);
        bookTextArea.setWrapText(true);

        Button novaPesquisaButton = new Button("Nova Pesquisa");
        novaPesquisaButton.setOnAction(e -> {
            setLivro(0);
            setPesquisa("");
            displayContent(0, "search");
        });

        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> principal());

        VBox buttonsBox = new VBox(10, novaPesquisaButton, voltarButton);
        buttonsBox.setAlignment(Pos.CENTER);

        BorderPane contentPane = new BorderPane();
        contentPane.setCenter(bookTextArea);
        contentPane.setTop(titleLabel);
        contentPane.setBottom(buttonsBox);

        inface.setCenter(contentPane);
    }

    private void inserirLivroNoBancoDeDados(long idLivro) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:livros.db");

            // SQL para inserir um ID de livro na tabela
            String sql = "INSERT OR IGNORE INTO livros (id, esta_na_tabela) VALUES (?, ?)";
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

    private long bookId;
    Procurar searcher = new Procurar();
    private void displaySearch() {
        clearInterface();
    
        VBox searchPanel = new VBox(10);
        Label label = new Label("Digite o termo a ser buscado:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Buscar");
        
    
        HBox threadBox = new HBox(10);
        Label threadLabel = new Label("Digite quantidade de threads (muitos pode bugar):");
        TextField threadField = new TextField("2");
        threadBox.getChildren().addAll(threadLabel, threadField);
    
        Label livroAtualLabel = new Label();
        searchPanel.getChildren().addAll(label, searchField, searchButton, livroAtualLabel, threadBox);
    
        searchButton.setOnAction(e -> {
            String term = searchField.getText();
            setPesquisa(term);
            int threads = Integer.parseInt(threadField.getText());
            searcher = new Procurar();
            Task<Long> task = new Task<Long>() {
                @Override
                protected Long call() throws Exception {
                    return searcher.procurar(term, threads);
                }
            };
    
            task.setOnSucceeded(event -> {
                long bookId = task.getValue();
                if (bookId > 0) {
                    displayContent(bookId, "show");
                }
            });
    
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(task);
    
            executor.shutdown();
        });
    
        Button stopButton = new Button("Parar Pesquisa");
        stopButton.setOnAction(e -> {
            Main.livros.clear();
            searcher.pararThreads();
        });
    
        searchPanel.getChildren().addAll(stopButton);
        inface.setBottom(searchPanel);
        GerarLivros.setRunning(true);
    
        // Atualizar o número de livros enquanto a pesquisa está em andamento
        Task<Void> updateLivrosTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                long livroAtual=0;
                Thread.sleep(100);
                while (GerarLivros.getRunning()) {
                    livroAtual = Main.livros.size();
                    updateMessage(livroAtual + " livros");
                    Thread.sleep(1000);
                }Thread.sleep(2350);
                if (GerarLivros.getRunning()==false) {
                    updateMessage("0 livros");
                    Thread.sleep(1000);    
                    Main.livros.clear();
                    updateMessage("0 livros");
                }
                return null;
            }
        };
    
        updateLivrosTask.messageProperty().addListener((observable, oldValue, newValue) -> {
            livroAtualLabel.setText(newValue);
        });
    
        Thread thread = new Thread(updateLivrosTask);
        thread.setDaemon(true);
        thread.start();
    }
    // Additional methods if needed
    private void setPesquisa(String term) {
        this.pesquisa = term;
    }

    public void setLivro(long livro) {
        this.livro = livro;
    }

    public String getPesquisa() {
        return this.pesquisa;
    }

    public long getLivro() {
        return this.livro;
    }
}
