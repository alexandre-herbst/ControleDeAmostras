package projeto;

public class CamerasIP {

    private String modelo;
    private String serialNumber;
    private String MAC;
    private int resolucao;
    private int IR;
    private String firmWare;

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

    public int getResolucao() {
        return resolucao;
    }

    public void setResolucao(int resolucao) {
        this.resolucao = resolucao;
    }

    public int getIR() {
        return IR;
    }

    public void setIR(int IR) {
        this.IR = IR;
    }

    public String getFirmWare() {
        return firmWare;
    }

    public void setFirmWare(String firmWare) {
        this.firmWare = firmWare;
    }

    @Override
    public String toString() {
        return "CamerasIP{" +
                "modelo='" + modelo + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", MAC='" + MAC + '\'' +
                ", resolucao=" + resolucao +
                ", IR=" + IR +
                ", firmWare='" + firmWare + '\'' +
                '}';
    }
}
