// package com.softmaes.sparkbdd

// import org.apache.spark.sql.{DataFrame, SparkSession}
// import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}
// import org.apache.hadoop.hbase.spark.datasources._

// import org.apache.hadoop.hbase.spark.HBaseContext
// import org.apache.hadoop.hbase.HBaseConfiguration

// import com.softmaes.utils.UtilsSpark

// object SparkAndHbase {

//   def catalogOrders =
//     s"""{
//        |"table":{"namespace":"default", "name":"table_orders"},
//        |"rowkey":"key",
//        |"columns":{
//        |     "order_id":{"cf":"rowkey", "col":"key", "type":"string"},
//        |     "customer_id":{"cf":"orders", "col":"customerid", "type":"string"},
//        |     "campaign_id":{"cf":"orders", "col":"campaignid", "type":"string"},
//        |     "order_date":{"cf":"orders", "col":"orderdate", "type":"string"},
//        |     "city":{"cf":"orders", "col":"city", "type":"string"},
//        |     "state":{"cf":"orders", "col":"state", "type":"string"}
//        |     "zipcode":{"cf":"orders", "col":"zipcode", "type":"string"}
//        |     "paymenttype":{"cf":"orders", "col":"paymenttype", "type":"string"}
//        |     "totalprice":{"cf":"orders", "col":"totalprice", "type":"float"}
//        |     "numorderlines":{"cf":"orders", "col":"numorderlines", "type":"int"}
//        |     "numunits":{"cf":"orders", "col":"numunits", "type":"int"}
//        |}""".stripMargin

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession(true)

//     writeOnHbase(sparkSession)
//     // readFromHbase(sparkSession)
//   }

//   def writeOnHbase(sparkSession: SparkSession) = {
//     val conf = HBaseConfiguration.create()
//     conf.set("hbase.zookeeper.quorum", "127.0.0.1:2181")
//     new HBaseContext(sparkSession.sparkContext, conf)
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
//     // Ecriture dans HBase
//     val dfOrders = sparkSession.read
//       .format("com.databricks.spark.csv")
//       .option("delimiter", "\t")
//       .option("header", "true")
//       .schema(schemaOrder)
//       .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

//     dfOrders.write
//       .format("org.apache.hadoop.hbase.spark")
//       .options(Map(HBaseTableCatalog.tableCatalog -> catalogOrders, HBaseTableCatalog.newTable -> "3"))
//       .save()
//   }

//   def getViewFromDf(dfOrders: DataFrame, sparkSession: SparkSession) = {
//     // travailler en sql
//     dfOrders.createOrReplaceTempView("Orders")
//     sparkSession.sql("select * from Orders where state = 'MA'").show()
//   }

//   def readFromHbase(sparkSession: SparkSession) = {
//     // Lecture dans HBase
//     val dfOrders = sparkSession.read
//       .options(Map(HBaseTableCatalog.tableCatalog -> catalogOrders))
//       .format("org.apache.spark.sql.execution.datasources.hbase")
//       .load()

//     dfOrders.printSchema()
//     dfOrders.show()

//     getViewFromDf(dfOrders, sparkSession)
//   }
// }
