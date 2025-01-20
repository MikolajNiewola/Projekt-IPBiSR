package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Component
public class SendConfirmationWorker {

    @JobWorker(type = "sendConfirmation", name = "sendConfirmationWorker")
    public void sendConfirmation(final JobClient client, final ActivatedJob job) {
        System.out.println("Wysyłanie potwierdzenia...");

//        client.newCompleteCommand(job.getKey())
//            .send()
//            .join();

        Map<String, Object> variables = job.getVariablesAsMap();
        String typeOfMembership = (String) variables.get("membershipType");


        System.out.println("Karnet typu " +  typeOfMembership + " został aktywowany.");
        System.out.println("Dzień zakupu: " + LocalDate.now() + ", karnet wygasa dnia: " + LocalDate.now().plusMonths(1) + ".");

        System.out.println("Potwierdzenie zostało wysłane.");

        return;
    }
}
