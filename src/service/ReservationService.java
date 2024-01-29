package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService_instance = null;
    public static Set<IRoom> rooms = new HashSet<>();
    private static final Set<Reservation> reservations = new HashSet<>();


    private ReservationService() {
    }
// https://www.geeksforgeeks.org/singleton-class-java/
    public static ReservationService getInstance(){
        if (reservationService_instance == null)
            reservationService_instance = new ReservationService();

        return reservationService_instance;
    }

    public void addRoom(IRoom room){
        rooms.add(room);


    }
    public IRoom getARoom(String roomId){
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        List<IRoom> availableRooms = new ArrayList<>();
        if(isBooked(room, checkInDate,checkOutDate) || isCustomerBooked(customer, checkInDate, checkOutDate)){
            System.out.println("Room is already booked");
            Date alternateCheckInDate = reccomendADate(checkInDate, 7);
            Date alternateCheckOutDate = reccomendADate(checkOutDate, 7);
            for(IRoom roome:rooms){
                if(isRoomAvailable(roome, alternateCheckInDate, alternateCheckOutDate)){
                    availableRooms.add(roome);
                }
            }
            System.out.println(availableRooms);
            System.out.println(alternateCheckInDate);
            System.out.println(alternateCheckOutDate);
            System.out.println("Would you like to book these alternate dates?");
            if(new Scanner(System.in).next().equalsIgnoreCase("yes")) {
                Reservation reservation = new Reservation(customer, room, alternateCheckInDate, alternateCheckOutDate);
                reservations.add(reservation);
                return reservation;
            } else{
                return null;
            }
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }
    private static boolean isBooked(IRoom room, Date checkInDate, Date checkOutDate){
        for(Reservation reservation:reservations){
            if(reservation.getRoom().equals(room) && !reservation.getCheckOutDate().before(checkInDate) && !reservation
                    .getCheckInDate().after(checkOutDate))
                return true;
        }
        return false;
    }
    private static boolean isCustomerBooked(Customer customer, Date checkInDate, Date checkOutDate){
        for(Reservation reservation:reservations){
            if(reservation.getCustomer().equals(customer) && !reservation.getCheckOutDate().before(checkInDate) && !reservation
                    .getCheckInDate().after(checkOutDate)){
                return true;
            }
        }
        return false;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        if(availableRooms.isEmpty()){
            Date alternateCheckInDate = reccomendADate(checkInDate, 7);
            Date alternateCheckOutDate = reccomendADate(checkOutDate, 7);
            for(IRoom room:rooms){
                if(isRoomAvailable(room, alternateCheckInDate, alternateCheckOutDate)){
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms;

    }
    private static boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate){
        for(Reservation reservation : reservations){
            if(reservation.getRoom().equals(room) && !(checkOutDate.before(reservation.getCheckInDate()) ||
                    checkInDate.after(reservation.getCheckOutDate()))){
                return false;
            }
        }
        return true;
    }

    private static Date reccomendADate(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public Collection<Reservation> getCustomersReservations(Customer customer){
        List<Reservation> customerReservation = new ArrayList<>();
        for(Reservation reservation : reservations){
            if(reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
        return customerReservation;

    }
    public Collection<IRoom> getAllRooms(){
        return rooms;
    }


    public void printAllReservations(){
        for(Reservation reservation : reservations)
            System.out.println(reservation.toString());
    }


}
