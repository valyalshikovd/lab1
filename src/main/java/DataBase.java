import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    public Product find(String s) throws SQLException {
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
        return res.get(0);
    }
    public void delete(Product product) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_name = ?";
        PreparedStatement statement1 = getConnection().prepareStatement(sql);
        statement1.setString(1, product.getName());
        ResultSet resultSet = statement1.executeQuery();


        if(resultSet.next()){
            int id = resultSet.getInt("idproduct");
            int countCurrent = resultSet.getInt("product_count");
            if(countCurrent == product.getCount()){
                sql = "DELETE FROM product WHERE idproduct = ?";
                PreparedStatement statement4 = getConnection().prepareStatement(sql);
                statement4.setInt(1, id);
                statement4.execute();
                return;
            }
            if(countCurrent < product.getCount()){
                throw new RuntimeException("В базе нет столько товара, сколкьо планируется продать...");
            }
            sql = "UPDATE product SET product_count = ? WHERE idproduct = ?";
            PreparedStatement statement2 = getConnection().prepareStatement(sql);
            statement2.setInt(1, countCurrent - product.getCount());
            statement2.setInt(2, id);
            statement2.executeUpdate();
            System.out.println("s");
            return;
        }
    }
    public List<Product> showAll() throws SQLException {
        String sql = "SELECT * FROM product ";
        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Product> res = new LinkedList<>();
        while (resultSet.next()) {
            System.out.println("sds");
            res.add(new Product(resultSet.getString("product_name"), resultSet.getString("product_type"), resultSet.getInt("product_price"), resultSet.getInt("product_count")));
        }
        return res;
    }
    public void edit(Product product) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_name = ?";
        PreparedStatement statement1 = getConnection().prepareStatement(sql);
        statement1.setString(1, product.getName());
        ResultSet resultSet = statement1.executeQuery();


        if(resultSet.next()){
            int id = resultSet.getInt("idproduct");
            sql = "UPDATE product SET product_name = ? , product_type = ? , product_price = ?, product_count = ? WHERE idproduct = ?";
            PreparedStatement statement2 = getConnection().prepareStatement(sql);
            statement2.setString(1, product.getName());
            statement2.setString(2, product.getType());
            statement2.setInt(3, product.getPrice());
            statement2.setInt(4, product.getCount());
            statement2.setInt(5, id);
            statement2.executeUpdate();
            return;
        }
    }
}
