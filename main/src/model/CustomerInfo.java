import java.util.Date;

public class CustomerInfo {
    private int customerId;
    private String fullName;
    private String gender;
    private Date birthdate;
    private String address;
    private int loyaltyPoints;

    public CustomerInfo(int customerId, String fullName, String gender, Date birthdate, String address,
            int loyaltyPoints) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
