# OSINT
The project extracts queries from OpenStreetMap by implementing the following library: https://github.com/zsoltk/overpasser.git.

Another library you need is async-http-client from the https://github.com/AsyncHttpClient/async-http-client repository.

The OverpassFilterQuery.java class has been modified to the original library by adding new method that allows you to extract together node, way and relation.

The OverpassFilterQuery.java class has been modified compared to the original library to allow a customized extraction and especially by city.
