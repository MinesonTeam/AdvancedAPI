package kz.hxncus.mc.minesonapi.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class MariaDBTest {
    public MariaDB mariaDB;
    @BeforeAll
    public void createConnection() {
        this.mariaDB = new MariaDB("0.0.0.0", "3306", "mineson", "root", "", null, null);
    }

    @AfterAll
    public void closeConnection() {
        this.mariaDB.closeConnection();
    }
}
