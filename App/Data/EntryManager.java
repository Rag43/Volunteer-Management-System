import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;

public class EntryFetcher {

    public static List<Entry> fetchEntries() {
        try {
            HttpRequest entryRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://volunteer-management-system-ahfb.onrender.com/entries"))
                    .GET()
                    .build();

            HttpClient entryClient = HttpClient.newHttpClient();

            HttpResponse<String> response = entryClient.send(entryRequest, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Entry[] entries = gson.fromJson(response.body(), Entry[].class);
            return Arrays.asList(entries);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static List<Entry> fetchApprovedEntries() {
        List<Entry> allEntries = fetchEntries();
        List<Entry> approvedEntries = new ArrayList<>();
        for (Entry entry : allEntries) {
            if (entry.getApproved().equals("approved")) {
                approvedEntries.add(entry);
            }
        }
        return approvedEntries;
    }

    public static List<Entry> fetchDeniedEntries() {
        List<Entry> allEntries = fetchEntries();
        List<Entry> deniedEntries = new ArrayList<>();
        for (Entry entry : allEntries) {
            if (entry.getApproved().equals("denied")) {
                deniedEntries.add(entry);
            }
        }
        return deniedEntries;
    }

    public static List<Entry> fetchPendingEntries() {
        List<Entry> allEntries = fetchEntries();
        List<Entry> pendingEntries = new ArrayList<>();
        for (Entry entry : allEntries) {
            if (entry.getApproved().equals("pending")) {
                pendingEntries.add(entry);
            }
        }
        return pendingEntries;
    }

    public static void main(String[] args) {
        for (Entry entry : fetchPendingEntries()) {
            System.out.println(entry.getVolunteer());
        }
    }
}
