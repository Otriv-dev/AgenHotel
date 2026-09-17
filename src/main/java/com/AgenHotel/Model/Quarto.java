package com.AgenHotel.Model;

import java.math.BigDecimal;

public class Quarto {
    private int id;
    private int numero;
    private String tipo;
    private int capacidade;
    private BigDecimal precoDiaria;
    private String situacao;

    public Quarto() {}
    public Quarto(int id, int numero, String tipo, int capacidade, BigDecimal precoDiaria, String situacao) {
        this.id=id; this.numero=numero; this.tipo=tipo; this.capacidade=capacidade;
        this.precoDiaria=precoDiaria; this.situacao=situacao;
    }
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getNumero(){return numero;} public void setNumero(int numero){this.numero=numero;}
    public String getTipo(){return tipo;} public void setTipo(String tipo){this.tipo=tipo;}
    public int getCapacidade(){return capacidade;} public void setCapacidade(int capacidade){this.capacidade=capacidade;}
    public BigDecimal getPrecoDiaria(){return precoDiaria;} public void setPrecoDiaria(BigDecimal precoDiaria){this.precoDiaria=precoDiaria;}
    public String getSituacao(){return situacao;} public void setSituacao(String situacao){this.situacao=situacao;}
}
