// package com.softmaes.sparkbdd

// import org.apache.spark.sql.SparkSession
// import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}
// import com.softmaes.utilities.UtilsSpark
// import org.elasticsearch.spark.sql._
// import org.apache.commons.httpclient.HttpConnectionManager
// import org.apache.commons.httpclient._

// object SparkAndElasticSearch {

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession()
//     sparkSession.conf.set("es.index.auto.create", "true")
//     sparkSession.conf.set("es.nodes", "http://localhost")
//     sparkSession.conf.set("es.port", "9200")
//     sparkSession.conf.set("es.nodes.wan.only", "true")
//     sparkSession.conf.set("es.resource.write", "jea_db/orders")
//     // sparkSession.conf.set("spark.es.nodes.wan.only", "true")
//     sparkSession.conf.set("es.http.timeout", "1000s")

//     writeOnElasticSearch(sparkSession)
//   }

//   def writeOnElasticSearch(sparkSession: SparkSession) = {
//     /*sparkSession.sql(
//       "CREATE TEMPORARY TABLE myIndex    " +
//         "USING org.elasticsearch.spark.sql " +
//         "OPTIONS (resource 'jea_db/orders', " +
//         "scroll_size '20')" )*/

//     val schemaOrder = StructType(Array(
//       StructField("orderid", IntegerType, false),
//       StructField("customerid", IntegerType, false),
//       StructField("campaignid", IntegerType, true),
//       StructField("orderdate", TimestampType, true),
//       StructField("city", StringType, true),
//       StructField("state", StringType, true),
//       StructField("zipcode", StringType, true),
//       StructField("paymenttype", StringType, true),
//       StructField("totalprice", DoubleType, true),
//       StructField("numorderlines", IntegerType, true),
//       StructField("numunit", IntegerType, true)
//     ))

//     val dfOrdersData = sparkSession.read
//       .format("com.databricks.spark.csv")
//       .option("delimiter", "\t")
//       .option("header", "true")
//       .schema(schemaOrder)
//       .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

//     dfOrdersData.saveToEs("jea_db")
//   }

// }
