package uz.rustik.convertorbot.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${init.webhook}")
    private Boolean initWebhook;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.path}")
    private String path;

    @Override
    public void run(String... args) throws Exception {
        if (initWebhook) {
            StringBuilder stringBuilder = new StringBuilder();

            URL url = new URL(
                    "https://api.telegram.org/bot" + token +
                            "/setWebhook?url=" + path );

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    stringBuilder.append(line);
                }
            }
            System.out.println(stringBuilder);
        }

    }

}
