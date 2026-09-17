package com.AgenHotel.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int id;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate dataCheckin;
    private LocalDate dataCheckout;
    private LocalTime horaCheckin;
    private LocalTime horaCheckout;
    private String status;

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public Hospede getHospede(){return hospede;} public void setHospede(Hospede hospede){this.hospede=hospede;}
    public Quarto getQuarto(){return quarto;} public void setQuarto(Quarto quarto){this.quarto=quarto;}
    public LocalDate getDataCheckin(){return dataCheckin;} public void setDataCheckin(LocalDate dataCheckin){this.dataCheckin=dataCheckin;}
    public LocalDate getDataCheckout(){return dataCheckout;} public void setDataCheckout(LocalDate dataCheckout){this.dataCheckout=dataCheckout;}
    public LocalTime getHoraCheckin(){return horaCheckin;} public void setHoraCheckin(LocalTime horaCheckin){this.horaCheckin=horaCheckin;}
    public LocalTime getHoraCheckout(){return horaCheckout;} public void setHoraCheckout(LocalTime horaCheckout){this.horaCheckout=horaCheckout;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
}
