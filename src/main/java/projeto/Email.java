package projeto;


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

    public void enviarEmailLembrete(Pessoa responsavel, ArrayList<CamerasIP> listaDoResponsavel, String titulo){

         try {
             email.setFrom(enderecoAdm);
             email.setSubject(titulo);

             StringBuilder sb =  new StringBuilder();
             sb.append("As seguintes amostras estão no seu nome: \n\n");

             for (CamerasIP cameraip : listaDoResponsavel) {
                 sb.append(cameraip.toString()).append('\n');
             }

            
             email.setMsg(sb.toString());
             email.addTo(responsavel.getEmail());
             email.buildMimeMessage();
             System.out.println("Enviando Email...");
             email.sendMimeMessage();

         }
         catch (Exception e){
             e.printStackTrace();
         }

    }


}
