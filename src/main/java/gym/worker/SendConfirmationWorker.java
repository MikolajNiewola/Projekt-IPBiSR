package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

@Component
public class SendConfirmationWorker {

    @JobWorker(type = "sendConfirmation", name = "sendConfirmationWorker")
    public void sendConfirmation(final JobClient client, final ActivatedJob job) {
        System.out.println("Wysyłanie potwierdzenia...");

        client.newCompleteCommand(job.getKey()).send().join();

        System.out.println("Potwierdzenie zostało wysłane.");
    }
}
