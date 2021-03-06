package sample.model;

public class ShoppingList {
    private String name;
    private String quantity;
    private int userId;
    private int shoppingId;

    public ShoppingList() {
    }

    public ShoppingList(String name, String quantity, int userID) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(int shoppingId) {
        this.shoppingId = shoppingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + ", " + quantity;
    }
}
