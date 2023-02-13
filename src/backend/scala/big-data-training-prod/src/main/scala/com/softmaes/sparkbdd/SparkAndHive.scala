// package com.softmaes.sparkbdd

// import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}
// import org.apache.spark.sql.{SaveMode, SparkSession}

// import com.softmaes.utils.UtilsSpark

// object SparkAndHive {

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession(true)
//     // sparkSession.conf.set("hive.metastore.warehouse.dir", "hdfs://namenode:8020/user/hive/warehouse")
//     // sparkSession.conf.set("spark.sql.warehouse.dir", "hdfs://namenode:8020/user/hive/warehouse")

//     createOrders(sparkSession)
//     readOrders(sparkSession)
//   }

//   def createOrders(sparkSession: SparkSession) = {
//     import sparkSession.sql

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

//     sql("DROP TABLE IF EXISTS jea_db.orders")
//     sql("DROP DATABASE IF EXISTS jea_db")
//     sql("CREATE DATABASE IF NOT EXISTS jea_db")
//     sql("CREATE TABLE IF NOT EXISTS jea_db.orders" +
//       "(orderid int, customerid int, campaignid int, orderdate string, city string, state string," +
//       " zipcode string, paymenttype string, totalprice float, numorderlines int, numunits int)")

//     val dfOrdersData = sparkSession.read
//       .format("com.databricks.spark.csv")
//       .option("delimiter", "\t")
//       .option("header", "true")
//       .schema(schemaOrder)
//       .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

//     dfOrdersData
//       .write
//       .mode(SaveMode.Overwrite)
//       .saveAsTable("jea_db.orders")
//   }

//   def readOrders(sparkSession: SparkSession) = {
//     import sparkSession.sql

//     val ordersData = sql("SELECT * FROM jea_db.orders")
//     ordersData.printSchema()
//     ordersData.withColumnRenamed("_c0", "orderid")
//     ordersData.show()
//   }

// }
