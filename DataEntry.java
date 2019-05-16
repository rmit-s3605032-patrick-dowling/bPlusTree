import java.io.Serializable;
import java.nio.ByteBuffer;

public class DataEntry {

    private byte[] deviceID; // 4 Bytes
    private byte[] arrivalTime; // 32 Bytes
    private byte[] departureTime; //32 Bytes
    private byte[] durationSeconds; // 8 Bytes
    private byte[] streetMarker; // 16 Bytes
    private byte[] sign; // 16 Bytes
    private byte[] area; // 16 Bytes
    private byte[] streetID; // 4 Bytes
    private byte[] streetName; // 16 Bytes
    private byte[] betweenStreet1; // 16 Bytes
    private byte[] betweenStreet2; // 16 Bytes
    private byte[] sideOfStreet; // 4 Bytes
    private byte inViolation; // 1 Byte
    // total of 309 bytes per record

    public byte[] getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(deviceID);
        this.deviceID = buffer.array();
    }

    /*public void setDeviceID(byte[] deviceID) {
        this.deviceID = deviceID;
    }*/

    public byte[] getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime.getBytes();
    }

    public void setArrivalTime(byte[] arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public byte[] getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime.getBytes();
    }

    public void setDepartureTime(byte[] departureTime) {
        this.departureTime = departureTime;
    }

    public byte[] getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(durationSeconds);
        this.durationSeconds = buffer.array();

    }

    public void setDurationSeconds(byte[] durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public byte[] getStreetMarker() {
        return streetMarker;
    }

    public void setStreetMarker(String streetMarker) {
        this.streetMarker = streetMarker.getBytes();
    }

    public void setStreetMarker(byte[] streetMarker) {
        this.streetMarker = streetMarker;
    }

    public byte[] getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign.getBytes();
    }

    public void setSign(byte[] sign) {
        this.sign = sign;
    }

    public byte[] getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area.getBytes();
    }

    public void setArea(byte[] area) {
        this.area = area;
    }

    public byte[] getStreetID() {
        return streetID;
    }

    public void setStreetID(int streetID) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(streetID);
        this.streetID = buffer.array();
    }

    /*public void setStreetID(byte[] streetID) {
        this.streetID = streetID;
    }*/

    public byte[] getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName.getBytes();
    }

    public void setStreetName(byte[] streetName) {
        this.streetName = streetName;
    }

    public byte[] getBetweenStreet1() {
        return betweenStreet1;
    }

    public void setBetweenStreet1(String betweenStreet1) {
        this.betweenStreet1 = betweenStreet1.getBytes();
    }
    
    public void setBetweenStreet1(byte[] betweenStreet1) {
        this.betweenStreet1 = betweenStreet1;
    }

    public byte[] getBetweenStreet2() {
        return betweenStreet2;
    }

    public void setBetweenStreet2(String betweenStreet2) {
        this.betweenStreet2 = betweenStreet2.getBytes();
    }

    public void setBetweenStreet2(byte[] betweenStreet2) {
        this.betweenStreet2 = betweenStreet2;
    }

    public byte[] getSideOfStreet() {
        return sideOfStreet;
    }

    public void setSideOfStreet(int sideOfStreet) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(sideOfStreet);
        this.sideOfStreet = buffer.array();
    }

    /*public void setSideOfStreet(byte[] sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
    }*/

    public byte getInViolation() {
        return inViolation;
    }

    public void setInViolation(char inViolation) {
        this.inViolation = (byte) inViolation;
    }

    public void setInViolation(byte inViolation) {
        this.inViolation = inViolation;
    }

}