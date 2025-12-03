package champollion;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class ChampollionJUnitTest {
    Enseignant untel;
    UE uml, java;
    Salle salle;

    @BeforeEach
    public void setUp() {
        untel = new Enseignant("untel", "untel@gmail.com");
        uml = new UE("UML");
        java = new UE("Programmation en java");
        salle = new Salle("Salle 101", 20);
    }

    @Test
    public void testNouvelEnseignantSansService() {
        assertEquals(0, untel.heuresPrevues(),
            "Un nouvel enseignant doit avoir 0 heures prévues");
    }

    @Test
    public void testAjouteHeures() {
        // 10h TD pour UML
        untel.ajouteEnseignement(uml, 0, 10, 0);

        assertEquals(10, untel.heuresPrevuesPourUE(uml),
            "L'enseignant doit avoir 10 heures prévues pour l'UE 'uml'");

        // On rajoute 20h TD pour UML (Cumul)
        untel.ajouteEnseignement(uml, 0, 20, 0);

        assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
            "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");
    }

    @Test
    public void testHeuresPrevues() {
        untel.ajouteEnseignement(uml, 10, 20, 20);

        assertEquals(50, untel.heuresPrevues(),
            "Le calcul des heures équivalent TD est incorrect");
    }

    @Test
    public void testHeuresPrevuesArrondi() {
        untel.ajouteEnseignement(java, 1, 0, 1);
        assertEquals(2, untel.heuresPrevues(),
            "L'arrondi à l'entier le plus proche n'est pas correct");
    }

    @Test
    public void testSousService() {
        untel.ajouteEnseignement(uml, 0, 191, 0);
        assertTrue(untel.enSousService(),
            "L'enseignant devrait être en sous-service (< 192h)");

        untel.ajouteEnseignement(uml, 0, 1, 0);
        assertFalse(untel.enSousService(),
            "L'enseignant ne devrait plus être en sous-service (>= 192h)");
    }

    @Test
    public void testAjoutIntervention() {

        untel.ajouteEnseignement(uml, 0, 20, 0);

        Intervention inter = new Intervention(salle, uml, untel, TypeIntervention.TD, 2, new Date(), 10);

        assertDoesNotThrow(() -> untel.ajouteIntervention(inter));

        assertEquals(18, untel.resteAPlanifier(uml, TypeIntervention.TD),
            "Le reste à planifier est incorrect après ajout d'une intervention");
    }

    @Test
    public void testAjoutInterventionDepassement() {
        untel.ajouteEnseignement(uml, 0, 10, 0);

        Intervention interTropLongue = new Intervention(salle, uml, untel, TypeIntervention.TD, 12, new Date(), 10);

        Exception exception = assertThrows(Exception.class, () -> {
            untel.ajouteIntervention(interTropLongue);
        }, "Une exception doit être levée si le volume horaire est dépassé");
    }

    @Test
    public void testResteAPlanifier() {
        untel.ajouteEnseignement(uml, 10, 0, 0);

        assertEquals(10, untel.resteAPlanifier(uml, TypeIntervention.CM));

        try {
            untel.ajouteIntervention(new Intervention(salle, uml, untel, TypeIntervention.CM, 4, new Date(), 8));
        } catch (Exception e) {
            fail("L'ajout d'intervention valide ne devrait pas échouer");
        }

        assertEquals(6, untel.resteAPlanifier(uml, TypeIntervention.CM));
    }
}
