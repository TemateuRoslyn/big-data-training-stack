// package com.softmaes.sparkbdd

// import com.datastax.spark.connector.toSparkContextFunctions
// import org.apache.logging.log4j.{LogManager, Logger}
// import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
// import org.apache.spark.sql.cassandra.DataFrameReaderWrapper

// import java.io.IOException
// import java.util.Properties

// import com.softmaes.utilities.UtilsSpark

// object SparkCassandra {

//   val properties = new Properties();
//   properties.put("catalog", "com.datastax.spark.connector.datasourceCatalog")
//   properties.put("source", "org.apache.spark.sql.cassandra")

//   val traceLog: Logger = LogManager.getLogger("Spark_Cassandra_Logger")

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession(true)
//     // sparkSession.sparkContext.cassandraTable("jea_db", "orders")
//     /*val dfOrders2 = sparkSession.read
//       .cassandraFormat("orders", "jea_db","order_id")
//       .load()*/
//     val dfOrders = readDataFromCassandra(sparkSession,
//       properties.getProperty("source"),"jea_db","orders")
//     if (dfOrders != null) {
//       dfOrders.printSchema()
//       dfOrders.explain()
//       dfOrders.show()
//     }
//   }

//   /**
//    * Read data from Cassandra
//    * @author Serigne
//    * @param sparkSession  Spark session for defining Dataframe
//    * @param source        Connector of cassandra
//    * @param keySpace      Keyspace or database name which we use
//    * @param tableName     Name of the table which we do queries
//    * @param cluster       cluster
//    * @return              Dataframe object or null value
//    */
//   def readDataFromCassandra(sparkSession: SparkSession, source: String,
//                             keySpace:String, tableName:String, cluster: String = ""): DataFrame = {
//     try {
//       val dataFrame = sparkSession.read
//         .format(source)
//         .options(Map("keyspace"->keySpace, "table"->tableName, "cluster" -> cluster))
//         .load()
//       return dataFrame
//     } catch {
//       case exception: IOException => traceLog.error(s"IO Exception: ${exception.getMessage}")
//       case exception: ClassNotFoundException => traceLog.error(s"Class Not Found: ${exception.getMessage}")
//       case exception: Exception => traceLog.error(s"Exception: ${exception.printStackTrace()}")
//     }
//     null
//   }

//   /**
//    * Write data on Cassandra DB
//    * @author Serigne
//    * @param dataFrame  Data which we write on Cassandra db
//    * @param source     Connector of cassandra
//    * @param keySpace   Keyspace or database name
//    * @param tableName  Name of table
//    * @param level      level
//    * @param ttl        ttl
//    */
//   def writeDataToCassandra(dataFrame: DataFrame,source: String,keySpace:String,tableName:String,
//                            level:String="ALL",ttl:String): Unit ={
//     try {
//       dataFrame.write
//         .mode(SaveMode.Append)
//         .format(source)
//         .options(Map("keyspace"->keySpace, "table"->tableName, "output.consistency.level" -> level, "ttl" -> ttl))
//         .save()
//     } catch {
//       case exception: IOException => traceLog.error(s"IO Exception: ${exception.getMessage}")
//       case exception: ClassNotFoundException => traceLog.error(s"Class Not Found: ${exception.getMessage}")
//       case exception: Exception => traceLog.error(s"Exception: ${exception.printStackTrace()}")
//     }
//   }
// }
