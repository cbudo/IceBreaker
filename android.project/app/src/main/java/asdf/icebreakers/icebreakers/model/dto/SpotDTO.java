package asdf.icebreakers.icebreakers.model.dto;

public class SpotDTO {

    int spotId; // Primary key
    String spotTime;
    int spotNumber;
    String spotName;
    String spotAddress;
    String spotCity;
    String spotState;
    String spotZip;
    float precision;
    double latitude;
    double longitude;

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getSpotTime() {
        return spotTime;
    }

    public void setSpotTime(String spotTime) {
        this.spotTime = spotTime;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public String getSpotCity() {
        return spotCity;
    }

    public void setSpotCity(String spotCity) {
        this.spotCity = spotCity;
    }

    public String getSpotState() {
        return spotState;
    }

    public void setSpotState(String spotState) {
        this.spotState = spotState;
    }

    public String getSpotZip() {
        return spotZip;
    }

    public void setSpotZip(String spotZip) {
        this.spotZip = spotZip;
    }

    public float getPrecision() {
        return precision;
    }

    public void setPrecision(float precision) {
        this.precision = precision;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
