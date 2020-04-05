package projeto;

public class CamerasIP {

    private String modelo;
    private String serialNumber;
    private String MAC;
    private String local = "armario";


    public CamerasIP(String modelo, String serialNumber, String MAC) {
        this.MAC = MAC;
        this.modelo = modelo;
        this.serialNumber = serialNumber;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "CamerasIP " +
                ", Modelo= [" + modelo +
                "] Numero De Série= ["  + serialNumber +
                "] MAC= [" + MAC +
                "] Local = [" + local + "]";
    }
}
