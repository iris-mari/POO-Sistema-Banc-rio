import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {
    private static final String URL = "jdbc:sqlite:banco.db";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite JDBC não encontrado. Certifique-se de que o JAR está no classpath.");
        }
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBanco() {
        String sqlEndereco = "CREATE TABLE IF NOT EXISTS enderecos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "logradouro TEXT NOT NULL, " +
                "numero TEXT NOT NULL, " +
                "bairro TEXT, " +
                "cidade TEXT NOT NULL, " +
                "estado TEXT NOT NULL, " +
                "cep TEXT" +
                ");";

        String sqlCliente = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cpf TEXT NOT NULL, " +
                "telefone TEXT, " +
                "senha TEXT, " +
                "endereco_id INTEGER UNIQUE, " +
                "FOREIGN KEY (endereco_id) REFERENCES enderecos(id)" +
                ");";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlEndereco);
            stmt.execute(sqlCliente);
            System.out.println("[Banco de Dados] Tabelas 'enderecos' e 'clientes' inicializadas com sucesso!");
        } catch (SQLException e) {
            System.err.println("[Banco de Dados] Erro ao inicializar tabelas: " + e.getMessage());
        }
    }
}
