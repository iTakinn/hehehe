package com.takinlib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class heh {
    public static void main(String[] args) throws SQLException {
        String a = "package com.takinlib.frames;\r\n" + //
                        "\r\n" + //
                        "import javafx.application.Application;\r\n" + //
                        "import javafx.application.Platform;\r\n" + //
                        "import javafx.concurrent.Task;\r\n" + //
                        "import javafx.event.ActionEvent;\r\n" + //
                        "import javafx.event.EventHandler;\r\n" + //
                        "import javafx.geometry.Insets;\r\n" + //
                        "import javafx.geometry.Pos;\r\n" + //
                        "import javafx.scene.Scene;\r\n" + //
                        "import javafx.scene.control.Button;\r\n" + //
                        "import javafx.scene.control.Label;\r\n" + //
                        "import javafx.scene.control.TextArea;\r\n" + //
                        "import javafx.scene.control.TextField;\r\n" + //
                        "import javafx.scene.layout.Background;\r\n" + //
                        "import javafx.scene.layout.BackgroundFill;\r\n" + //
                        "import javafx.scene.layout.BorderPane;\r\n" + //
                        "import javafx.scene.layout.CornerRadii;\r\n" + //
                        "import javafx.scene.layout.GridPane;\r\n" + //
                        "import javafx.scene.layout.HBox;\r\n" + //
                        "import javafx.scene.layout.VBox;\r\n" + //
                        "import javafx.scene.paint.Color;\r\n" + //
                        "import javafx.scene.text.Font;\r\n" + //
                        "import javafx.stage.Stage;\r\n" + //
                        "\r\n" + //
                        "import java.io.File;\r\n" + //
                        "import java.sql.*;\r\n" + //
                        "import java.util.concurrent.ExecutorService;\r\n" + //
                        "import java.util.concurrent.Executors;\r\n" + //
                        "\r\n" + //
                        "import com.takinlib.Main;\r\n" + //
                        "import com.takinlib.livros.GerarLivros;\r\n" + //
                        "import com.takinlib.livros.Procurar;\r\n" + //
                        "\r\n" + //
                        "public class ShowBookv2 extends Application {\r\n" + //
                        "    private TextArea pagina1;\r\n" + //
                        "    private BorderPane inface;\r\n" + //
                        "    private Stage stage;\r\n" + //
                        "    private long livro;\r\n" + //
                        "    private String pesquisa;\r\n" + //
                        "\r\n" + //
                        "    @Override\r\n" + //
                        "    public void start(Stage primaryStage) {\r\n" + //
                        "        stage = primaryStage;\r\n" + //
                        "        stage.setTitle(\"livraria\");\r\n" + //
                        "\r\n" + //
                        "        pagina1 = new TextArea();\r\n" + //
                        "        pagina1.setWrapText(true);\r\n" + //
                        "        pagina1.setEditable(false);\r\n" + //
                        "\r\n" + //
                        "        inface = new BorderPane();\r\n" + //
                        "        inface.setCenter(pagina1);\r\n" + //
                        "        Scene scene = new Scene(inface, 1200, 600);\r\n" + //
                        "        stage.setScene(scene);\r\n" + //
                        "        stage.show();\r\n" + //
                        "\r\n" + //
                        "        principal();\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void clearInterface() {\r\n" + //
                        "        inface.getChildren().clear();\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    public void principal() {\r\n" + //
                        "        clearInterface();\r\n" + //
                        "        criarBancoDeDadosSeNaoExistir();\r\n" + //
                        "\r\n" + //
                        "        Button searchButton = new Button(\"Fazer Pesquisa\");\r\n" + //
                        "        searchButton.setOnAction(e -> displayContent(0, \"search\"));\r\n" + //
                        "\r\n" + //
                        "        Button findBookButton = new Button(\"Pesquisar por Livro\");\r\n" + //
                        "        findBookButton.setOnAction(e -> displayContent(0, \"unique\"));\r\n" + //
                        "\r\n" + //
                        "        Button loadLibraryButton = new Button(\"Carregar Biblioteca\");\r\n" + //
                        "        loadLibraryButton.setOnAction(e -> displayContent(0, \"list\"));\r\n" + //
                        "\r\n" + //
                        "        VBox buttonsBox = new VBox(10, searchButton, findBookButton, loadLibraryButton);\r\n" + //
                        "        buttonsBox.setAlignment(Pos.CENTER);\r\n" + //
                        "\r\n" + //
                        "        inface.setBottom(buttonsBox);\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    public void displayContent(long livro, String oq) {\r\n" + //
                        "        clearInterface();\r\n" + //
                        "\r\n" + //
                        "        if (oq.equals(\"search\")) {\r\n" + //
                        "            displaySearch();\r\n" + //
                        "        } else if (oq.equals(\"show\")) {\r\n" + //
                        "            displayBook(livro);\r\n" + //
                        "        } else if (oq.equals(\"list\")) {\r\n" + //
                        "            criarBiblioteca();\r\n" + //
                        "        } else if (oq.equals(\"unique\")) {\r\n" + //
                        "            principal(); // TODO pesquisa por livro\r\n" + //
                        "        }\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void criarBancoDeDadosSeNaoExistir() {\r\n" + //
                        "        // Nome do arquivo do banco de dados\r\n" + //
                        "        String nomeArquivo = \"livros.db\";\r\n" + //
                        "\r\n" + //
                        "        // Verificar se o arquivo do banco de dados existe\r\n" + //
                        "        File arquivo = new File(nomeArquivo);\r\n" + //
                        "\r\n" + //
                        "        if (!arquivo.exists()) {\r\n" + //
                        "            // Se o arquivo não existe, criar o banco de dados e a tabela\r\n" + //
                        "            try {\r\n" + //
                        "                Connection connection = DriverManager.getConnection(\"jdbc:sqlite:\" + nomeArquivo);\r\n" + //
                        "                Statement statement = connection.createStatement();\r\n" + //
                        "\r\n" + //
                        "                // Criação da tabela 'livros' com a coluna 'id' e 'esta_na_tabela'\r\n" + //
                        "                String sql = \"CREATE TABLE IF NOT EXISTS livros (\" +\r\n" + //
                        "                        \"id BIGINT, \" +\r\n" + //
                        "                        \"esta_na_tabela BOOLEAN)\";\r\n" + //
                        "                statement.execute(sql);\r\n" + //
                        "\r\n" + //
                        "                statement.close();\r\n" + //
                        "                connection.close();\r\n" + //
                        "\r\n" + //
                        "                System.out.println(\"Banco de dados criado com sucesso.\");\r\n" + //
                        "            } catch (SQLException e) {\r\n" + //
                        "                e.printStackTrace();\r\n" + //
                        "            }\r\n" + //
                        "        } else {\r\n" + //
                        "            System.out.println(\"O banco de dados já existe.\");\r\n" + //
                        "        }\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void criarBiblioteca() {\r\n" + //
                        "        clearInterface();\r\n" + //
                        "\r\n" + //
                        "        VBox bibliotecaPanel = new VBox(5);\r\n" + //
                        "        bibliotecaPanel.setPadding(new Insets(10));\r\n" + //
                        "\r\n" + //
                        "        try {\r\n" + //
                        "            Connection connection = DriverManager.getConnection(\"jdbc:sqlite:livros.db\");\r\n" + //
                        "            String sql = \"SELECT * FROM livros\";\r\n" + //
                        "            PreparedStatement statement = connection.prepareStatement(sql);\r\n" + //
                        "            ResultSet resultSet = statement.executeQuery();\r\n" + //
                        "\r\n" + //
                        "            while (resultSet.next()) {\r\n" + //
                        "                long livroId = resultSet.getLong(\"id\");\r\n" + //
                        "                Button livroButton = new Button(\"ID: \" + livroId);\r\n" + //
                        "                livroButton.setPrefWidth(130);\r\n" + //
                        "                livroButton.setOnAction(e -> {\r\n" + //
                        "                    displayContent(livroId);\r\n" + //
                        "                });\r\n" + //
                        "\r\n" + //
                        "                if (resultSet.getBoolean(\"esta_na_tabela\")) {\r\n" + //
                        "                    livroButton.setBackground(\r\n" + //
                        "                            new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));\r\n" + //
                        "                }\r\n" + //
                        "\r\n" + //
                        "                bibliotecaPanel.getChildren().add(livroButton);\r\n" + //
                        "            }\r\n" + //
                        "\r\n" + //
                        "            resultSet.close();\r\n" + //
                        "            statement.close();\r\n" + //
                        "            connection.close();\r\n" + //
                        "        } catch (SQLException e) {\r\n" + //
                        "            e.printStackTrace();\r\n" + //
                        "        }\r\n" + //
                        "\r\n" + //
                        "        Button voltarPrincipal = new Button(\"Voltar\");\r\n" + //
                        "        voltarPrincipal.setOnAction(e -> principal());\r\n" + //
                        "\r\n" + //
                        "        BorderPane contentPane = new BorderPane();\r\n" + //
                        "        contentPane.setCenter(bibliotecaPanel);\r\n" + //
                        "        contentPane.setTop(voltarPrincipal);\r\n" + //
                        "\r\n" + //
                        "        inface.setCenter(contentPane);\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void displayContent(long livro) {\r\n" + //
                        "        clearInterface();\r\n" + //
                        "\r\n" + //
                        "        GerarLivros gerador = new GerarLivros(livro);\r\n" + //
                        "        String dataLivro = gerador.obterLivroEspecifico(livro);\r\n" + //
                        "\r\n" + //
                        "        Label titleLabel = new Label(\"livro \" + livro);\r\n" + //
                        "        titleLabel.setFont(Font.font(\"Ubuntu\", 13));\r\n" + //
                        "\r\n" + //
                        "        TextArea bookTextArea = new TextArea(dataLivro);\r\n" + //
                        "        bookTextArea.setEditable(false);\r\n" + //
                        "        bookTextArea.setWrapText(true);\r\n" + //
                        "\r\n" + //
                        "        Button voltarButton = new Button(\"Voltar\");\r\n" + //
                        "        voltarButton.setOnAction(e -> principal());\r\n" + //
                        "\r\n" + //
                        "        VBox buttonsBox = new VBox(10, voltarButton);\r\n" + //
                        "        buttonsBox.setAlignment(Pos.CENTER);\r\n" + //
                        "\r\n" + //
                        "        BorderPane contentPane = new BorderPane();\r\n" + //
                        "        contentPane.setCenter(bookTextArea);\r\n" + //
                        "        contentPane.setTop(titleLabel);\r\n" + //
                        "        contentPane.setBottom(buttonsBox);\r\n" + //
                        "\r\n" + //
                        "        inface.setCenter(contentPane);\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void displayBook(long livro) {\r\n" + //
                        "        final long duracao = System.nanoTime() - Procurar.tempoInicioProcura;\r\n" + //
                        "        double tempoProcura = (duracao / 1000000000);\r\n" + //
                        "        // Limpa a interface antes de mostrar o livro\r\n" + //
                        "        clearInterface();\r\n" + //
                        "\r\n" + //
                        "        GerarLivros gerador = new GerarLivros(livro);\r\n" + //
                        "        String dataLivro = gerador.obterLivroEspecifico(livro)\r\n" + //
                        "                .replaceAll(pesquisa, \"<<<<<>\" + pesquisa + \"<>>>>>\");\r\n" + //
                        "\r\n" + //
                        "        inserirLivroNoBancoDeDados(livro);\r\n" + //
                        "\r\n" + //
                        "        Label titleLabel = new Label(\"livro \" + livro + \"  |||  \" + Main.livros.size() +\r\n" + //
                        "                \" livros em \" + tempoProcura + \"s, \" + (Main.livros.size() / tempoProcura) + \"liv/s.\");\r\n" + //
                        "        titleLabel.setFont(Font.font(\"Ubuntu\", 13));\r\n" + //
                        "\r\n" + //
                        "        TextArea bookTextArea = new TextArea(dataLivro);\r\n" + //
                        "        bookTextArea.setEditable(false);\r\n" + //
                        "        bookTextArea.setWrapText(true);\r\n" + //
                        "\r\n" + //
                        "        Button novaPesquisaButton = new Button(\"Nova Pesquisa\");\r\n" + //
                        "        novaPesquisaButton.setOnAction(e -> {\r\n" + //
                        "            setLivro(0);\r\n" + //
                        "            setPesquisa(\"\");\r\n" + //
                        "            displayContent(0, \"search\");\r\n" + //
                        "        });\r\n" + //
                        "\r\n" + //
                        "        Button voltarButton = new Button(\"Voltar\");\r\n" + //
                        "        voltarButton.setOnAction(e -> principal());\r\n" + //
                        "\r\n" + //
                        "        VBox buttonsBox = new VBox(10, novaPesquisaButton, voltarButton);\r\n" + //
                        "        buttonsBox.setAlignment(Pos.CENTER);\r\n" + //
                        "\r\n" + //
                        "        BorderPane contentPane = new BorderPane();\r\n" + //
                        "        contentPane.setCenter(bookTextArea);\r\n" + //
                        "        contentPane.setTop(titleLabel);\r\n" + //
                        "        contentPane.setBottom(buttonsBox);\r\n" + //
                        "\r\n" + //
                        "        inface.setCenter(contentPane);\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private void inserirLivroNoBancoDeDados(long idLivro) {\r\n" + //
                        "\r\n" + //
                        "        try {\r\n" + //
                        "            Connection connection = DriverManager.getConnection(\"jdbc:sqlite:livros.db\");\r\n" + //
                        "\r\n" + //
                        "            // SQL para inserir um ID de livro na tabela\r\n" + //
                        "            String sql = \"INSERT OR IGNORE INTO livros (id, esta_na_tabela) VALUES (?, ?)\";\r\n" + //
                        "            PreparedStatement statement = connection.prepareStatement(sql);\r\n" + //
                        "            statement.setLong(1, idLivro);\r\n" + //
                        "            statement.setBoolean(2, true); // Definir como true para indicar que está na tabela\r\n" + //
                        "            statement.executeUpdate();\r\n" + //
                        "\r\n" + //
                        "            statement.close();\r\n" + //
                        "            connection.close();\r\n" + //
                        "\r\n" + //
                        "            System.out.println(\"Livro inserido no banco de dados com sucesso.\");\r\n" + //
                        "        } catch (SQLException e) {\r\n" + //
                        "            e.printStackTrace();\r\n" + //
                        "        }\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    private long bookId;\r\n" + //
                        "\r\n" + //
                        "    private void displaySearch() {\r\n" + //
                        "        clearInterface();\r\n" + //
                        "\r\n" + //
                        "        VBox searchPanel = new VBox(10);\r\n" + //
                        "        Label label = new Label(\"Digite o termo a ser buscado:\");\r\n" + //
                        "        TextField searchField = new TextField();\r\n" + //
                        "        Button searchButton = new Button(\"Buscar\");\r\n" + //
                        "        Procurar searcher = new Procurar();\r\n" + //
                        "\r\n" + //
                        "        HBox threadBox = new HBox(10);\r\n" + //
                        "        Label threadLabel = new Label(\"Digite quantidade de threads (muitos pode bugar):\");\r\n" + //
                        "        TextField threadField = new TextField(\"2\");\r\n" + //
                        "        threadBox.getChildren().addAll(threadLabel, threadField);\r\n" + //
                        "\r\n" + //
                        "        searchButton.setOnAction(e -> {\r\n" + //
                        "            String term = searchField.getText();\r\n" + //
                        "            setPesquisa(term);\r\n" + //
                        "            int threads = Integer.parseInt(threadField.getText());\r\n" + //
                        "\r\n" + //
                        "            ExecutorService executor = Executors.newSingleThreadExecutor();\r\n" + //
                        "            executor.submit(() -> {\r\n" + //
                        "\r\n" + //
                        "                try {\r\n" + //
                        "                    bookId = searcher.procurar(term, threads);\r\n" + //
                        "                    if (bookId > 0) {\r\n" + //
                        "                        Platform.runLater(() -> displayContent(bookId, \"show\"));\r\n" + //
                        "                    }\r\n" + //
                        "                } catch (InterruptedException e1) {\r\n" + //
                        "\r\n" + //
                        "                    e1.printStackTrace();\r\n" + //
                        "                }\r\n" + //
                        "\r\n" + //
                        "            });\r\n" + //
                        "\r\n" + //
                        "            executor.shutdown();\r\n" + //
                        "        });\r\n" + //
                        "\r\n" + //
                        "        Button stopButton = new Button(\"Parar Pesquisa\");\r\n" + //
                        "        stopButton.setOnAction(e -> {\r\n" + //
                        "            Main.livros.clear();\r\n" + //
                        "            searcher.pararThreads();\r\n" + //
                        "        });\r\n" + //
                        "\r\n" + //
                        "        searchPanel.getChildren().addAll(label, searchField, searchButton, stopButton, threadBox);\r\n" + //
                        "        inface.setBottom(searchPanel);\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    // Additional methods if needed\r\n" + //
                        "    private void setPesquisa(String term) {\r\n" + //
                        "        this.pesquisa = term;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    public void setLivro(long livro) {\r\n" + //
                        "        this.livro = livro;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    public String getPesquisa() {\r\n" + //
                        "        return this.pesquisa;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    public long getLivro() {\r\n" + //
                        "        return this.livro;\r\n" + //
                        "    }\r\n" + //
                        "}\r\n" + //
                        "package com.takinlib.livros;\r\n" + //
                                                        "\r\n" + //
                                                        "import java.util.Calendar;\r\n" + //
                                                        "import java.util.HashSet;\r\n" + //
                                                        "import java.util.Random;\r\n" + //
                                                        "import java.text.SimpleDateFormat;\r\n" + //
                                                        "import com.takinlib.Main;\r\n" + //
                                                        "\r\n" + //
                                                        "public class GerarLivros {\r\n" + //
                                                        "    private static volatile boolean running = true;\r\n" + //
                                                        "    public String livroGerado;\r\n" + //
                                                        "    private String pesquisa;\r\n" + //
                                                        "    private long livro;\r\n" + //
                                                        "\r\n" + //
                                                        "    public GerarLivros(long threadIndex) {\r\n" + //
                                                        "        this.livro = threadIndex;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    public void setLivro(long livro) {\r\n" + //
                                                        "        this.livro = livro;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    public void setPesquisa(String pesquisa) {\r\n" + //
                                                        "        this.pesquisa = pesquisa;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    StringBuilder sb = new StringBuilder();\r\n" + //
                                                        "    public int tamanho = 3600;\r\n" + //
                                                        "\r\n" + //
                                                        "    public boolean achado = false;\r\n" + //
                                                        "    \r\n" + //
                                                        "    @SuppressWarnings({ \"rawtypes\", \"unchecked\" })\r\n" + //
                                                        "    public HashSet run(boolean running, boolean achado) {\r\n" + //
                                                        "\r\n" + //
                                                        "        System.out.println(Thread.currentThread() + \"no gerarlivro\");\r\n" + //
                                                        "        Random aa = new Random();\r\n" + //
                                                        "        Random seed = new Random();\r\n" + //
                                                        "        while (running) {\r\n" + //
                                                        "            for (int i = 0; i <= tamanho; i++) {\r\n" + //
                                                        "                seed.setSeed(livro * i);\r\n" + //
                                                        "                int sed = seed.nextInt();\r\n" + //
                                                        "                aa.setSeed(sed);\r\n" + //
                                                        "                int gen = aa.nextInt(100, 1000000);\r\n" + //
                                                        "                int index = (gen - 1) % 28;\r\n" + //
                                                        "                int asciiValue = index + 97;\r\n" + //
                                                        "                char letra;\r\n" + //
                                                        "                if (asciiValue > 122) {\r\n" + //
                                                        "                    letra = ' ';\r\n" + //
                                                        "                } else {\r\n" + //
                                                        "                    letra = (char) asciiValue;\r\n" + //
                                                        "                }\r\n" + //
                                                        "                sb.append(letra);\r\n" + //
                                                        "            }\r\n" + //
                                                        "            livroGerado = this.sb.toString();\r\n" + //
                                                        "            if (livroGerado.contains(pesquisa)) {\r\n" + //
                                                        "                System.out.println(livroGerado);\r\n" + //
                                                        "                System.out.println(Thread.currentThread() + \"  ACHOU no livro \" + livro + \"   \"\r\n" + //
                                                        "                        + new SimpleDateFormat(\"HH:mm:ss\")\r\n" + //
                                                        "                                .format(Calendar.getInstance().getTime()));\r\n" + //
                                                        "                running = false;\r\n" + //
                                                        "                HashSet conteudo = new HashSet();\r\n" + //
                                                        "                // conteudo.add(livroGerado);\r\n" + //
                                                        "                conteudo.add(livro);\r\n" + //
                                                        "                return conteudo;\r\n" + //
                                                        "            } else {\r\n" + //
                                                        "                Main.livros.add(livro);\r\n" + //
                                                        "                livro++;\r\n" + //
                                                        "                this.sb.delete(0, tamanho + 1);\r\n" + //
                                                        "            }\r\n" + //
                                                        "\r\n" + //
                                                        "        }\r\n" + //
                                                        "        return null;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    /*\r\n" + //
                                                        "     * public boolean checarPesquisa() throws IOException, InterruptedException {\r\n" + //
                                                        "     * while (!achou && running) {\r\n" + //
                                                        "     * if (this.getLivroGerado().toString().contains(pesquisa)) {\r\n" + //
                                                        "     * System.out.println(getLivroGerado());\r\n" + //
                                                        "     * System.out.println(Thread.currentThread() + \"  ACHOU no livro \" + livro +\r\n" + //
                                                        "     * \"   \"\r\n" + //
                                                        "     * + new SimpleDateFormat(\"HH:mm:ss\" + livroGerado)\r\n" + //
                                                        "     * .format(Calendar.getInstance().getTime()));\r\n" + //
                                                        "     * achou = true;\r\n" + //
                                                        "     * running = false;\r\n" + //
                                                        "     * } else {\r\n" + //
                                                        "     * livro++;\r\n" + //
                                                        "     * System.out.println(livro);\r\n" + //
                                                        "     * this.sb.delete(0, tamanho + 1);\r\n" + //
                                                        "     * }\r\n" + //
                                                        "     * }\r\n" + //
                                                        "     * return achou;\r\n" + //
                                                        "     * }\r\n" + //
                                                        "     */\r\n" + //
                                                        "\r\n" + //
                                                        "    public String getLivroGerado() {\r\n" + //
                                                        "        return this.livroGerado;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    public long getLivro() {\r\n" + //
                                                        "        return this.livro;\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    public String obterLivroEspecifico(long livroo) {\r\n" + //
                                                        "\r\n" + //
                                                        "        Random aa = new Random();\r\n" + //
                                                        "        Random seed = new Random();\r\n" + //
                                                        "\r\n" + //
                                                        "        for (int i = 0; i <= tamanho; i++) {\r\n" + //
                                                        "            seed.setSeed(livroo * i);\r\n" + //
                                                        "            int sed = seed.nextInt();\r\n" + //
                                                        "            aa.setSeed(sed);\r\n" + //
                                                        "            int gen = aa.nextInt(100, 1000000);\r\n" + //
                                                        "\r\n" + //
                                                        "            int index = (gen - 1) % 28;\r\n" + //
                                                        "            int asciiValue = index + 97;\r\n" + //
                                                        "            char letra;\r\n" + //
                                                        "            if (asciiValue > 122) {\r\n" + //
                                                        "                letra = ' ';\r\n" + //
                                                        "            } else {\r\n" + //
                                                        "                letra = (char) asciiValue;\r\n" + //
                                                        "            }\r\n" + //
                                                        "            sb.append(letra);\r\n" + //
                                                        "        }\r\n" + //
                                                        "        return sb.toString();\r\n" + //
                                                        "    }\r\n" + //
                                                        "\r\n" + //
                                                        "    public static void setRunning(boolean b) {\r\n" + //
                                                        "        running = b;\r\n" + //
                                                        "        System.out.println(running);\r\n" + //
                                                        "    }\r\n" + //
                                                        "}\r\n" + //
                                                        "package com.takinlib.livros;\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "import java.util.ArrayList;\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "import java.util.HashSet;\r\n" + //
                                                                                                                        "import java.util.List;\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "public class Procurar {\r\n" + //
                                                                                                                        "    protected String pesquisa;\r\n" + //
                                                                                                                        "    public static List<Thread> threadList = new ArrayList<>();\r\n" + //
                                                                                                                        "    public List<String> resultado = new ArrayList<>();\r\n" + //
                                                                                                                        "    private long num = -1;\r\n" + //
                                                                                                                        "    public static long tempoInicioProcura;\r\n" + //
                                                                                                                        "    \r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "    @SuppressWarnings(\"static-access\")\r\n" + //
                                                                                                                        "    public Long procurar(String procura, int threads) throws InterruptedException {\r\n" + //
                                                                                                                        "        tempoInicioProcura = System.nanoTime();\r\n" + //
                                                                                                                        "      \r\n" + //
                                                                                                                        "        threadList.clear();\r\n" + //
                                                                                                                        "        for (int i = 0; i < threads; i++) {\r\n" + //
                                                                                                                        "            long livroDaThread = i * 1000000000L;\r\n" + //
                                                                                                                        "            GerarLivros gerador = new GerarLivros(livroDaThread);\r\n" + //
                                                                                                                        "            gerador.setPesquisa(procura);\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "            @SuppressWarnings(\"rawtypes\")\r\n" + //
                                                                                                                        "            Thread thread = new Thread(() -> {\r\n" + //
                                                                                                                        "                HashSet conteudo = new HashSet();\r\n" + //
                                                                                                                        "                conteudo = gerador.run(true, false);\r\n" + //
                                                                                                                        "                if (conteudo != null) {\r\n" + //
                                                                                                                        "                    gerador.achado = true;\r\n" + //
                                                                                                                        "                    gerador.setRunning(false);\r\n" + //
                                                                                                                        "                    for (Object obj : conteudo) {\r\n" + //
                                                                                                                        "                        if (obj instanceof Long) {\r\n" + //
                                                                                                                        "                            num = (long) obj;\r\n" + //
                                                                                                                        "                            System.out.println(\"Long: \" + num);\r\n" + //
                                                                                                                        "                        }\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "                    }\r\n" + //
                                                                                                                        "                }\r\n" + //
                                                                                                                        "                pararThreads();\r\n" + //
                                                                                                                        "            });\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "            threadList.add(thread);\r\n" + //
                                                                                                                        "        }\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "        threadList.forEach(Thread::start);\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "        threadList.forEach(t -> {\r\n" + //
                                                                                                                        "            try {\r\n" + //
                                                                                                                        "                t.join();\r\n" + //
                                                                                                                        "            } catch (InterruptedException e) {\r\n" + //
                                                                                                                        "                e.printStackTrace();\r\n" + //
                                                                                                                        "            }\r\n" + //
                                                                                                                        "        });\r\n" + //
                                                                                                                        "        return num;\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "        /*\r\n" + //
                                                                                                                        "         * for (int i = 0; i < 10; i++) {\r\n" + //
                                                                                                                        "         * long livroDaThread = i * 1000000000L;\r\n" + //
                                                                                                                        "         * GerarLivros gerador = new GerarLivros(livroDaThread);\r\n" + //
                                                                                                                        "         * gerador.setPesquisa(procura);\r\n" + //
                                                                                                                        "         * Thread thread = new Thread(gerador);\r\n" + //
                                                                                                                        "         * threadList.add(thread);\r\n" + //
                                                                                                                        "         * }\r\n" + //
                                                                                                                        "         * \r\n" + //
                                                                                                                        "         * threadList.forEach(Thread::start);\r\n" + //
                                                                                                                        "         */\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "    }\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "    @SuppressWarnings(\"deprecation\")\r\n" + //
                                                                                                                        "    public void pararThreads() {\r\n" + //
                                                                                                                        "        System.out.println(Thread.currentThread() + \" parando threads\");\r\n" + //
                                                                                                                        "        try {\r\n" + //
                                                                                                                        "            Thread.sleep(400);\r\n" + //
                                                                                                                        "        } catch (InterruptedException e) {\r\n" + //
                                                                                                                        "            e.printStackTrace();\r\n" + //
                                                                                                                        "        }\r\n" + //
                                                                                                                        "        threadList.forEach(Thread::stop);\r\n" + //
                                                                                                                        "        threadList.forEach(Thread::interrupt);\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "    }\r\n" + //
                                                                                                                        "}\r\n" + //
                                                                                                                        "\r\n" + //
                                                                                                                        "/*\r\n" + //
                                                                                                                        " * fazer as threads iniciarem direto, e um metodo no gerar livros pra modificar\r\n" + //
                                                                                                                        " * o rodando e resetar o livro\r\n" + //
                                                                                                                        " * pra no swing ativar o rodando quando clicar no fazer pesquisa e quando achar\r\n" + //
                                                                                                                        " * desativar\r\n" + //
                                                                                                                        " */package com.takinlib;\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "import java.util.HashSet;\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "import com.takinlib.frames.ShowBookv2;\r\n" + //
                                                                                                                                                                                                                                                        "import com.takinlib.livros.Procurar;\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "public class Main {\r\n" + //
                                                                                                                                                                                                                                                        "    \r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "    public static void main(String[] args) throws InterruptedException {\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "        ShowBookv2.launch(ShowBookv2.class, args);\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "    }\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "    public static void pararThreads() {\r\n" + //
                                                                                                                                                                                                                                                        "        Procurar hm = new Procurar();\r\n" + //
                                                                                                                                                                                                                                                        "        hm.pararThreads();\r\n" + //
                                                                                                                                                                                                                                                        "    }\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "    @SuppressWarnings(\"rawtypes\")\r\n" + //
                                                                                                                                                                                                                                                        "    public static volatile HashSet livros = new HashSet();\r\n" + //
                                                                                                                                                                                                                                                        "\r\n" + //
                                                                                                                                                                                                                                                        "}";
         System.out.println(a.length());
    }
}
