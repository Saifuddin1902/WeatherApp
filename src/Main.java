import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    private static final String API_KEY = "2d55f3c3f2e8a9ced7629c9eee2ac36a"; 
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the city name: ");
        String city = scanner.nextLine();
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();
            if (jsonObject.has("name")) {
                String cityName = jsonObject.get("name").getAsString();
                JsonObject main = jsonObject.getAsJsonObject("main");
                double temp = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                System.out.println("Weather in " + cityName + ":");
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
            } else 
            {
                System.out.println("City not found. Please enter a valid city name.");
            }

        } 
        catch (IOException | InterruptedException e) 
        {
            System.out.println("Error: Unable to fetch weather data.");
            e.printStackTrace();
        } 
        finally 
        {
            scanner.close();
        }
    }
}
