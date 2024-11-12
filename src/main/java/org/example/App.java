package org.example;

import org.example.domain.dao.Dao;
import org.example.domain.dao.impl.PassengerFileDao;
import org.example.domain.entity.PassengerEntity;

import java.util.Optional;

public class App {
    public static void main(String[] args) {
        PassengerEntity passengerEntity
                = new PassengerEntity("Cavad","Nazirli","javadnazirli123","21321");
        PassengerFileDao passengerFileDao = new PassengerFileDao();
        passengerFileDao.save(passengerEntity);
        Optional<PassengerEntity> passengerOptional = passengerFileDao.getById(1L);
        if (passengerOptional.isPresent()) {
            PassengerEntity passenger = passengerOptional.get();
            System.out.println("Passenger found: " + passenger.getFirstName() + " " + passenger.getLastName());
        } else {
            System.out.println("Passenger with ID 1 not found.");
        }
    }
}
