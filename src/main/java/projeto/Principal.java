package projeto;

import javafx.scene.SceneAntialiasing;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Principal {


    public static void main(String[] args) throws IOException, URISyntaxException {

// Declarações
        Scanner teclado = new Scanner(System.in);
        int menu = 0;
        int flagRemover = 0;
        int login = 0;

        ControleCameras controleCameras = new ControleCameras();
        ListaDePessoas listaDePessoas = new ListaDePessoas();

        Arquivo arq = new Arquivo("TodasAsAmostras.txt", "TodasPessoas.txt");
        Pessoa usuarioLogado = new Pessoa("adm", "adm", "adm");


        controleCameras = arq.puxarArquivoCameras(controleCameras);
        listaDePessoas = arq.puxarArquivoPessoas(listaDePessoas);



//Tela de Login
        while (login == 0){
            System.out.println("-------LOGIN-------");
            System.out.println("Ecolha uma das opcões");
            System.out.println("1- Login");
            System.out.println("2- Registrar");

            int opcao = teclado.nextInt();


            switch (opcao){
                case 1:
                    subirTela();
                    String matricula = recebeMatricula();
                  try {
                      usuarioLogado = listaDePessoas.buscarPessoa(matricula);
                      login++;
                  }
                  catch ( IllegalArgumentException e){
                      System.out.println("Usuario não encontrado");
                  }
                    break;
                case 2:
                    subirTela();
                    Pessoa novaPessoa = cadastrarPessoa();
                    listaDePessoas = arq.salvarPessoa(novaPessoa,listaDePessoas);
                    System.out.println("Responsavel Adicionado ao Registro");
                    break;

                    default:
                    System.out.println("Não foi inserida uma opção válida");
                    break;
            }



        }


        subirTela();
        System.out.println("-------------Controle de Amostras-------------");
        System.out.println("Usuario Logado: " + usuarioLogado.getNome());



//Menu
        while (menu == 0) {

            Email SMTP = new Email("controle.de.amostras.itb@gmail.com", "intelbras");


            if(flagRemover != 0){

               System.out.print("Informe o Numero de Série da Camera que deseja remover: ");

               String NS = teclado.next();
               if(controleCameras.removerCamerasIP(NS)){
                   System.out.println("Camera removida");
               }
               else System.out.println("NS não encontrado para ser removido");

               flagRemover = 0;
           }


            // Sempre deve-se atualizar o as listas em memoria
            imprimeMenu();
            arq.atualizarArquivoControleDeCameras(controleCameras);

            int opcao = teclado.nextInt();

            switch (opcao) {
                case 1:
                    subirTela();
                    String modelo = escolheModelo();
                    subirTela();
                    String numeroDeSerie = recebeNS();
                    subirTela();
                    String MAC = recebeMAC();
                    subirTela();

                    controleCameras = arq.salvarCamera(new CamerasIP(modelo,numeroDeSerie,MAC), controleCameras);

                    break;

                case 2:
                    flagRemover++;
                    subirTela();
                    break;
                case 3:
                    subirTela();

                    try {
                        CamerasIP buscada = controleCameras.buscarCamera(recebeNS());
                        System.out.println("Informações da câmera: ");
                        System.out.println(buscada.toString());
                        System.out.println(" ");
                        segurarTela();
                    }
                    catch (IllegalArgumentException e){
                    System.out.println("Numero de Série não encontrado");
                }
                    break;

                case 4:
                    subirTela();
                    try {
                        String ns = recebeNS();
                        CamerasIP emprestimo = controleCameras.buscarCamera(ns);

                        if(emprestimo.getResponsavel().equals("livre")) {
                            controleCameras.alterarResponsavel(ns, usuarioLogado.getNome());
                            System.out.println("Amostra retirada em nome de: " + usuarioLogado.getNome());
                            SMTP.enviarEmailLembrete(usuarioLogado, controleCameras.retornaListaDoResposavel(usuarioLogado), "Retirada de Amostra CA");
                        }
                        else{
                            System.out.println("Câmera já emprestada, verifique com Responsável (" + emprestimo.getResponsavel() + ")");
                        }

                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Numero de Série não encontrado");
                    }
                    break;

                case 5:
                    subirTela();
                    String recebe = recebeNS();
                    try {
                        CamerasIP verifica = controleCameras.buscarCamera(recebe);
                        if(verifica.getResponsavel().equals("livre")){
                            System.out.println("Esta Câmera não está emprestada");
                        }
                        else{
                            System.out.println("O Responsável por ela é :" + verifica.getResponsavel());
                            System.out.print("Deseja devolve-la? (S/N)");
                            String choice = teclado.next();
                            if(choice.equals("S")){
                                controleCameras.alterarResponsavel(recebe,"livre");
                                System.out.println("Camera Devolvida.");
                                SMTP.enviarEmailLembrete(usuarioLogado, controleCameras.retornaListaDoResposavel(usuarioLogado), "Amostra Devolvida ao CA");
                            }
                        }
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Numero de Série não encontrado");
                    }
                    break;

                case 6:


                    break;
                case 7:
                    System.out.println(controleCameras.toString());
                    System.out.println("Digite qualquer coisa para voltar");
                    teclado.next();
                    break;

                default:
                    menu++;
            }
        }





    }



    private static void subirTela(){
        for (int i = 0; i <50 ; i++) {
            System.out.println("  ");
        }
    }

    private static String escolheModelo(){
        imprimeModelos();
        System.out.print("Selecione o Modelo da Câmera (pelo numero): ");
        Scanner teclado = new Scanner(System.in);
        int numero = teclado.nextInt();
        switch (numero){
            case 1 : return "VIP 5550 DZ IA";
            case 2 : return "VIP 5550 Z IA";
            case 3 : return "VIP 3430 D G2";
            case 4 : return "VIP 3430 B G2";
            case 5 : return "VIP 3230 D G2";
            case 6 : return "VIP 3230 B G2";
            case 7 : return "VIP 3240 D IA";
            case 8 : return "VIP 3240 IA";
            default: throw new IllegalArgumentException("Numero não Válido");
        }
    }

    private static void imprimeModelos(){
        System.out.println("1 - VIP 5550 DZ IA");
        System.out.println("2 - VIP 5550 Z IA");
        System.out.println("3 - VIP 3430 D G2");
        System.out.println("4 - VIP 3430 B G2");
        System.out.println("5 - VIP 3230 D G2");
        System.out.println("6 - VIP 3230 B G2");
        System.out.println("7 - VIP 3240 D IA");
        System.out.println("8 - VIP 3240  IA");
    }

    private static String recebeNS(){
        System.out.println("Informe o Numero de Série: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.next();
    }

    private static String recebeMAC(){
        System.out.println("Informe o MAC: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.next();
    }

    private static String recebeMatricula(){
        System.out.println("Informe sua matricula:");
        Scanner teclado = new Scanner(System.in);
        return teclado.next();
    }

    private static Pessoa cadastrarPessoa(){

        System.out.println("Informe o nome do usuário: ");
        Scanner teclado = new Scanner(System.in);

        String nome = teclado.next();
        subirTela();

        System.out.println("Informe a matricula: ");
        String matricula = teclado.next();
        subirTela();

        System.out.println("Informe o endereco de email: ");
        String email = teclado.next();

        return new Pessoa(nome, matricula,email);
    }

    private static void imprimeMenu(){

        System.out.println("Escolha entre uma das opções");
        System.out.println("1- Inserir uma Câmera no Armário");
        System.out.println("2- Remover uma Câmera no Armário");
        System.out.println("3- Buscar por uma câmera");
        System.out.println("4- Retirar uma Camera para empréstimo");
        System.out.println("5- Devolver uma Câmera");
        System.out.println("6- Enviar email");
        System.out.println("7- Listar todas as Cameras");
        System.out.println("8- Sair");
    }

    private static void segurarTela(){
        System.out.println("Digite qualquer coisa para sair");
        Scanner teclado = new Scanner(System.in);
        teclado.next();
    }
}
