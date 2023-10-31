public class Product {
    private String name;
    private String type;
    private int price;
    private int count;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product(String name, String type, int price, int count) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.count = count;
    }
}
