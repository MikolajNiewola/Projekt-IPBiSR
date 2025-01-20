package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CheckUserDataWorker {

    @JobWorker(type = "checkUserData", name = "checkUserDataWorker")
    public void checkUserData(final JobClient client, final ActivatedJob job) {
        System.out.println("Rozpoczęto sprawdzanie poprawności danych...");

        Map<String, Object> variables = job.getVariablesAsMap();
        Map<String, String> userData = (Map<String, String>) variables.get("userData");

        boolean isValid = validateUserData(userData);


        client.newCompleteCommand(job.getKey())
                .variables("{\"userDataValid\": " + isValid + "}")
                .send()
                .join();
    }

    private boolean validateUserData(Map<String, String> userData) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String phoneRegex = "\\d{9}";
        String dateRegex = "^([0-2][0-9]|3[0-1])\\.(0[1-9]|1[0-2])\\.(\\d{4})$";

        String name = userData.get("name");
        String surname = userData.get("surname");
        String email = userData.get("email");
        String phone = userData.get("phone");
        String dob = userData.get("dob");
        String sex = userData.get("sex");

        // Sprawdzenie czy wszstko jest wpisane
        if (name.equals("") || surname.equals("") || email.equals("") || phone.equals("") || dob.equals("") || sex.equals("")) {
            System.out.println("Proszę uzupełnić wszystkie pola.");
            return false;
        }

        // Sprawdzenie maila
        if (!email.matches(emailRegex)) {
            System.out.println("Niepoprawny adres Email.");
            return false;
        }

        // Sprawdzenie telefonu
        if (!phone.matches(phoneRegex)) {
            System.out.println("Niepoprawny nr. telefonu.");
            return false;
        }

        if (!dob.matches(dateRegex)) {
            System.out.println("Niepoprawna data.");
            return false;
        }

        return true;
    }
}