package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
public class paymentConfirmationWorker {
    @JobWorker(type = "paymentConfirmation", name = "paymentConfirmationWorker")
    public Map<String, Boolean> paymentConfirmation(final JobClient client, final ActivatedJob job) {
        System.out.println("Potwierdź płatność klikając [y]");

        boolean paymentValid = false;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while(true) {
            if (input.equals("y")) {
                paymentValid = true;
                break;

            }
        }

//        client.newCompleteCommand(job.getKey())
//                .variables("{\"paymentValid\": " + paymentValid + "}")
//                .send()
//                .join();

        return Map.of(
                "paymentValid", paymentValid
        );
    }
}


