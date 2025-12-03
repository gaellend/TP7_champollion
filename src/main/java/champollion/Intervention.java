package champollion;

import java.util.Date;

public class Intervention {
    private Date debut;
    private int duree;
    private boolean annulee = false;
    private int heureDebut;

    // ReadOnly
    private final UE matiere;
    private final Enseignant intervenant;

    private Salle lieu;
    private TypeIntervention type;

    public Intervention(Salle lieu, UE matiere, Enseignant intervenant, TypeIntervention type, int duree, Date debut, int heureDebut) {
        this.lieu = lieu;
        this.matiere = matiere;
        this.intervenant = intervenant;
        this.type = type;
        this.duree = duree;
        this.debut = debut;
        this.heureDebut = heureDebut;
    }


    public Date getDebut() { return debut; }
    public int getDuree() { return duree; }
    public boolean isAnnulee() { return annulee; }
    public int getHeureDebut() { return heureDebut; }
    public UE getMatiere() { return matiere; }
    public Enseignant getIntervenant() { return intervenant; }
    public Salle getLieu() { return lieu; }
    public TypeIntervention getType() { return type; }


    public void setDebut(Date debut) { this.debut = debut; }
    public void setDuree(int duree) { this.duree = duree; }
    public void setAnnulee(boolean annulee) { this.annulee = annulee; }
    public void setHeureDebut(int heureDebut) { this.heureDebut = heureDebut; }
    public void setLieu(Salle lieu) { this.lieu = lieu; }
    public void setType(TypeIntervention type) { this.type = type; }
}
