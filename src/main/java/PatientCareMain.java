import java.sql.Time;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;


import PatientManagement.Catalogs.AgeGroup;
import PatientManagement.Catalogs.Limits;
import PatientManagement.Catalogs.VitalSignLimits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.EventSchedule;
import PatientManagement.Clinic.Location;
import PatientManagement.Clinic.LocationList;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Clinic.Site;
import PatientManagement.Clinic.SiteCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Persona.Person;
import PatientManagement.Persona.PersonDirectory;

public class PatientCareMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Adding a person
        Scanner scannerPerson = new Scanner(System.in);

        // Log in 
            System.out.println("Northeastern Hospitals Health ChatBot App");
            boolean isLoggedIn = login(scannerPerson);
            
            // Clinic
            Clinic clinic = new Clinic("Northeastern Hospitals");

            // Configuring vital signs catalog
            VitalSignsCatalog vsc = clinic.getVitalSignsCatalog();
            // Define age groups and their ranges
            AgeGroup children = vsc.newAgeGroup("Children", 20, 0);
            AgeGroup adults_21_50 = vsc.newAgeGroup("Adults 21-50", 50, 21);
            AgeGroup seniors = vsc.newAgeGroup("Seniors", 100, 51);

            // Define vital sign limits for each age group
            VitalSignLimits heartRateLimits = vsc.newVitalSignLimits("HR");
            VitalSignLimits bloodPressureLimits = vsc.newVitalSignLimits("BP");
            VitalSignLimits bloodSugarLimits = vsc.newVitalSignLimits("BS");

            // Add heart rate limits for age groups
            heartRateLimits.addLimits(children, 120, 70);
            heartRateLimits.addLimits(adults_21_50, 105, 55);
            heartRateLimits.addLimits(seniors, 110, 50);

            // Add blood pressure limits for age groups
            bloodPressureLimits.addLimits(children, 120, 80);
            bloodPressureLimits.addLimits(adults_21_50, 140, 70);
            bloodPressureLimits.addLimits(seniors, 150, 60);

            // Add blood sugar limits for age groups
            bloodSugarLimits.addLimits(children, 110, 70);
            bloodSugarLimits.addLimits(adults_21_50, 120, 70);
            bloodSugarLimits.addLimits(seniors, 130, 70);

            if (isLoggedIn){

                // Adding a person
                PersonDirectory pd = new PersonDirectory();
               
                System.out.print("Enter name: ");
                String name = scannerPerson.nextLine();
        
                System.out.print("Enter age: ");
                int age = scannerPerson.nextInt();

                // Get vital sign limits for the patient's age
                Limits hrLimits = clinic.getVitalSignsCatalog().findVitalSignLimits(age, "HR");
                Limits bpLimits = clinic.getVitalSignsCatalog().findVitalSignLimits(age, "BP");
                Limits bsLimits = clinic.getVitalSignsCatalog().findVitalSignLimits(age, "BS");
                                
                // Adjust random HR, BP, and BS values based on user's input and age limits
                Random random = new Random();
                int randomHR = random.nextInt(hrLimits.getUpperLimit() - hrLimits.getLowerLimit() + 1) + hrLimits.getLowerLimit();
                int randomBP = random.nextInt(bpLimits.getUpperLimit() - bpLimits.getLowerLimit() + 1) + bpLimits.getLowerLimit();
                int randomBS = random.nextInt(bsLimits.getUpperLimit() - bsLimits.getLowerLimit() + 1) + bsLimits.getLowerLimit();
                
                scannerPerson.nextLine();
                Person person = pd.newPerson(name, age);
     
                // Adding a date
                LocalDate today = LocalDate.now();
                System.out.println("Welcome "+  person.getName() + " to use this Health Check App!");
                System.out.println("Today is "+ today);
        
                // Creating a patient
                PatientDirectory patientDirectory = clinic.getPatientDirectory();
                Patient patient = patientDirectory.newPatient(person);
         
                // Create a location - Greater Boston Area, MA
         
                LocationList locationsInMA = clinic.getLocationList();
         
                Location greaterBostonArea = locationsInMA.newLocation("Greater Boston Area");
         
                SiteCatalog siteCatalog = clinic.getSiteCatalog();
         
                Site nuHealthServices = siteCatalog.newSite(greaterBostonArea);
        
                EventSchedule eventSchedule = new EventSchedule();
         
                Event HealthScreening = eventSchedule.newEvent(nuHealthServices, "0");
         
                Encounter patientVisitToDoctor = patient.newEncounter("Health Check", HealthScreening);
                
                
                // Repeat
                boolean repeat = true;

                while (repeat) {
                    System.out.println("Hi "+ person.getName()+ " , I'm your Health ChatBot. What can I help you with?");
                    System.out.println("Type 'check' for health check, 'advice' for health advice, or 'quit' to exit.");
                    String userInput = scannerPerson.nextLine().trim().toLowerCase();

                    switch (userInput) {
                        case "check":
                            // Ask the user about their recent health condition
                            boolean validInput;
                            String comfortResponse;
                    
                            do {
                                System.out.print("Hi "+ person.getName()+", Do you feel comfortable? (yes/no): ");
                                comfortResponse = scannerPerson.nextLine();
                                
                                validInput = comfortResponse.equalsIgnoreCase("yes") || comfortResponse.equalsIgnoreCase("no");
                                
                                if (!validInput) {
                                    System.out.println("Your health is important. Please answer truthfully.");
                                }
                            } while (!validInput);
                    
                            boolean isComfortable = comfortResponse.equalsIgnoreCase("yes") ? true : false;
                    

                            if (!isComfortable) {
                                String fastHeartbeatResponse;
                                String chestTightnessResponse;
                                String dizzyResponse;
                                String lowBloodPressureResponse;
                                String highCalorieResponse;
                                String lowBloodSugarResponse;
                    
                                // HR
                                do {
                                    System.out.print("Do you have a fast heartbeat? (yes/no): ");
                                    fastHeartbeatResponse = scannerPerson.nextLine();
                                    
                                    validInput = fastHeartbeatResponse.equalsIgnoreCase("yes") || fastHeartbeatResponse.equalsIgnoreCase("no");
                                    
                                    if (!validInput) {
                                        System.out.println("Your health is important. Please answer truthfully.");
                                    }
                                } while (!validInput);
                    
                                if (fastHeartbeatResponse.equalsIgnoreCase("yes")) {
                                    randomHR = hrLimits.getUpperLimit() + 50;
                                } 
                                else{
                                    do {
                                        System.out.print("Do you feel chest tightness? (yes/no): ");
                                        chestTightnessResponse = scannerPerson.nextLine();
                                        
                                        validInput = chestTightnessResponse.equalsIgnoreCase("yes") || chestTightnessResponse.equalsIgnoreCase("no");
                                        
                                        if (!validInput) {
                                            System.out.println("Your health is important. Please answer truthfully.");
                                        }
                                    } while (!validInput);
                    
                                    if (chestTightnessResponse.equalsIgnoreCase("yes")) {
                                        randomHR = hrLimits.getLowerLimit() - 20; 
                                    }
                                }
                    
                                // BP
                                do {
                                    System.out.print("Do you feel dizzy? (yes/no): ");
                                    dizzyResponse = scannerPerson.nextLine();
                                    
                                    validInput = dizzyResponse.equalsIgnoreCase("yes") || dizzyResponse.equalsIgnoreCase("no");
                                    
                                    if (!validInput) {
                                        System.out.println("Your health is important. Please answer truthfully.");
                                    }
                                } while (!validInput);
                    
                                if (dizzyResponse.equalsIgnoreCase("yes")) {
                                    randomBP = bpLimits.getUpperLimit() + 50;
                                }
                                else{
                                    do {
                                        System.out.print("Do you feel faint? (yes/no): ");
                                        lowBloodPressureResponse = scannerPerson.nextLine();
                                        
                                        validInput = lowBloodPressureResponse.equalsIgnoreCase("yes") || lowBloodPressureResponse.equalsIgnoreCase("no");
                                        
                                        if (!validInput) {
                                            System.out.println("Your health is important. Please answer truthfully.");
                                        }
                                    } while (!validInput);
                        
                                    if (lowBloodPressureResponse.equalsIgnoreCase("yes")) {
                                        randomBP = bpLimits.getLowerLimit() - 20;
                                    }
                                }
                    
                                // BS
                                do {
                                    System.out.print("Have you eaten high-calorie foods recently? (yes/no): ");
                                    highCalorieResponse = scannerPerson.nextLine();
                                    
                                    validInput = highCalorieResponse.equalsIgnoreCase("yes") || highCalorieResponse.equalsIgnoreCase("no");
                                    
                                    if (!validInput) {
                                        System.out.println("Your health is important. Please answer truthfully.");
                                    }
                                } while (!validInput);
                    
                                if (highCalorieResponse.equalsIgnoreCase("yes")) {
                                    randomBS = bsLimits.getUpperLimit() + 30;
                                }else{
                                    do {
                                        System.out.print("Do you feel weakness? (yes/no): ");
                                        lowBloodSugarResponse = scannerPerson.nextLine();
                                        
                                        validInput = lowBloodSugarResponse.equalsIgnoreCase("yes") || lowBloodSugarResponse.equalsIgnoreCase("no");
                                        
                                        if (!validInput) {
                                            System.out.println("Your health is important. Please answer truthfully.");
                                        }
                                    } while (!validInput);
                                        if (lowBloodSugarResponse.equalsIgnoreCase("yes")) {
                                        randomBS = bsLimits.getLowerLimit() - 20; 
                                    }
                                }
                                
                            }
                    
                            patientVisitToDoctor.addNewVitals("HR", randomHR);
                            patientVisitToDoctor.addNewVitals("BP", randomBP);
                            patientVisitToDoctor.addNewVitals("BS", randomBS);
                            
                            // Check the health status of the patient
                            boolean areVitalsNormal = patientVisitToDoctor.areVitalsNormal();
                            if (isComfortable != true) {
                                if (areVitalsNormal && !isComfortable) {
                                    System.out.println(person.getName() + " may have experienced psychological stress recently. It is essential to pay attention to your mental well-being and take time to rest and recover.");
                                } else if (!areVitalsNormal && !isComfortable) {
                                    System.out.println(person.getName() + " seems to not be feeling well. It is recommended to do the further evaluation.");
                                } 
                            } else {
                                System.out.println(person.getName() + " appears to be in good health. Continue maintaining a healthy lifestyle.");
                            }
                                    
                            // Ask the user if they want to perform a health check
                            System.out.print("Do you want to perform a health check? (yes/no): ");
                            String healthCheckResponse = scannerPerson.nextLine();
                            
                            if (healthCheckResponse.equalsIgnoreCase("yes")) {
                    
                                System.out.println("Health Check Report for " + person.getName() + ":");
                                System.out.println("Name: " + person.getName() + ", Age: " + person.getAge());
                                System.out.println("Heart Rate (HR) value: " + randomHR);
                                System.out.println("Blood Pressure (BP) value: " + randomBP);
                                System.out.println("Blood Sugar (BS) value: " + randomBS);
                            
                                // Generate health advice based on the patient's vital signs
                                StringBuilder healthAdvice = new StringBuilder();
                    
                                if (hrLimits != null) {
                                    if (randomHR > hrLimits.getUpperLimit()) {
                                        healthAdvice.append("High heart rate. \n");
                                        healthAdvice.append("Try deep breathing exercises, stay hydrated, reduce caffeine intake, and maintain a healthy sleep schedule. \n");
                                    } else if (randomHR < hrLimits.getLowerLimit()) {
                                        healthAdvice.append("Low heart rate. \n");
                                        healthAdvice.append("Increase physical activity, eat a balanced diet, and monitor your heart rate during exercise. \n");
                                    }
                                }
                                if (bpLimits != null) {
                                    if (randomBP > bpLimits.getUpperLimit()) {
                                        healthAdvice.append("High blood pressure. \n");
                                        healthAdvice.append("Reduce salt intake, increase potassium-rich foods, limit alcohol consumption, and maintain a healthy weight. \n");
                                    } else if (randomBP < bpLimits.getLowerLimit()) {
                                        healthAdvice.append("Low blood pressure. \n");
                                        healthAdvice.append("Increase salt intake moderately, stay hydrated, avoid standing up too quickly, and wear compression stockings if necessary. \n");
                                    }
                                }
                                if (bsLimits != null) {
                                    if (randomBS > bsLimits.getUpperLimit()) {
                                        healthAdvice.append("High blood sugar. \n");
                                        healthAdvice.append("Limit sugar intake, consume high-fiber foods, stay active, and maintain a healthy weight. \n");
                                    } else if (randomBS < bsLimits.getLowerLimit()) {
                                        healthAdvice.append("Low blood sugar. \n");
                                        healthAdvice.append("Consume small, frequent meals, include complex carbohydrates in your diet, and always carry a quick source of sugar with you. \n");
                                    }
                                }
                                
                                if (healthAdvice.length() == 0) {
                                    healthAdvice.append("All vital signs are within normal range. \n");
                                }
                                System.out.println("Health advice for " + person.getName() + ": " +"\n"+ healthAdvice);
                            } else {
                                System.out.println("No health check performed. Have a nice day!");
                            }
                            break;

                        case "advice":
                            // Provide health advice
                            System.out.println("\nGeneral Health Advice:");
                            System.out.println("1. Maintain a balanced diet with plenty of fruits, vegetables, whole grains, and lean proteins.");
                            System.out.println("2. Exercise regularly, aiming for at least 150 minutes of moderate-intensity aerobic activity per week.");
                            System.out.println("3. Get enough sleep, aiming for 7-9 hours per night for adults.");
                            System.out.println("4. Stay hydrated by drinking plenty of water throughout the day.");
                            System.out.println("5. Manage stress through activities such as meditation, yoga, or deep breathing exercises.");
                            System.out.println("6. Avoid smoking and limit alcohol consumption.");
                            System.out.println("7. Get regular check-ups and screenings as recommended by your healthcare provider.");
                            break;  

                        case "quit":
                            repeat = false;
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Sorry, I didn't understand that. Please try again.");
                            break;
                    }
                }
                
        } 
        
        }

        private static boolean login(Scanner scanner) {
            boolean loggedIn = false;
    
            while (!loggedIn) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
        
                if (username.equals("JackChan") && password.equals("123456")) {
                    loggedIn = true;
                } else {
                    System.out.println("Incorrect username or password. Please try again.");
                }
            }
            
            return loggedIn;
        }

    }    
    

    
