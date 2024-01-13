package PatientManagement.Patient.Orders;

import PatientManagement.Patient.ClinicalHistory.Vaccination;
import PatientManagement.Patient.ClinicalHistory.Vaccine;
import PatientManagement.Patient.Patient;

public class VaccinationOrder extends Order {
    Vaccine vaccine;
    Patient patient;
    String date;
    Vaccination vaccination;

    public VaccinationOrder(Vaccine v) {
        super();
        vaccine = v;

    }

    public Vaccination newVaccination() {

        if (vaccination == null)
            vaccination = new Vaccination();
        return vaccination;

    }

}
