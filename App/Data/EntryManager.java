import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;

public class EntryManager {

    public static List<Entry> fetchEntries() {
        try {
            HttpRequest entryRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:3000/entries"))
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

    public static boolean denyEntry(Entry entry) {
        String uri = "http://localhost:3000/entries/" + entry.get_id();
        try {
            HttpRequest patchRequest = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"approved\":\"denied\"}"))
                    .header("content-type", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200 || response.statusCode() == 204 || response.statusCode() == 202;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean approveEntry(Entry entry) {
        String uri = "http://localhost:3000/entries/" + entry.get_id();

        try {
            HttpRequest patchRequest = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString("{\"approved\":\"approved\"}"))
                    .header("content-type", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());

            // Update hours based on approved entry
            boolean updatedHours = updateHours(entry.getVolunteer(), entry.getHours());
            if (!updatedHours) {
                return false;
            }

            return response.statusCode() == 200 || response.statusCode() == 204 || response.statusCode() == 202;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHours(Volunteer volunteer, double entryHours) {
        String uri = "http://localhost:3000/volunteers/" + volunteer.getId();
        double hours = volunteer.getHours() + entryHours;

        // Get JSON body using gson
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hours", hours);
        String json = jsonObject.toString();

        try {
            HttpRequest patchRequest = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .header("content-type", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200 || response.statusCode() == 204 || response.statusCode() == 202;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        List<Entry> entries = fetchEntries();
        for (Entry entry : entries) {
            if (entry.getApproved().equals("denied")) System.out.println(entry.getVolunteer());
        }
    }
}
