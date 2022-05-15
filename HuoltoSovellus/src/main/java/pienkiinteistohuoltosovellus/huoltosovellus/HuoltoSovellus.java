package pienkiinteistohuoltosovellus.huoltosovellus;

import domain.database.DatabaseController;
import pienkiinteistohuoltosovellus.ui.UserInterface;


public class HuoltoSovellus {

    public static void main(String[] args) {
        DatabaseController.initializeDatabase(null);
        UserInterface.main(args);       

    }
}
