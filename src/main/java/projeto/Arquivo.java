package projeto;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Arquivo {

    private File arquivoDeCameras;
    private File arquivoDePessoas;


    public Arquivo(String arquivoCameras, String arquivoPessoas) {
        this.arquivoDeCameras = new File(arquivoCameras);
        this.arquivoDePessoas = new File(arquivoPessoas);
    }



    public ControleCameras puxarArquivoCameras(ControleCameras listaDeCameras) throws FileNotFoundException {

            listaDeCameras = new ControleCameras();

            Scanner leitor = new Scanner(arquivoDeCameras);
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                listaDeCameras.addCamera(montaCamera(linha));
            }
            return  listaDeCameras;

        }

    public ListaDePessoas puxarArquivoPessoas(ListaDePessoas listaDePessoas) throws FileNotFoundException {
        listaDePessoas = new ListaDePessoas();
        Scanner leitor = new Scanner(arquivoDePessoas);
        while (leitor.hasNext()) {
            String linha = leitor.nextLine();
            listaDePessoas.inserirPessoa(montaPessoa(linha));
        }
        return listaDePessoas;
    }

    //NOK
    public void atualizarArquivoControleDeCameras(ControleCameras controleCameras) throws IOException {


        FileWriter arq = new FileWriter(arquivoDeCameras.getName());
        PrintWriter gravarArq = new PrintWriter(arq);
        String arquivoNovo = controleCameras.toString();

        gravarArq.printf(arquivoNovo);

        arq.close();


    }


    /**
     * @param modo = 1 imprime arquivo de cameras
     *             = 2 imprime arquivo de pessoas
     */
    public  String imprimeConteudoArquivo(int modo) {
        try {
            switch (modo) {
                case 1:
                   Scanner leitor = new Scanner(arquivoDeCameras);
                    StringBuilder sb = new StringBuilder();
                    while (leitor.hasNext()) {
                        String linha = leitor.nextLine();
                        sb.append(linha).append('\n');
                    }
                    return sb.toString();

                case 2:
                     Scanner leitor2 = new Scanner(arquivoDePessoas);
                    StringBuilder sb2 = new StringBuilder();
                    while (leitor2.hasNext()) {
                        String linha = leitor2.nextLine();
                        sb2.append(linha).append('\n');
                    }
                    return sb2.toString();

                default:
                    throw new IllegalStateException("Valor invalido " + modo);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public ControleCameras salvarCamera(CamerasIP camerasIP, ControleCameras controleCameras) throws IOException   {//throws IOException {

           FileWriter fwArquivo = null;
           BufferedWriter bw;

           if (!arquivoDeCameras.exists()) {
               fwArquivo = new FileWriter(arquivoDeCameras, !arquivoDeCameras.exists()); //parametro do tipo FILE
               bw = new BufferedWriter(fwArquivo);
               bw.write(camerasIP.toString() + "\n");
               bw.close();
               fwArquivo.close();


           } else {
               // se true, ele concatena, se false ele cria ou zera o arquivo
               fwArquivo = new FileWriter(arquivoDeCameras, true); //parametro do tipo FILE
               bw = new BufferedWriter(fwArquivo);
               bw.write(camerasIP.toString() + "\n");
               bw.close();
               fwArquivo.close();
           }

       controleCameras.addCamera(camerasIP);
       return controleCameras;
    }

    public void salvarPessoa(){


    }



    private CamerasIP montaCamera(String linha){

        String modelo = linha.substring(linha.indexOf('[') + 1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String numeroDeSerie = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String MAC = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+1,linha.lastIndexOf(']')+1);

        String local = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));

        CamerasIP camerasIP = new CamerasIP(modelo,numeroDeSerie,MAC);
        camerasIP.setLocal(local);
        return camerasIP;
    }
    private Pessoa montaPessoa(String linha){

        String nome = linha.substring(linha.indexOf('[') + 1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String matricula = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String email = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));

        return new Pessoa(nome, matricula, email);

    }


}