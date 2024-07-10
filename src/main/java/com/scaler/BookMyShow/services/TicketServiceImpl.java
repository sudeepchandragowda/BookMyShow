package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.exception.ShowSeatNotAvailableException;
import com.scaler.BookMyShow.exception.TicketNotFoundException;
import com.scaler.BookMyShow.exception.UserNotFoundException;
import com.scaler.BookMyShow.models.*;
import com.scaler.BookMyShow.repository.ShowRepository;
import com.scaler.BookMyShow.repository.ShowSeatRepository;
import com.scaler.BookMyShow.repository.TicketRepository;
import com.scaler.BookMyShow.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowRepository showRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(Long userId, List<Long> showSeatIds, Long showId) {
        Users bookedByUser = userRepository.findById(userId).get();
        Show show = showRepository.findById(showId).get();

        for (Long showSeatId : showSeatIds) {
           ShowSeat showSeat = showSeatRepository.findById(showSeatId).get();
           if(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
               showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
           } else {
               throw new ShowSeatNotAvailableException("Show seat is not available");
           }
           showSeatRepository.save(showSeat);
        }

        boolean paymentDone = paymentCheck();
        List<ShowSeat> showSeats = new ArrayList<>();
        double amount = 0;
        if (paymentDone) {
            for (Long showSeatId : showSeatIds) {
                ShowSeat showSeat = showSeatRepository.findById(showSeatId).get();
                showSeat.setShowSeatStatus(ShowSeatStatus.BOOKED);
                showSeatRepository.save(showSeat);
                showSeats.add(showSeat);
                amount = amount + showSeat.getPrice();
            }
        }
        return ticketGenerator(bookedByUser, show, showSeats, amount);
    }
    @Override
    public Ticket cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket for given ID is not found");
        }
        Ticket ticket = ticketOptional.get();
        ticket.setBookingStatus(BookingStatus.CANCELLED);
        for(ShowSeat showSeat : ticket.getShowSeats()) {
            showSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        ticketRepository.save(ticket);
        for(Payment p : ticket.getPayments()) {
            p.getRefNo();
            //send a message to third party with payment ref number for refund
        }
        return ticket;
    }

    @Override
    public Ticket transferTicket(Long ticketId, Long fromUserId, Long toUserId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket for given ID is not found");
        }
        Optional<Users> fromUserOptional = userRepository.findById(fromUserId);
        Optional<Users> toUserOptional = userRepository.findById(toUserId);

        if(fromUserOptional.isEmpty() || toUserOptional.isEmpty()) {
            throw new UserNotFoundException("User details given for the ticket transfer is not found");
        }

        Ticket ticket = ticketOptional.get();
        Users fromUser = fromUserOptional.get();
        List<Ticket> bookedTicketHistory = fromUser.getTickets();
        bookedTicketHistory.remove(ticket);
        userRepository.save(fromUser);

        Users toUser = toUserOptional.get();
        bookedTicketHistory = toUser.getTickets();
        bookedTicketHistory.add(ticket);
        toUser = userRepository.save(toUser);

        ticket.setUser(toUser);
        return ticketRepository.save(ticket);
    }
    private boolean paymentCheck() {
        return true;
    }

    private Ticket ticketGenerator (Users user, Show show, List<ShowSeat> showSeats, double amount) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setAmount(amount);
        ticket.setBookedAt(LocalDateTime.now());
        ticket.setBookingStatus(BookingStatus.CONFIRMED);
        return ticketRepository.save(ticket);
    }
}
