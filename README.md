# Selenium-Google

## Installation
The following need to be installed:
* Gauge installed: https://docs.gauge.org/latest/installation.html
* Maven installed
* Java 11

## Running the tests
Run all tests (default browser is Chrome):
* mvn clean install -Pall

To run all failing tests (awaiting potential bug fixes or clarification on requirements):
* mvn clean install -PfailingTests

To run all API tests:
* mvn clean install -Papi

Run web tests on a specific web browser:
* mvn clean install -Pchrome
* mvn clean install -Pfirefox
* mvn clean install -Pedge

To run tests on a remote machine using browser stack:
You will need to update the default.properties file with your BrowserStack username and access key.
* mvn clean install -PremoteChromeWindows
* mvn clean install -PremoteChromeMac

## Contributing
* 
