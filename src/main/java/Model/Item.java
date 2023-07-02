package Model;

public class Item{
    private String accID;
    private String charID;
    private String itemId;
    private int itemQuant;
    private boolean hand;
    private String itemName;
    private int bulletAta;
    
    /**
     * @return the accID
     */
    public String getAccID() {
        return accID;
    }

    /**
     * @param accID the accID to set
     */
    public void setAccID(String accID) {
        this.accID = accID;
    }

    /**
     * @return the charID
     */
    public String getCharID() {
        return charID;
    }

    /**
     * @param charID the charID to set
     */
    public void setCharID(String charID) {
        this.charID = charID;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the itemQuant
     */
    public int getItemQuant() {
        return itemQuant;
    }

    /**
     * @param itemQuant the itemQuant to set
     */
    public void setItemQuant(int itemQuant) {
        this.itemQuant = itemQuant;
    }

    /**
     * @return the hand
     */
    public boolean isHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(boolean hand) {
        this.hand = hand;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the bulletAta
     */
    public int getBulletAta() {
        return bulletAta;
    }

    /**
     * @param bulletAta the bulletAta to set
     */
    public void setBulletAta(int bulletAta) {
        this.bulletAta = bulletAta;
    }
}
