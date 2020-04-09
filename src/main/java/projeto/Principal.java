package projeto;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {

        ControleCameras controleCameras = new ControleCameras();
        String caminho = "TodasAsAmostras.txt";
        Arquivo arq = new Arquivo(caminho);
        arq.puxarArquivo(controleCameras);

        Pessoa pessoa1 = new Pessoa("Alexandre", "al053681", "alexandre.carvalho@intelbras.com.br");
        Pessoa pessoa2 = new Pessoa("Leonardo", "le052354", "leo@intelbras.com.br");
        Pessoa pessoa3 = new Pessoa("Marcos", "ma053254", "marcos.soares@intelbras.com.br");

        ListaDePessoas listaDePessoas = new ListaDePessoas();
        listaDePessoas.inserirPessoa(pessoa1);
        listaDePessoas.inserirPessoa(pessoa2);
        listaDePessoas.inserirPessoa(pessoa3);

        Pessoa pessoa4 = listaDePessoas.buscarPessoa("Alexandre");
        System.out.println(pessoa4.toString());
        pessoa4 = listaDePessoas.buscarPessoa("al053681");
        System.out.println(pessoa4.toString());
        System.out.println(" ");

        System.out.println(listaDePessoas.toString());
        listaDePessoas.removerPessoa(pessoa1);
        System.out.println(" ");
        System.out.println(listaDePessoas.toString());

    }
}
