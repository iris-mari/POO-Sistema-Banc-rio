public class ProgramaPessoa {
    public static void main(String[] args) {
        PessoaFisica pessoa1 = new PessoaFisica();
        pessoa1.setNome("João");
        pessoa1.setCpf("123.456.789-00");
        

        System.out.println("Nome: " + pessoa1.getNome());
        System.out.println("CPF: " + pessoa1.getCpf());
    }

}
