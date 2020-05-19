package projeto;


import com.sun.mail.util.MailConnectException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.ArrayList;

public class Email {


    private String enderecoAdm;
    private String senha;
    private String destino;
    private SimpleEmail email;

    public Email(String enderecoAdm, String senha){
        this.enderecoAdm = enderecoAdm;
        this.senha = senha;
        this.email = new SimpleEmail();

        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(456);
        email.setAuthentication(enderecoAdm, senha);
        email.setSSLOnConnect(true);
    }

    public void enviarEmailLembrete(Pessoa responsavel, ArrayList<CamerasIP> listaDoResponsavel, String titulo, int modo){




         try {
             email.setFrom(enderecoAdm);
             email.setSubject(titulo);

             StringBuilder sb =  new StringBuilder();
             if(modo == 1) {
                 sb.append("Amostra do PeD retirada para emprestimo\n");
                 sb.append("As seguintes amostras estão no seu nome: \n\n");
             }
             if(modo == 2){
                 sb.append("Amostra emprestada foi devolvida ao PeD\n").append("As seguintes amostras estão no seu nome: \n\n");
             }
             if(modo == 3){
                 sb.append("Você esta sendo lembrado que as seguintes amostras estão no seu nome: \n\n");
             }
             for (CamerasIP cameraip : listaDoResponsavel) {
                 sb.append(cameraip.toString()).append('\n');
             }

            
             email.setMsg(sb.toString());
             email.addTo(responsavel.getEmail());
             email.buildMimeMessage();
             System.out.println("Enviando Email...");
             email.sendMimeMessage();

         }
         catch (EmailException mailConnectException){
             System.out.println("Não foi possivel enviar email, verifique sua conexao");
         }

    }

    public void enviarEmailAdm(Pessoa responsavel ,ArrayList<CamerasIP> listaDoResponsavel, String titulo){

        Pessoa adm = new Pessoa("Controle", "Controle", "controle.de.amostras.itb@gmail.com", "adm");

        try {
            email.setFrom(enderecoAdm);
            email.setSubject(titulo);

            StringBuilder sb =  new StringBuilder();
            sb.append("As seguintes amostras foram retiradas no nome de ").append(responsavel.getNome()).append( " : \n\n");

            for (CamerasIP cameraip : listaDoResponsavel) {
                sb.append(cameraip.toString()).append('\n');
            }


            email.setMsg(sb.toString());
            email.addTo(responsavel.getEmail());
         //   email.buildMimeMessage();
            email.sendMimeMessage();

        }
        catch (EmailException mailConnectException){
            System.out.println("Não foi possivel enviar email, verifique sua conexao");
        }

    }



}
