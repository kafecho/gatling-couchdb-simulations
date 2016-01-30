package org.kafecho.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.UUID

/** A sample Gatling simulation that generates load on a CouchDB server.
  * The simulation creates synthetic users.
  * Each user lists all databases, creates a new database and adds documents the newly created database.
  * At the end of the test, each synthetic user deletes the database that it has created.
  * In order to better understand what is going on, have a read through http://gatling.io/docs/2.1.7/
  */
class CouchDBSimulation extends Simulation {
  val prefix = "CouchDBSimulation"
  val httpConf = http.baseURL(System.getProperty(s"$prefix.serverUrl", "http://127.0.0.1:5984"))
  val nbUsers = System.getProperty(s"$prefix.nbUsers", "10").toInt
  val rampUpTime = System.getProperty(s"$prefix.rampUpTime", "2").toInt
  val nbDocuments = System.getProperty(s"$prefix.nbDocuments", "50").toInt

  /** A Gatling feeder that generates random database names.*/
  val dbNameFeeder = Iterator.continually(Map("dbName" -> s"db-${UUID.randomUUID.toString}"))

  val scn = scenario("CouchDB")
    .exec(
      http("List all databases").get("/_all_dbs"))
    .feed(dbNameFeeder)
    .exec(
      http("Create a new database").put("/${dbName}"))
    .repeat(nbDocuments) {
      exec(
        http("Add a JSON document to the database")
        .post("/${dbName}")
        .body(RawFileBody("doc.json")).asJSON
      )
      .pause(2)
    }
    .exec(
      http("Delete the database.")
      .delete("/${dbName}")
    )

  setUp(
    scn.inject(rampUsers(nbUsers) over (rampUpTime seconds))
  )
  .protocols(httpConf)
}
