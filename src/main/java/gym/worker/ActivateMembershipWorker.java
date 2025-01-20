package gym.worker;

import com.google.api.client.util.Value;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

@Component
public class ActivateMembershipWorker {

    @JobWorker(type = "activateMembership", name = "activateMembershipWorker")
    public void activateMembership(final JobClient client, final ActivatedJob job) throws IOException {
        System.out.println("Aktywacja karnetu...");

        Map<String, Object> variables = job.getVariablesAsMap();
        Map<String, String> userData = (Map<String, String>) variables.get("userData");


        String name = userData.get("name");
        String surname = userData.get("surname");
        String email = userData.get("email");
        String phone = userData.get("phone");
        String dob = userData.get("dob");
        String sex = userData.get("sex");
        String typeOfMembership = (String) variables.get("membershipType");



        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://195.150.230.208:5432/2023_niewola_mikolaj", "2023_niewola_mikolaj", "36397");

            String sql = "INSERT INTO IPBiSR.Members (name, surname, email, phone_nr, date_of_birth, sex, type_of_membership, date_of_purchase, date_of_expiration) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement( sql );

            String formattedPhone = phone.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1-$2-$3");

            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setString(4, formattedPhone);
            ps.setString(5, dob);
            ps.setString(6, sex);
            ps.setString(7, typeOfMembership);
            ps.setString(8, String.valueOf(LocalDate.now()));
            ps.setString(9, String.valueOf(LocalDate.now().plusMonths(1)));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        client.newCompleteCommand(job.getKey()).send().join();

        System.out.println("Karnet został aktywowany.");
    }
}
