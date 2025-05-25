public class ComboDetail {
    private int comboDetailId;
    private int comboId;
    private int itemId;
    private int quantity;

    public ComboDetail(int comboDetailId, int comboId, int itemId, int quantity) {
        this.comboDetailId = comboDetailId;
        this.comboId = comboId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getComboDetailId() {
        return comboDetailId;
    }

    public void setComboDetailId(int comboDetailId) {
        this.comboDetailId = comboDetailId;
    }

    public int getComboId() {
        return comboId;
    }

    public void setComboId(int comboId) {
        this.comboId = comboId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
