package champollion;

public class ServicePrevu {
    private int volumeCM;
    private int volumeTD;
    private int volumeTP;
    private final UE ue;


    public ServicePrevu(int volumeCM, int volumeTD, int volumeTP, UE ue) {
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
        this.ue = ue;
    }


    public void ajouterHeures(int cm, int td, int tp) {
        this.volumeCM += cm;
        this.volumeTD += td;
        this.volumeTP += tp;
    }


    public int getVolumeCM() {return volumeCM;}
    public int getVolumeTD() {return volumeTD;}
    public int getVolumeTP() {return volumeTP;}
    public UE getUe() {return ue;}

}
