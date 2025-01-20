package gym.worker;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterPurchaseWorker {
    @JobWorker(type = "registerPurchase", name = "registerPurchaseWorker")
    public void registerPurchase(final JobClient client, final ActivatedJob job) {


//        client.newCompleteCommand(job.getKey())
//                .variables(job.getVariables())
//                .send()
//                .join();

        return;
    }
}


