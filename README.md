# OSINT
The project extracts queries from OpenStreetMap by implementing the following library: https://github.com/zsoltk/overpasser.git.

Another library you need is async-http-client from the https://github.com/AsyncHttpClient/async-http-client repository.

The OverpassFilterQuery class has been modified to the overpasser library by creating a new constructor method and defining as new methods close, node, rel, way, nwr, area (the new method takes a City as argument), adminLevel, wikipedia, searcharea, build.

This allows you to expand the query filters and the most important thing is the search within a city instead of generic geographic coordinates.
