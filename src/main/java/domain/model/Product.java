package domain.model;

public class Product {

    private int productId;
    private double value;

    public Product() {
    }
    public Product(int productId, double value) {
        this.productId = productId;
        this.value = value;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
