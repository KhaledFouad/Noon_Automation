package pages;

public class CartItem {
    private final String name;
    private final String priceText;
    private final int quantity;

    public CartItem(String name, String priceText, int quantity) {
        this.name = name == null ? "" : name.trim();
        this.priceText = priceText == null ? "" : priceText.trim();
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public String getPriceText() {
        return priceText;
    }
    public int getQuantity() {
        return quantity;
    }

    public boolean nameMatches(String expectedName) {
        if (expectedName == null) return false;
        String namee = name.toLowerCase();
        String expectedNamee = expectedName.toLowerCase();
        if (namee.isBlank() || expectedNamee.isBlank()) return false;
        String expectedNamePrefix = expectedNamee.substring(0, Math.min(expectedNamee.length(), 30));
        return namee.contains(expectedNamePrefix) || expectedNamee.contains(namee.substring(0, Math.min(namee.length(), 30)));
}
}
