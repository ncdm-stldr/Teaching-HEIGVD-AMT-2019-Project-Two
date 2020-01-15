package ch.heigvd.amt.project_two.auth.api.spec.helpers;

import ch.heigvd.amt.project_two.auth.api.DefaultApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private DefaultApi api = new DefaultApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.amt.project_two.auth.server.url");
        api.getApiClient().setBasePath(url);

    }

    public DefaultApi getApi() {
        return api;
    }


}
