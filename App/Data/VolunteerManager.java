import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;

public class VolunteerFetcher {

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

}
