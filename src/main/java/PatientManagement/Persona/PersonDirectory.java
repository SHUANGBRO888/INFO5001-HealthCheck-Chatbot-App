package PatientManagement.Persona;

import java.util.ArrayList;

public class PersonDirectory {
    ArrayList<Person> personlist;

    public PersonDirectory() {
        personlist = new ArrayList<Person>();
    }

    public Person newPerson(String id, int age) {
        Person p = new Person(id, age);
        personlist.add(p);
        return p;
    }

    public Person findPerson(String id) {

        for (Person p : personlist) {

            if (p.isMatch(id)) {
                return p;
            }
        }
        return null; // not found after going through the whole list
    }
}
