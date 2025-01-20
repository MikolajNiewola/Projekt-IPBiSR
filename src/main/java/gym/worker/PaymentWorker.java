package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentWorker {
    @JobWorker(type = "payment", name = "paymentWorker")
    public void processPayment(final JobClient client, final ActivatedJob job) {
        System.out.println("Rozpoczęto inicjalizację płatności...");

        String paymentId = UUID.randomUUID().toString();

        client.newCompleteCommand(job.getKey())
                .variables("{\"paymentId\": \"" + paymentId + "\"}")
                .send()
                .join();

        System.out.println("paymentId: " + paymentId);
    }
}