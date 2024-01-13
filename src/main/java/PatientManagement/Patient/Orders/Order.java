package PatientManagement.Patient.Orders;

import PatientManagement.Persona.Person;

public class Order {

    Person performer;
    Person originator;

    public Order() {

        performer = null;
        originator = null;
    }

    public Order(Person from, Person to) {

        performer = to;
        originator = from;
    }
}
