import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_URL =
        "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    getWeather();
                    break;
                case 2:
                    getWindSpeed();
                    break;
                case 3:
                    getPressure();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getUserChoice() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
            return getUserChoice();
        }
    }

    private static void getWeather() {
        System.out.print("Enter the date (yyyy-MM-dd): ");
        String date = getUserInput();

        JSONObject jsonObject = getWeatherDataFromAPI();
        if (jsonObject != null) {
            try {
                JSONArray list = jsonObject.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");
                    if (dt_txt.contains(date)) {
                        JSONObject main = data.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        System.out.println("Temperature on " + dt_txt + " : " + temp + " °C");
                        return;
                    }
                }
                System.out.println("No data available for the given date.");
            } catch (JSONException e) {
                System.out.println("Error parsing JSON data.");
            }
        }
    }

    private static void getWindSpeed() {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String date = getUserInput();

        JSONObject jsonObject = getWeatherDataFromAPI();
        if (jsonObject != null) {
            try {
                JSONArray list = jsonObject.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");
                    if (dt_txt.contains(date)) {
                        JSONObject wind = data.getJSONObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        System.out.println("Wind Speed on " + dt_txt + " : " + windSpeed + " m/s");
                        return;
                    }
                }
                System.out.println("No data available for the given date.");
            } catch (JSONException e) {
                System.out.println("Error parsing JSON data.");
            }
        }
    }
    

    private static void getPressure() {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String date = getUserInput();

        JSONObject jsonObject = getWeatherDataFromAPI();
        if (jsonObject != null) {
            try {
                JSONArray list = jsonObject.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject data = list.getJSONObject(i);
                    String dt_txt = data.getString("dt_txt");
                    if (dt_txt.contains(date)) {
                        JSONObject main = data.getJSONObject("main");
                        double pressure = main.getDouble("pressure");
                        System.out.println("Pressure on " + dt_txt + " : " + pressure + " hPa");
                        return;
                    }
                }
                System.out.println("No data available for the given date.");
            } catch (JSONException e) {
                System.out.println("Error parsing JSON data.");
            }
        }
    }

    private static JSONObject getWeatherDataFromAPI() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new JSONObject(response.toString());
        } catch (IOException | JSONException e) {
            System.out.println("Error fetching data from API.");
            return null;
        }
    }

    private static String getUserInput() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading user input.");
            return "";
        }
    }
}

