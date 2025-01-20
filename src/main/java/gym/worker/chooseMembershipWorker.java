package gym.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class chooseMembershipWorker {

    String[] memberships = {"Student", "Standard", "Premium"};
    String[] membershipPrices = {"50", "100", "150"};

    @JobWorker(type = "chooseMembership", name = "chooseMembershipWorker")
    public Map<String, String> chooseMembershipWorker(final JobClient client, final ActivatedJob job) {

        System.out.println("Karnety: ");
        for (int i=0;i<memberships.length;i++) {
            System.out.printf("[%d] %s: %s %n", i+1, memberships[i], membershipPrices[i]);
        }
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        int choice;
        String chosenMembership;
        String priceOfMembership;
        while (true) {
            try {
                System.out.println("Wybierz karnet: ");
                choice = Integer.parseInt(scanner.nextLine());
                chosenMembership = memberships[choice-1];
                priceOfMembership = membershipPrices[choice-1];
                break;
            } catch (Exception e) {
                System.err.println("Błąd wyboru: " + e.getMessage());
            }
        }

        System.out.println("Wybrano karnet: " + chosenMembership + ", cena: " + priceOfMembership);
        System.out.println();

        return Map.of(
                "membershipType", chosenMembership,
                "membershipPrice", priceOfMembership
        );

    }
}
