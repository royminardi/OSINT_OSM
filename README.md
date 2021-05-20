# OSINT
The project extracts queries from OpenStreetMap by implementing the following library: https://github.com/zsoltk/overpasser.git.

Another library needed is async-http-client from the https://github.com/AsyncHttpClient/async-http-client repository.

The OverpassFilterQuery class, from the overpasser library, has been modified by creating a new constructor method and defining close, node, rel, way, nwr, area (which accepts the name of a City as an argument), adminLevel, wikipedia, searcharea, build as new methods.

This allows you to expand the query filters and, more importantly, search within a city instead of by generic geographic coordinates.
