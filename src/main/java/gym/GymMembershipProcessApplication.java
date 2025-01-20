package gym;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
@EnableZeebeClient
@Deployment(resources = "classpath*:/model/*.*")
public class GymMembershipProcessApplication implements CommandLineRunner {

    private final ZeebeClient zeebeClient;

    public GymMembershipProcessApplication(@Qualifier("zeebeClientLifecycle") ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(GymMembershipProcessApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Aby rozpocząć zakup karnetu naciśnij [y]");
        String input = scanner.nextLine();

        if (input.equals("y")) {
            try {
                ProcessInstanceEvent processInstance = zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("ModelZakupuKarnetu")
                        .latestVersion()
                        .send()
                        .join();

                System.out.println();
                System.out.println("Uruchomiono proces o kluczu: "+ processInstance.getProcessInstanceKey());
            } catch (Exception e) {
                System.err.println("Błąd poczas uruchamiania procesu: " + e.getMessage());
            }
        } else {
            System.out.println("Błąd");
        }
    }
}