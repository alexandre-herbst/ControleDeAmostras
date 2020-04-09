package projeto;

import java.io.*;
import java.util.Scanner;

public class Arquivo {

    private File arquivo;

    public Arquivo(String nomeArquivo) {
        this.arquivo = new File(nomeArquivo);
    }

    public void puxarArquivo(ControleCameras listaDeCameras)
    {
        try {
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                listaDeCameras.addCamera(montaCamera(linha));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void imprimeConteudoArquivo() {
        try {
            Scanner leitor = new Scanner(arquivo);
            while (leitor.hasNext()) {
                String linha = leitor.nextLine();
                System.out.println(linha);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void salvarCamera(CamerasIP camerasIP) {//throws IOException {

        FileWriter fwArquivo = null;
        BufferedWriter bw;
        try {
            if (!arquivo.exists()) {
                fwArquivo = new FileWriter(arquivo, !arquivo.exists()); //parametro do tipo FILE
                bw = new BufferedWriter(fwArquivo);
                bw.write(camerasIP.toString() + "\n");
                bw.close();
                fwArquivo.close();


            } else {
                // se true, ele concatena, se false ele cria ou zera o arquivo
                fwArquivo = new FileWriter(arquivo, true); //parametro do tipo FILE
                bw = new BufferedWriter(fwArquivo);
                bw.write(camerasIP.toString() + "\n");
                bw.close();
                fwArquivo.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    //public void salvarCamera(CamerasIP camerasIP) throws IOException {
//
//        try {
//
//            FileOutputStream fout = new FileOutputStream(arquivo);
//            ObjectOutputStream oos = new ObjectOutputStream(fout);
//
//            oos.writeObject(camerasIP);
//
//            oos.flush(); //Descarregar o flush
//            oos.close();
//            fout.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public CamerasIP lerCamerasIP() throws IOException, ClassNotFoundException {
        try {

            FileInputStream fis = new FileInputStream(arquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CamerasIP p = (CamerasIP) ois.readObject();
            ois.close();
            fis.close();
            return p;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CamerasIP montaCamera(String linha){

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

}