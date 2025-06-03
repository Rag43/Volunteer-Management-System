import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;

public class VolunteerManager {

    public static List<Volunteer> fetchVolunteers() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:3000/volunteers"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Volunteer[] volunteers = gson.fromJson(response.body(), Volunteer[].class);
            return Arrays.asList(volunteers);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

    }

    public static boolean addVolunteer(String name, String email, String phone) {
        try {
            Volunteer volunteer = new Volunteer(name, email, phone);
            Gson gson = new Gson();
            String json = gson.toJson(volunteer);
            HttpRequest addRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:3000/volunteers"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(addRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());

            return response.statusCode() == 200 || response.statusCode() == 201;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteVolunteer(String id) {
        try {
            String url = "http://localhost:3000/volunteers/" + id;
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .DELETE()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 || response.statusCode() == 204;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
