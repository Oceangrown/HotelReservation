package model;

public class Room implements IRoom {
    public String roomNumber;
    public double price;
    public RoomType roomType;

    public Room(String roomNumber, double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {

        return roomNumber;
    }

    @Override
    public double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {

        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {

        return "\t" + roomNumber + "\t\t" + (price > 0 ? "$" + price : "Free") + "\t\t" + roomType;
    }
}
