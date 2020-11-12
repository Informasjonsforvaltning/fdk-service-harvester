package no.fdk.fdk_service_harvester.utils

import no.fdk.fdk_service_harvester.model.ServiceDBO
import no.fdk.fdk_service_harvester.model.MiscellaneousTurtle
import no.fdk.fdk_service_harvester.model.UNION_ID
import no.fdk.fdk_service_harvester.service.gzip
import org.bson.Document

private val responseReader = TestResponseReader()


val SERVICE_DBO_0 = ServiceDBO(
    uri = "http://public-service-publisher.fellesdatakatalog.digdir.no/services/1",
    fdkId = SERVICE_ID_0,
    issued = TEST_HARVEST_DATE.timeInMillis,
    modified = TEST_HARVEST_DATE.timeInMillis,
    turtleHarvested = gzip(responseReader.readFile("no_meta_service_0.ttl")),
    turtleService= gzip(responseReader.readFile("service_0.ttl"))
)


val SERVICE_DBO_1 = ServiceDBO(
    uri = "http://public-service-publisher.fellesdatakatalog.digdir.no/services/2",
    fdkId = SERVICE_ID_1,
    issued = TEST_HARVEST_DATE.timeInMillis,
    modified = TEST_HARVEST_DATE.timeInMillis,
    turtleHarvested = gzip(responseReader.readFile("no_meta_service_1.ttl")),
    turtleService= gzip(responseReader.readFile("service_1.ttl"))
)


val SERVICE_DBO_2 = ServiceDBO(
    uri = "http://public-service-publisher.fellesdatakatalog.digdir.no/services/3",
    fdkId = SERVICE_ID_2,
    issued = TEST_HARVEST_DATE.timeInMillis,
    modified = TEST_HARVEST_DATE.timeInMillis,
    turtleHarvested = gzip(responseReader.readFile("no_meta_service_2.ttl")),
    turtleService= gzip(responseReader.readFile("service_2.ttl"))
)

val HARVESTED_DBO = MiscellaneousTurtle(
    id = "http://localhost:5000/fdk-public-service-publisher.ttl",
    isHarvestedSource = true,
    turtle = gzip(responseReader.readFile("harvest_response_0.ttl"))
)

val UNION_DATA = MiscellaneousTurtle(
    id = UNION_ID,
    isHarvestedSource = false,
    turtle = gzip(responseReader.readFile("all_services.ttl"))
)

fun miscDBPopulation(): List<Document> =
    listOf(UNION_DATA, HARVESTED_DBO)
        .map { it.mapDBO() }

fun serviceDBPopulation(): List<Document> =
    listOf(SERVICE_DBO_0, SERVICE_DBO_1, SERVICE_DBO_2)
        .map { it.mapDBO() }

private fun ServiceDBO.mapDBO(): Document =
    Document()
        .append("_id", uri)
        .append("fdkId", fdkId)
        .append("issued", issued)
        .append("modified", modified)
        .append("turtleHarvested", turtleHarvested)
        .append("turtleService", turtleService)

private fun MiscellaneousTurtle.mapDBO(): Document =
    Document()
        .append("_id", id)
        .append("isHarvestedSource", isHarvestedSource)
        .append("turtle", turtle)
