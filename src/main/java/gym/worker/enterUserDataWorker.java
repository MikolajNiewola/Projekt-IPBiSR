package gym.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;


@Component
public class enterUserDataWorker {

    String[] genders = {"Kobieta","Mężczyzna"};
    @JobWorker(type = "enterUserData", name = "enterUserDataWorker")
    public void enterUserData(final JobClient client, final ActivatedJob job) throws JsonProcessingException {


        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dane osobowe: ");
        System.out.println();
        System.out.println("Imie: ");
        String imie = scanner.nextLine();
        System.out.println("Nazwisko: ");
        String nazwisko = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Numer telefonu: ");
        String nrtel = scanner.nextLine();
        System.out.println("Data Urodzenia (dd.mm.rrrr): ");
        String date = scanner.nextLine();
        System.out.println("Płeć: ");
        System.out.println("[1] Kobieta");
        System.out.println("[2] Mężczyzna");
        int choice = scanner.nextInt();
        String gender = genders[choice-1];


        Map<String, String> userData = Map.of(
                "name", imie,
                "surname",nazwisko,
                "email",email,
                "phone",nrtel,
                "dob", date,
                "sex", gender
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String userDataJson = objectMapper.writeValueAsString(userData);

        client.newCompleteCommand(job.getKey())
                .variables("{\"userData\": " + userDataJson + "}")
                .send()
                .join();
    }
}