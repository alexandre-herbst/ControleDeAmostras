package projeto;



import java.io.IOException;
import java.net.URISyntaxException;

import java.util.ArrayList;
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

        Arquivo arq = new Arquivo("/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasAsAmostras.txt", "/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasPessoas.txt");
        Pessoa usuarioLogado = new Pessoa("adm", "adm", "adm");


        controleCameras = arq.puxarArquivoCameras(controleCameras, "/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasAsAmostras.txt");
        listaDePessoas = arq.puxarArquivoPessoas(listaDePessoas,"/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasPessoas.txt");



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

            arq.atualizarArquivoControleDeCameras(controleCameras);
            // Sempre deve-se atualizar o as listas em memoria
            imprimeMenu();


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


                        CamerasIP emprestimo;
                        listaCamerasEmprestadas(controleCameras, usuarioLogado);
                        System.out.println("Tipo de Retirada:");
                        try {
                            emprestimo = tipoDeRetirada(controleCameras);
                        }
                        catch(IllegalArgumentException e){
                            break;
                        }

                    try {
                        if(emprestimo.getResponsavel().equals("livre")) {
                            controleCameras.alterarResponsavel(emprestimo.getSerialNumber(), usuarioLogado.getNome());
                            subirTela();
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

                    try {

                        listaCamerasEmprestadas(controleCameras, usuarioLogado);
                        System.out.println("Tipo de Devolução: ");
                        CamerasIP verifica = tipoDeRetirada(controleCameras);

                        if(verifica.getResponsavel().equals("livre")){
                            System.out.println("Esta Câmera não está emprestada");
                        }
                        else{
                            System.out.println("O Responsável por ela é :" + verifica.getResponsavel());
                            System.out.print("Deseja devolve-la? (s/n)");
                            String choice = teclado.next();
                            subirTela();
                            if(choice.equals("s")){
                                controleCameras.alterarResponsavel(verifica.getSerialNumber(),"livre");
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
                    subirTela();
                    System.out.println("Enviar lembrete para:");
                    System.out.println(listaDePessoas.toString());
                    System.out.print("Nome: ");
                    String nome = teclado.next();
                    try {
                        Pessoa lembrar = listaDePessoas.buscarPessoa(nome);
                        SMTP.enviarEmailLembrete(lembrar,controleCameras.retornaListaDoResposavel(lembrar), "Lembrete de Amostras em seu nome");
                        subirTela();
                        System.out.println("Lembrete enviado");
                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Responsavel nao encontrado");
                    }


                    break;
                case 7:

                    subirTela();
                    String modelo1 = escolheModelo();

                    ArrayList<CamerasIP> todas = controleCameras.retornaListaDoModelo(modelo1);
                    System.out.println(" ");
                    System.out.println(todas.toString());
                    System.out.println(" ");
                    segurarTela();
                    break;

                default:
                    menu++;
            }
        }





    }



    private static CamerasIP tipoDeRetirada(ControleCameras controleCameras) {


        System.out.println("1- Por Numero de Série");
        System.out.println("2- Por MAC");
        System.out.println("3- Genérica");
        System.out.println("4- Voltar");

        Scanner teclado = new Scanner(System.in);
        int opcao = teclado.nextInt();
        subirTela();

        switch (opcao){

            case 1:
                String ns = recebeNS();
                return controleCameras.buscarCamera(ns);
            case 2:
                String MAC = recebeMAC();
                return controleCameras.bucarCameraMAC(MAC);
            case 3:
                return controleCameras.retornaGenerica(escolheModelo());

            default:
                throw new IllegalArgumentException("Voltar");
        }

    }

    private static void listaCamerasEmprestadas(ControleCameras controleCameras, Pessoa responsavel){
        System.out.println("As Seguintes amostras estão em seu nome:");
        System.out.println(" ");
        for (CamerasIP camerasIP: controleCameras.retornaListaDoResposavel(responsavel)) {
            System.out.println(camerasIP.toString());
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
        System.out.println("6- Enviar Lembrete para Responsavel");
        System.out.println("7- Listar Cameras");
        System.out.println("8- Enviar Modelos para cadastro no IntelbrasCloud");
        System.out.println("9- Sair");
    }

    private static void segurarTela(){
        System.out.println("Digite qualquer coisa para sair");
        Scanner teclado = new Scanner(System.in);
        teclado.next();
    }
}
