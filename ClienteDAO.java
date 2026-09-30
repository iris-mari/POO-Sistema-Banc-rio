import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    public boolean salvar(Cliente cliente) {
        if (cliente.getEndereco() == null) {
            System.err.println("Erro: Não é possível salvar um Cliente sem um Endereço associado (Mapeamento 1:1).");
            return false;
        }

        // 1. Salva o endereço no banco de dados primeiro se ele ainda não foi salvo
        if (cliente.getEndereco().getId() == null) {
            boolean enderecoSalvo = enderecoDAO.salvar(cliente.getEndereco());
            if (!enderecoSalvo) {
                System.err.println("Erro ao salvar o Endereço do Cliente.");
                return false;
            }
        }

        // 2. Salva o cliente vinculando a chave estrangeira (endereco_id)
        String sql = "INSERT INTO clientes (nome, cpf, telefone, senha, endereco_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getSenha());
            stmt.setInt(5, cliente.getEndereco().getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cliente.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar Cliente: " + e.getMessage());
        }
        return false;
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int enderecoId = rs.getInt("endereco_id");
                    Endereco endereco = enderecoDAO.buscarPorId(enderecoId);

                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            endereco
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Cliente por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int enderecoId = rs.getInt("endereco_id");
                Endereco endereco = enderecoDAO.buscarPorId(enderecoId);

                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        endereco
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Clientes: " + e.getMessage());
        }
        return clientes;
    }
}
