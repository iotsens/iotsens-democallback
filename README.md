# IoTsens demo callback

This projects shows how to build a simple callback for the IoTsens platform using Spring Boot. This callback simulates that is in charge of deciding if a garbage bin must be gathered depending on the filling level and the environment temperature.

The callback performs the following functionality:
 - Receives a Measure from the IoTsens platform using a REST endpoint.
 - Reads the last temperature Measure from a temperature sensor using the IoTsens API.
 - Computes if the trash bin must be gathered using a dummy algorithm.
 - Sends to the bin sensor in the IoTsens platform the current gathering state using the IoTsens INBOX API.

For running and executing this project you have to configure your credentials and your sensors identifiers in the application.properties file.

Please, note that for using the IoTsens API in Java projects you can use the library provided by IoTsens instead of manually calling the API REST endpoints.

