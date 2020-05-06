package projeto;



import java.io.IOException;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Principal {


    public static void main(String[] args) throws IOException, URISyntaxException {

// Declarações


        Date instanteAtual;
        Scanner teclado = new Scanner(System.in);
        int menu = 0;
        int flagRemover = 0;
        int login = 0;

        ControleCameras controleCameras = new ControleCameras();
        ListaDePessoas listaDePessoas = new ListaDePessoas();

        String caminho1 = "C:\\Users\\al053681\\ControleDeAmostras\\src\\main\\resources\\TodasAsAmostras.txt";
        String caminho2 = "C:\\Users\\al053681\\ControleDeAmostras\\src\\main\\resources\\TodasPessoas.txt";

        String caminho3 = "/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasAsAmostras.txt";
        String caminho4 = "/Users/alexandreherbst/Desktop/Controle De Amostras/ControleDeAmostras/src/main/resources/TodasPessoas.txt";
        Arquivo arq = new Arquivo(caminho3, caminho4);
        Pessoa usuarioLogado = new Pessoa("adm", "adm", "adm");


        controleCameras = arq.puxarArquivoCameras(controleCameras, caminho3);
        listaDePessoas = arq.puxarArquivoPessoas(listaDePessoas,caminho4);



//Tela de Login
        while (login == 0){
            System.out.println("-------LOGIN-------");
            System.out.println("ESCOLHA UMA OPCAO");
            System.out.println("[1] - Login");
            System.out.println("[2] - Registrar");

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
        System.out.println("________________________________________________");
        System.out.println("|             Controle de Amostras             |");
        System.out.println("------------------------------------------------");
        System.out.println("Usuario Logado: " + usuarioLogado.getNome() + " \n");



//Menu
        while (menu == 0) {

            Email SMTP = new Email("controle.de.amostras.itb@gmail.com", "intelbras");


            if(flagRemover != 0){

               System.out.print("Informe o Numero de Serie da Camera que deseja remover: ");

               String NS = teclado.next();
               if(controleCameras.removerCamerasIP(NS)){
                   System.out.println("Camera removida");
               }
               else System.out.println("NS nao encontrado para ser removido");

               flagRemover = 0;
           }

            arq.atualizarArquivoControleDeCameras(controleCameras);
            // Sempre deve-se atualizar o as listas em memoria
            imprimeMenu();


            int opcao = teclado.nextInt();

            switch (opcao) {


                case 1:
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
                            System.out.println("Câmera ja emprestada, verifique com Responsavel (" + emprestimo.getResponsavel() + ")");
                        }

                    }
                    catch (IllegalArgumentException e){
                        System.out.println("Numero de Série não encontrado");
                    }
                    break;


                case 2:
                    subirTela();

                    try {

                        listaCamerasEmprestadas(controleCameras, usuarioLogado);
                        System.out.println("Tipo de Devolução: ");
                        CamerasIP verifica = tipoDeRetirada(controleCameras);

                        if(verifica.getResponsavel().equals("livre")){
                            System.out.println("Esta Camera nao esta emprestada");
                        }
                        else{
                            System.out.println("O Responsavel por ela é :" + verifica.getResponsavel());
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
                        System.out.println("Numero de Serie não encontrado");
                    }
                    break;

                case 3:

                    subirTela();
                    String ns = recebeNS();
                    subirTela();
                    System.out.println("Qual o novo local?");
                    System.out.println("[1] - Armario P&D");
                    System.out.println("[2] - Armario de Tras");
                    System.out.println("[3] - Bancada");

                    String local = "armario";
                    opcao = teclado.nextInt();
                    if(opcao == 1) local = "Armario P&D";
                    if(opcao == 2) local = "Armario de Tras";
                    if(opcao == 3) local = "Bancada";

                    controleCameras.buscarCamera(ns).setLocal(local);
                    break;



                case 4:
                    subirTela();
                    System.out.println("[1] - Adicionar Camera");
                    System.out.println("[2] - Remover Camera");
                    opcao = teclado.nextInt();
                    if(opcao == 1) {
                        int a = 0;

                        while (a == 0) {
                            subirTela();
                            String modelo = escolheModelo();
                            subirTela();
                            String numeroDeSerie = recebeNS();
                            subirTela();
                            String MAC = recebeMAC();
                            subirTela();

                            controleCameras = arq.salvarCamera(new CamerasIP(modelo, numeroDeSerie, MAC), controleCameras);
                            System.out.println("Deseja adicionar outra? (s/n)");
                            String decisao = teclado.next();
                            if (decisao.equals("n")) {
                                a++;
                            }
                        }
                        break;
                    }
                    if(opcao == 2){
                        flagRemover++;
                        subirTela();
                        break;
                    }


                case 5:
                    subirTela();

                    try {
                        CamerasIP buscada = controleCameras.buscarCamera(recebeNS());
                        System.out.println("Informacoes da camera: ");
                        System.out.println(buscada.toString());
                        System.out.println(" ");
                        segurarTela();
                    }
                    catch (IllegalArgumentException e){
                    System.out.println("Numero de Serie não encontrado");
                }
                    break;


                case 6:
                    subirTela();
                    System.out.println("Enviar lembrete para:");
                    System.out.println(listaDePessoas.toString2());
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
                    System.out.println(" ");
                    System.out.println(todas.toString());
                    System.out.println(" ");
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
        System.out.println("3- Voltar");

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

            default:
                throw new IllegalArgumentException("Voltar");
        }

    }

    private static void listaCamerasEmprestadas(ControleCameras controleCameras, Pessoa responsavel){
        System.out.println("As Seguintes amostras estao em seu nome:");
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
        System.out.print("Selecione o Modelo da Camera (pelo numero): ");
        Scanner teclado = new Scanner(System.in);
        int numero = teclado.nextInt();
        switch (numero){
            case 1 : return "VIP 5550 DZ IA";
            case 2 : return "VIP 5550 Z IA";
            case 3 : return "VIP 5450 DZ G2";
            case 4 : return "VIP 5450 Z G2";
            case 5 : return "VIP 5850 B";

            case 6 : return "VIP 3430 D G2";
            case 7 : return "VIP 3430 B G2";
            case 8 : return "VIP 3430 D";
            case 9 : return "VIP 3430 B";


            case 10 : return "VIP 3230 D G2";
            case 11 : return "VIP 3230 B G2";
            case 12 : return "VIP 3230 D";
            case 13 : return "VIP 3230 B";


            case 14 : return "VIP 3240 D IA";
            case 15 : return "VIP 3240 IA";


            default: throw new IllegalArgumentException("Numero não Válido");
        }
    }

    private static void imprimeModelos(){

        System.out.println("-------Linha 5000-------");

        System.out.print("1 - VIP 5550 DZ IA");
        System.out.println("    2 - VIP 5550 Z IA");
        System.out.print("3 - VIP 5450 DZ G2");
        System.out.println("    4 - VIP 5450 Z G2");
        System.out.println("5 - VIP 5850 B");
        System.out.println(" ");

        System.out.println("-------Linha 3000-------");

        System.out.println("3430");
        System.out.print("6 - VIP 3430 D G2");
        System.out.println("    7 - VIP 3430 B G2");
        System.out.print("8 - VIP 3430 D");
        System.out.println("       9 - VIP 3430 B");
        System.out.println(" ");

        System.out.println("3230");
        System.out.print("10 - VIP 3230 D G2");
        System.out.println("   11 - VIP 3230 B G2");
        System.out.print("12 - VIP 3230 D");
        System.out.println("      13 - VIP 3230 B");
        System.out.println(" ");

        System.out.println("3240");
        System.out.print("14 - VIP 3240 D IA");
        System.out.println("   15 - VIP 3240  IA");
        System.out.println(" ");



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

        System.out.println("\t\t\tESCOLHA UMA OPCAO");
        System.out.println("[1] - Retirar uma Camera para emprestimo");
        System.out.println("[2] - Devolver uma Camera");
        System.out.println("[3] - Alterar Local");
        System.out.println("[4] - Inserir/Remover uma Camera no Armario");
        System.out.println("[5] - Buscar por uma camera");
        System.out.println("[6] - Enviar Lembrete para Responsavel");
        System.out.println("[7] - Listar Cameras");
        System.out.println("[8] - Enviar Modelos para cadastro no IntelbrasCloud");
        System.out.println("[9] - Sair");
    }

    private static void segurarTela(){
        System.out.println("Digite qualquer coisa para sair");
        Scanner teclado = new Scanner(System.in);
        teclado.next();
    }
}
