package cgi.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MovieDetailsAPIRequestUtil {

    private final String API_KEY = "a5601610";
    /**
     * @param title The movie title
     *         Takes the movie title and performs a GET request to omdbapi
     *
     * @return Array where arr[0] = movie imdb rating, arr[1] = movie poster url from online
     * */
    public String[] getMovieRatingsAndImage(String title) throws IOException, JSONException {
        String titleForQuery = title.replace(" ","+");

        URL url = new URL("http://www.omdbapi.com/?t="+titleForQuery+"&apikey="+API_KEY);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        if(connection.getResponseCode() == 200){
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String line = br.readLine();
                while (line != null){
                    response.append(line);
                    line = br.readLine();
                }
            }
        }else{
            return null;
        }
        JSONObject json = new JSONObject(response.toString());

        return new String[] {(String) json.get("imdbRating"), (String) json.get("Poster")};
    }
}
