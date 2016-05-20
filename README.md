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

To find out more, just have a look at the file

`src/test/scala/org/kafecho/simulation/CouchDBSimulation`

Once the simulation has run, Gatling will produce a useful summary as well as detailed graphical reports about response time distribution, throughput, etc, etc...

The summary looks like the following:

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                       2600 (OK=2600   KO=0     )
> min response time                                      1 (OK=1      KO=-     )
> max response time                                   5814 (OK=5814   KO=-     )
> mean response time                                   456 (OK=456    KO=-     )
> std deviation                                       1436 (OK=1436   KO=-     )
> response time 95th percentile                       5564 (OK=5564   KO=-     )
> response time 99th percentile                       5799 (OK=5799   KO=-     )
> mean requests/sec                                  90.90 (OK=90.90  KO=-     )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                          2401 ( 92%)
> 800 ms < t < 1200 ms                                   0 (  0%)
> t > 1200 ms                                          199 (  8%)
> failed                                                 0 (  0%)
================================================================================
```

## Writing your own simulation

You write Gatling simulations in a Scala DSL with a fluent interface. Although not much knowledge of Scala is required, you sometimes need to be familiar with the syntax, especially when writing non-obvious tests.

## Handling authentication

If you have configured your CouchDB server with user accounts, you need to pass those credentials to Gatling, otherwise the requests will fail with HTTP 404 codes.
You can simply modify your Scala simulation files to include the credentials that you want to pass.

For example, append the credentials to the http protocol like so:

```
val httpConf = http.baseURL(System.getProperty(s"$prefix.serverUrl", "http://127.0.0.1:5984")).basicAuth("foo","bar")
```

Out of the box, Gatling also supports Digest authentication (see http://gatling.io/docs/2.1.7/http/http_protocol.html ).

## Resources

The Jenkins plugin for Gatling is quite handy: https://wiki.jenkins-ci.org/display/JENKINS/Gatling+Plugin

You just need to create a Maven Jenkins job which runs on of the mvn test commands above and configure the Gatling Jenkins plugin to track the output of this job. This will give you an idea of how / if the performance of your system changes over time.
