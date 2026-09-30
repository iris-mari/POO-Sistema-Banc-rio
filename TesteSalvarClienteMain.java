import java.util.List;

public class TesteSalvarClienteMain {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SISTEMA BANCÁRIO - TESTE DE SALVAMENTO (1:1)   ");
        System.out.println("==================================================\n");

        // 1. Inicializa o Banco de Dados e as tabelas
        ConexaoBanco.inicializarBanco();

        // 2. Criação do Endereço (Mapeamento 1:1)
        Endereco endereco = new Endereco(
                "Rua das Flores",
                "123",
                "Centro",
                "São Paulo",
                "SP",
                "01000-000"
        );

        // 3. Criação do Cliente com o seu respectivo Endereço
        Cliente cliente = new Cliente(
                "Carlos Silva",
                "123.456.789-00",
                "(11) 98765-4321",
                "senha123",
                endereco
        );

        System.out.println("\n[1] Tentando salvar o Cliente no banco de dados...");
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean sucesso = clienteDAO.salvar(cliente);

        if (sucesso) {
            System.out.println("[SUCESSO] Cliente salvo no banco de dados com ID: " + cliente.getId());
            System.out.println("[SUCESSO] Endereço associado salvo com ID: " + cliente.getEndereco().getId());
        } else {
            System.out.println("[ERRO] Falha ao salvar o cliente.");
        }

        // 4. Consulta do banco para verificar a persistência e o relacionamento 1:1
        System.out.println("\n[2] Consultando clientes salvos no banco de dados:");
        List<Cliente> lista = clienteDAO.listarTodos();

        for (Cliente c : lista) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID Cliente  : " + c.getId());
            System.out.println("Nome        : " + c.getNome());
            System.out.println("CPF         : " + c.getCpf());
            System.out.println("Telefone    : " + c.getTelefone());
            System.out.println("--- Endereço Mapeado (1:1) ---");
            if (c.getEndereco() != null) {
                System.out.println("ID Endereço : " + c.getEndereco().getId());
                System.out.println("Logradouro  : " + c.getEndereco().getLogradouro() + ", " + c.getEndereco().getNumero());
                System.out.println("Bairro      : " + c.getEndereco().getBairro());
                System.out.println("Cidade/UF   : " + c.getEndereco().getCidade() + " / " + c.getEndereco().getEstado());
                System.out.println("CEP         : " + c.getEndereco().getCep());
            } else {
                System.out.println("Nenhum endereço associado.");
            }
        }
        System.out.println("--------------------------------------------------");
    }
}
