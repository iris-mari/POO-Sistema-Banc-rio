public class Cliente extends Pessoa implements Autenticavel {

    private Integer id;
    private String cpf;
    private String telefone;
    private String senha;
    private Endereco endereco; // Mapeamento 1:1 (Um Cliente possui Um Endereço)

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String senha, Endereco endereco) {
        this.setNome(nome);
        this.cpf = cpf;
        this.telefone = telefone;
        this.senha = senha;
        this.endereco = endereco;
    }

    public Cliente(Integer id, String nome, String cpf, String telefone, String senha, Endereco endereco) {
        this.id = id;
        this.setNome(nome);
        this.cpf = cpf;
        this.telefone = telefone;
        this.senha = senha;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + getNome() + ", cpf=" + cpf + ", telefone=" + telefone + ", endereco=" + endereco + "]";
    }
}
