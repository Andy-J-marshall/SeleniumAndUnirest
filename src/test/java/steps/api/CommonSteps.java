package steps.api;

import com.mashape.unirest.http.HttpResponse;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static steps.api.BaseRequest.HTTP_RESPONSE_KEY;

public class CommonSteps {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("The status code should be <statusCode>")
    public void assertLastStatusCode(int statusCode) {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        assertThat(response.getStatus(), is(equalTo(statusCode)));
    }

    @Step("The response contains an error message <errorMessage>")
    public void assertErrorInResponse(String errorMessage) {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        assertThat(response.getBody().toString(), containsString(errorMessage));
    }
}
