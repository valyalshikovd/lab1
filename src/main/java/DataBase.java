import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            String userName = "root";
            String password = "30102023";
            String connectionUrl = "jdbc:mysql://localhost:3306/product";
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        }
        return connection;
    }
    public void add(Product product) throws SQLException {

        String sql = "SELECT * FROM product WHERE product_name = ?";
        PreparedStatement statement1 = getConnection().prepareStatement(sql);
        statement1.setString(1, product.getName());
        ResultSet resultSet = statement1.executeQuery();


        if(resultSet.next()){
            int id = resultSet.getInt("idproduct");
            int countCurrent = resultSet.getInt("product_count");
            sql = "UPDATE product SET product_count = ? WHERE idproduct = ?";
            PreparedStatement statement2 = getConnection().prepareStatement(sql);
            statement2.setInt(1, countCurrent + product.getCount());
            statement2.setInt(2, id);
            statement2.executeUpdate();
            System.out.println("каво");
            return;
        }



        sql = "INSERT INTO product ( product_name, product_type, product_price, product_count) VALUES ( ?, ?, ?, ?)";
        PreparedStatement statement3 = getConnection().prepareStatement(sql);
        statement3.setString(1, product.getName());
        statement3.setString(2, product.getType());
        statement3.setInt(3, product.getPrice());
        statement3.setInt(4, product.getCount());
        int rowsAffected = statement3.executeUpdate();
        System.out.println(rowsAffected);
    }
    public List<Product> find(String s) throws SQLException {
        String sql = "SELECT * FROM product WHERE name = ?";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setString(1, s);
        ResultSet resultSet = statement.executeQuery();
        List<Product> res = new LinkedList<>();
        while (resultSet.next()) {
            res.add(new Product(resultSet.getString("product_name"), resultSet.getString("product_type"), resultSet.getInt("product_price"), resultSet.getInt("product_count")));
        }
        if(res.size() > 1){
            throw new RuntimeException("Что то не так с уникальностью продуктов в базе данных...");
        }
        return res;
    }
}
