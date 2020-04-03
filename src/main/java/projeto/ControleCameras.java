package projeto;

import java.util.ArrayList;

public class ControleCameras {
    private ArrayList<CamerasIP> listaDeCamerasIP = new ArrayList();

    public boolean addCamera(CamerasIP c){
       if(listaDeCamerasIP.add(c)) return true;
       else return false;
    }

    public  boolean removerCamerasIP(String numeroDeSerie){
        CamerasIP camerasIP = procuraPorNS(numeroDeSerie);
        return listaDeCamerasIP.remove(camerasIP);
    }

    public  boolean removerCamerasIP(CamerasIP camerasIP){
        return listaDeCamerasIP.remove(camerasIP);
    }




    public CamerasIP procuraPorNS(String numeroDeSerie){
        for (CamerasIP camerasIP : listaDeCamerasIP){
            if(numeroDeSerie.equals(camerasIP.getSerialNumber())){
                return camerasIP;
            }
        }
        throw new IllegalArgumentException("Numero de série não encontrado");
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (CamerasIP camerasIP : listaDeCamerasIP) {
            sb.append('\n').append("Modelo:  ").append(camerasIP.getModelo())
                    .append("   Numero de Serie: ").append(camerasIP.getSerialNumber())
                    .append("   MAC:  ").append(camerasIP.getMAC());
        }
        return sb.toString();
    }
}
