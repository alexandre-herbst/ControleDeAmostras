package projeto;

import java.util.ArrayList;

public class ListaDePessoas {

    private ArrayList<Pessoa> listaDePessoas = new ArrayList<Pessoa>();

    public void inserirPessoa(Pessoa pessoa) {
        listaDePessoas.add(pessoa);
    }

    public ArrayList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }

    public Pessoa buscarPessoa(String nomeOUmatricula){
        for (Pessoa pessoa : listaDePessoas) {
            if(pessoa.getNome().equals(nomeOUmatricula)) return pessoa;
            if(pessoa.getMatricula().equals(nomeOUmatricula)) return pessoa;
        }
        throw new IllegalArgumentException("Nome não cadastrado");
    }
    public boolean removerPessoa(Pessoa pessoa){
        return listaDePessoas.remove(pessoa);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pessoa p : listaDePessoas) {
            sb.append(p.toString());
        }
        return sb.toString();
    }

    public String toString2() {
        StringBuilder sb = new StringBuilder();
        for (Pessoa p : listaDePessoas) {
            sb.append(p.getNome()).append('\n');
        }
        return sb.toString();
    }


}
