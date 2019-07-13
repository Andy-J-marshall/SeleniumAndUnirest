package steps.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseRequest {
    static final String HTTP_RESPONSE_KEY = "httpResponse";
    static final String BASE_URL = System.getenv("host");
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private String basicAuth = System.getenv("basicAuth");

    public void deleteRequest(String endpoint) throws UnirestException {
        HttpResponse<String> response = Unirest.delete(BASE_URL + endpoint)
                .header("Authorization", basicAuth)
                .asString();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "DELETE", null, "String");
    }

    public void deleteRequestNoAuth(String endpoint) throws UnirestException {
        HttpResponse<String> response = Unirest.delete(BASE_URL + endpoint)
                .asString();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "DELETE", null, "String");
    }

    public void getRequest(String endpoint) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(BASE_URL + endpoint)
                .headers(addAcceptAndContentTypeHeaders())
                .asJson();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "GET", null, "JSON");
    }

    public void getRequestReturningString(String endpoint) throws UnirestException {
        HttpResponse<String> response = Unirest.get(BASE_URL + endpoint)
                .headers(addAcceptAndContentTypeHeaders())
                .asString();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "GET", null, "String");
    }

    public void postRequest(String endpoint, String body) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post(BASE_URL + endpoint)
                .headers(addAcceptAndContentTypeHeaders())
                .body(body)
                .asJson();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "POST", body, "JSON");
    }

    public void postRequestReturningString(String endpoint, String body) throws UnirestException {
        HttpResponse<String> response = Unirest.post(BASE_URL + endpoint)
                .headers(addAcceptAndContentTypeHeaders())
                .body(body)
                .asString();
        logRequestAndResponseAndSaveToDatastore(response, endpoint, "POST", body, "String");
    }

    private Map<String, String> addAcceptAndContentTypeHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    private void logRequestAndResponseAndSaveToDatastore(HttpResponse response, String endpoint, String httpMethod, String body, String responseType) {
        String formattedJsonResponse = response.getBody().toString();
        if (!responseType.equalsIgnoreCase("String")) {
            JsonParser jp = new JsonParser();
            JsonElement unformattedJson = jp.parse(response.getBody().toString());
            formattedJsonResponse = gson.toJson(unformattedJson);
        }

        dataStore.put(HTTP_RESPONSE_KEY, response);
        Gauge.writeMessage("******REQUEST******");
        Gauge.writeMessage("***HTTP Method:*** " + httpMethod);
        Gauge.writeMessage("***Request url:*** " + BASE_URL + endpoint);
        Gauge.writeMessage("***Request body:*** " + body);
        Gauge.writeMessage("******RESPONSE******");
        Gauge.writeMessage("***Response body:*** " + formattedJsonResponse);
        Gauge.writeMessage("***Response headers:*** " + response.getHeaders().toString());
    }
}
