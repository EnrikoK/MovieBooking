package cgi.demo.DTO;

import cgi.demo.entities.Screening;

import java.util.Date;

public interface UserTicketsProjection {

    Date getPurchaseDate();

    int getRow();

    int getSeat();

    Screening getScreening();


}
