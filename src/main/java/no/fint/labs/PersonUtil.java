package no.fint.labs;

import no.fint.model.felles.kompleksedatatyper.Personnavn;

public class PersonUtil {
    public static String getNavn(Personnavn personnavn) {
        if (personnavn.getMellomnavn() != null)
            return String.format("%s %s %s", personnavn.getFornavn(), personnavn.getMellomnavn(), personnavn.getEtternavn());
        return String.format("%s %s", personnavn.getFornavn(), personnavn.getEtternavn());
    }
}
