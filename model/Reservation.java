import java.util.Date;

public class Reservation {
    private int reservationId;
    private int customerId;
    private int tableId;
    private Date reservationTime;
    private int numberOfGuests;
    private String status;

    public Reservation(int reservationId, int customerId, int tableId, Date reservationTime, int numberOfGuests,
            String status) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.tableId = tableId;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
