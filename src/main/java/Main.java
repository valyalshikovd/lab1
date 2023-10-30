import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        new DataBase().add( new Product("dasfa", "asf", 10, 2));
        new DataBase().add( new Product("dasfssssa", "asf", 10, 34));
        new DataBase().add( new Product("dasfa", "asf", 10, 34));

    }
}
