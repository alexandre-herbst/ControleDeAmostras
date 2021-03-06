package projeto;

public class Pessoa {

    private String nome;
    private String matricula;
    private String email;
    private String tipo;

    public Pessoa(String nome, String matricula, String email, String tipo) {
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
                "Matricula= [" + matricula + ']'+
                "  Email= [" + email + ']' +
                "  Tipo= [" + tipo + ']' + '\n';
    }
}
