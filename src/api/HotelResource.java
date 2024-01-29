package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    public static final ReservationService reservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email){
        return customerService.getCustomer(email);

    }
    public static void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public static IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null){
            throw new IllegalArgumentException("Customer with email" + customerEmail+"does not exist");
        }
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public static Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null){
            throw new IllegalArgumentException("Customer with email" + customerEmail + "does not exist");

        }
        return reservationService.getCustomersReservations(customer);

    }
    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);
    }

}
