package br.com.ramondev.hotelservice.utils;

import java.util.Comparator;

import br.com.ramondev.hotelservice.model.domain.Apartamento;

public class CustomComparator implements Comparator<Apartamento> {
  
  @Override
  public int compare(Apartamento ap1, Apartamento ap2) {
    Integer numeroAp1 = (Integer) ap1.getNumeroApartamento();
    Integer numeroAp2 = (Integer) ap2.getNumeroApartamento();

    return numeroAp1.compareTo(numeroAp2);
  } 
}
