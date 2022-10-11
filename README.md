# SiTer
The project is called SiTer ("Sicurezza del Territorio per la Prevenzione del Crimine nelle Città Intelligenti" - "Territory Security for Crime Prevention in Smart Cities") and combines queries extracted from a purpose-built ontology with queries extracted from OpenStreetMap by implementing the following libraries:

https://github.com/zsoltk/overpasser.git;

https://github.com/AsyncHttpClient/async-http-client;

https://github.com/msteiger/jxmapviewer2.

For the OverpassFilterQuery class, from the overpasser library, it has been modified by creating a new constructor method and defining close, node, rel, way, nwr, area (which takes the name of a City as an argument), adminLevel, wikipedia, searcharea, build as new methods. This allows you to expand query filters and, more importantly, to search within a city rather than by generic geographic coordinates.

All libraries have been integrated with each other in the Java software with WindowBuilder graphical interface. 
