package az.edu.turing;

import az.edu.turing.controller.PassengerController;
import az.edu.turing.domain.dao.PassengerDao;
import az.edu.turing.domain.dao.impl.PassengerInMemoryDao;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.request.CreatePassengerRequest;
import az.edu.turing.service.PassengerService;
import az.edu.turing.service.PassengerServiceImpl;

public class App {
    public static void main(String[] args) {
//        PassengerEntity passengerEntity
//                = new PassengerEntity("Cavad","Nazirli","javadnazirli123","21321");
//        PassengerFileDao passengerFileDao = new PassengerFileDao();
//        passengerFileDao.save(passengerEntity);
//        Optional<PassengerEntity> passengerOptional = passengerFileDao.getById(1L);
//        if (passengerOptional.isPresent()) {
//            PassengerEntity passenger = passengerOptional.get();
//            System.out.println("Passenger found: " + passenger.getFirstName() + " " + passenger.getLastName());
//        } else {
//            System.out.println("Passenger with ID 1 not found.");
//        }

        final PassengerDao passengerDao =
                new PassengerInMemoryDao();
//                    new PassengerFileDao();
        PassengerMapper passengerMapper = new PassengerMapper();
        final PassengerService passengerService = new PassengerServiceImpl(passengerDao, passengerMapper);
        final PassengerController passengerController = new PassengerController(passengerService);

        run(passengerController);
    }

    private static void run(PassengerController passengerController) {
        System.out.println(passengerController.create(new CreatePassengerRequest(1, "Gurbanli", "joshgun123")));
        System.out.println(passengerController.findById(1));
    }
}
