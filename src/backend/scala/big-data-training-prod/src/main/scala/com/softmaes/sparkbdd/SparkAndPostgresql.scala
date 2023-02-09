// package com.softmaes.sparkbdd

// import org.apache.logging.log4j.{LogManager, Logger}
// import org.apache.spark.sql.{DataFrame, SparkSession}

// import java.sql.{SQLException, SQLSyntaxErrorException}
// import java.util.Properties

// import com.softmaes.utilities.UtilsSpark

// object SparkAndPostgresql {
//   val properties = new Properties();
//   properties.put("user","consult")
//   properties.put("password", "PassWord23!")

//   val traceLog: Logger = LogManager.getLogger("Spark_Postgres_Logger")
//   val prefixUrl = "jdbc:postgresql://"

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession(true)
//     testDbPostgres(sparkSession)
//   }

//   /**
//    * @author Serigne
//    * @param sparkSession Spark Session for creating Dataframe
//    * @see readDataFrameFromPostgresDb
//    */
//   def testDbPostgres(sparkSession: SparkSession) = {
//     /*val dfPostgre1 = sparkSession.read.jdbc("jdbc:postgresql://127.0.0.1:5433/jea_db","orders", properties)
//     dfPostgre1.show()*/

//     val dfPostgresOrders = readDataFrameFromPostgresDb(sparkSession,"localhost:5433","jea_db","orders",
//       properties.getProperty("user"),properties.getProperty("password"))
//     traceLog.info("SHOW DATA FROM POSTGRES.........")
//     if (dfPostgresOrders != null) {
//       dfPostgresOrders.show()
//     }

//     val dfPostgresQuery = readDataFrameFromPostgresDb(sparkSession,"localhost:5433","jea_db",
//     "(select state, city, sum(round(numunits * totalprice)) as commandes_totales from orders group by state, city) table_postgresql",
//       properties.getProperty("user"),properties.getProperty("password"))
//     traceLog.info("SHOW RESULTS QUERY FROM POSTGRES DB.......")
//     if (dfPostgresQuery != null) {
//       dfPostgresQuery.show()
//     }
//   }

//   /**
//    * Read data from Postgres Database
//    * @author Serigne
//    * @param sparkSession  Session of Spark
//    * @param url           Url of database e.g: (localhost:3361)
//    * @param dbName        Name of database we want to use
//    * @param tableName     Name of table where we read data
//    * @param user          User for connecting to database
//    * @param password      Password for connecting to database
//    * @return              Return DataFrame object or null value
//    */
//   def readDataFrameFromPostgresDb(sparkSession: SparkSession, url: String, dbName: String, tableName: String,
//                                   user: String, password: String): DataFrame = {
//     try {
//       val dfRead = sparkSession.read
//         .format("jdbc")
//         .option("url", s"${prefixUrl}${url}/${dbName}")
//         .option("dbtable", s"${tableName}")
//         .option("user", user)
//         .option("password", password)
//         .load()

//       return dfRead

//     } catch {
//       case exception: SQLSyntaxErrorException => traceLog.error(s"SQL Syntax Error: ${exception.getMessage}")
//       case exception: ClassNotFoundException => traceLog.error(s"Class not found: ${exception.getMessage}")
//       case exception: SQLException => traceLog.error(s"SQL Exception: ${exception.getMessage}")
//       case exception: Exception => traceLog.error(s"Exception: ${exception.getMessage}")
//     }
//     null
//   }

//   /**
//    * Write data on Postgres Database
//    * @author Serigne
//    * @param dataFrame   Dataframe which we want to save in Database
//    * @param url         Url for connecting to Database
//    * @param dbName      DbName which we use
//    * @param tableName   TableName with we write data from dataframe
//    * @param user        User for connecting to Database
//    * @param password    Password for connecting to Database
//    */
//   def writeDataFrameFromPostgresDb(dataFrame: DataFrame, url: String, dbName: String, tableName: String,
//                                   user: String, password: String): Unit = {
//     try {
//       dataFrame.write
//         .format("jdbc")
//         .option("url", s"${prefixUrl}${url}/${dbName}")
//         .option("dbtable", tableName)
//         .option("user", user)
//         .option("password", password)
//         .save()

//     } catch {
//       case exception: SQLSyntaxErrorException => traceLog.error(s"SQL Syntax Error: ${exception.getMessage}")
//       case exception: ClassNotFoundException => traceLog.error(s"Class not found: ${exception.getMessage}")
//       case exception: SQLException => traceLog.error(s"SQL Exception: ${exception.getMessage}")
//       case exception: Exception => traceLog.error(s"Exception: ${exception.getMessage}")
//     }
//   }
// }
