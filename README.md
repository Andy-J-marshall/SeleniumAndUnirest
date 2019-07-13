# Test Automation framework

## Installation
The following need to be installed:
* Gauge installed: https://docs.gauge.org/latest/installation.html
* Maven installed
* Java 11

Ensure the web drivers are compatible version for your locally installed browsers: ./testautomation/src/test/resources/drivers

## Running the tests
Run all tests (default browser is Chrome):
* mvn clean install -Pall

Run all failing tests (awaiting potential bug fixes or clarification on requirements):
* mvn clean install -PfailingTests

Run all API tests:
* mvn clean install -Papi

Run web tests on a specific web browser:
* mvn clean install -Pchrome
* mvn clean install -Pfirefox
* mvn clean install -Pedge

Run tests on a remote machine using Browserstack:
You will need to update the default.properties file with your BrowserStack username and access key.
* mvn clean install -PremoteChromeWindows
* mvn clean install -PremoteChromeMac

## Contributing
* If you would like to contribute then create a new branch and submit a pull request.
* Please try and follow the coding style of the existing code. In particular, any API tests should use a GSON to serialise and deserialise a POJO. Similarly, web tests should follow the page object model.
* Try and limit the number of scenarios that test the web page; instead try and focus on the API tests for most of the validation. This will make the tests much quicker to run and more reliable. 
