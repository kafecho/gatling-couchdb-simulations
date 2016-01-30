# gatling-couchdb-simulations

A sample Maven project to illustrate how to load-test a CouchDB server using Gatling (http://Gatling.io)

## Requirements

To run the sample, you need to have:

  - A JDK
  - Apache Maven (see https://maven.apache.org/)

## How to use
Just run the following command:
```
mvn test -Dgatling.simulationClass=org.kafecho.simulation.CouchDBSimulation
```

This will run the simulation against a local server.

If you want to test against a CouchDB server running on a different host, you can specify the following:
```
mvn test -DCouchDBSimulation.serverUrl=http://192.168.56.102:5984 -Dgatling.simulationClass=org.kafecho.simulation.CouchDBSimulation
```

The following options can also be specified using the prefix -DCouchDBSimulation:
  - nbUsers: number of concurrent users
  - rampUpTime: how quickly to ramp up users
  - nbDocuments: number of JSON document that each user uploads to its database

## Resources

The Jenkins plugin for Gatling is quite handy: https://wiki.jenkins-ci.org/display/JENKINS/Gatling+Plugin
