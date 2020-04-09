package projeto;

public class Pessoa {

    private String nome;
    private String matricula;
    private String email;

    public Pessoa(String nome, String matricula, String email) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Pessoa - " +
                "Nome= [" + nome + ']' +
                "  Matricula= [" + matricula + ']' +
                "  Email= [" + email + ']' + '\n';
    }
}
