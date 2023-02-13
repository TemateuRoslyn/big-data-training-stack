// package com.softmaes.sparkbdd

// import org.apache.spark.sql.SparkSession
// import sparkbdd.SparkAndPostgresql.properties

// import java.util.Properties

// import com.softmaes.utils.UtilsSpark

// object SparkAndSqlServer {

//   val properties = new Properties();
//   properties.put("user","sa")
//   properties.put("password", "PassWord23!")

//   def main(args: Array[String]): Unit = {
//     val sparkSession = UtilsSpark.sparkSession(true)
//     testDbSqlServer(sparkSession)
//   }

//   def testDbSqlServer(sparkSession: SparkSession) = {
//     val dfSqlServer1 = sparkSession.read.jdbc("jdbc:sqlserver://mssqlserver/localhost:1433;integratedSecurity=true;" +
//       "databaseName=jea_db", "orders", properties)

//     dfSqlServer1.show(15)

//     dfSqlServer1.printSchema()

//     val dfSqlServer2 = sparkSession.read
//       .format("jdbc")
//       .option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
//       .option("url", "jdbc:sqlserver://mssql/127.0.0.1:1433;databaseName=jea_db;useNTMLV2=true")
//       .option("user", "consultant")
//       .option("password", "P@sser123")
//       .option("dbtable", "orders")
//       .load()

//     dfSqlServer2.show()
//   }
// }
