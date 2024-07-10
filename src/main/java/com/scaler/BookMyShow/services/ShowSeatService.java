package com.scaler.BookMyShow.services;

import com.scaler.BookMyShow.models.ShowSeat;

import java.util.List;

public interface ShowSeatService {
    ShowSeat getShowSeat(Long showSeatId);
    List<ShowSeat> getShowSeats (List<Long> showSeats);
    ShowSeat updateShowSeat (ShowSeat showSeat);
    List<ShowSeat> updateShowSeats(List<ShowSeat> showSeats);
}
