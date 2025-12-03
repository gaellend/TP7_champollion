package champollion;
import java.util.HashSet;
import java.util.Set;

/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
    }
    private final Set<ServicePrevu> servicesPrevus = new HashSet<>();
    private final Set<Intervention> interventions = new HashSet<>();



    public int heuresPrevues() {
        double totalEquivalentTD = 0;

        for (ServicePrevu service : servicesPrevus) {
            totalEquivalentTD += service.getVolumeCM() * 1.5;
            totalEquivalentTD += service.getVolumeTD();
            totalEquivalentTD += service.getVolumeTP() * 0.75;
        }

        return (int) Math.round(totalEquivalentTD);
    }


    public int heuresPrevuesPourUE(UE ue) {
        double totalEquivalentTD = 0;

        for (ServicePrevu service : servicesPrevus) {
            if (service.getUe().equals(ue)) {
                totalEquivalentTD += service.getVolumeCM() * 1.5;
                totalEquivalentTD += service.getVolumeTD();
                totalEquivalentTD += service.getVolumeTP() * 0.75;
            }
        }

        return (int) Math.round(totalEquivalentTD);
    }


    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        ServicePrevu serviceTrouve = null;
        for (ServicePrevu service : servicesPrevus) {
            if (service.getUe().equals(ue)) {
                serviceTrouve = service;
                break;
            }
        }
        if (serviceTrouve != null) {
            serviceTrouve.ajouterHeures(volumeCM, volumeTD, volumeTP);
        } else {
            ServicePrevu nouveauService = new ServicePrevu(volumeCM, volumeTD, volumeTP, ue);
            servicesPrevus.add(nouveauService);
        }
    }


    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int heuresPrevues = 0;
        for (ServicePrevu service : servicesPrevus) {
            if (service.getUe().equals(ue)) {
                switch (type) {
                    case CM:
                        heuresPrevues += service.getVolumeCM();
                        break;
                    case TD:
                        heuresPrevues += service.getVolumeTD();
                        break;
                    case TP:
                        heuresPrevues += service.getVolumeTP();
                        break;
                }
            }
        }

        int heuresPlanifiees = 0;
        for (Intervention intervention : interventions) {
            if (intervention.getMatiere().equals(ue) && intervention.getType() == type) {
                heuresPlanifiees += intervention.getDuree();
            }
        }

        return heuresPrevues - heuresPlanifiees;
    }


    public void ajouteIntervention(Intervention inter) throws Exception {
        int reste = resteAPlanifier(inter.getMatiere(), inter.getType());
        if (inter.getDuree() > reste) {
            throw new Exception("Volume horaire dépassé pour " + inter.getType() + " dans l'UE " + inter.getMatiere().getIntitule());
        }
        interventions.add(inter);
    }


    public boolean enSousService() {
        return heuresPrevues() < 192;
    }

}
