// package com.softmaes.sparkbdd

// import org.apache.spark.sql.SparkSession
// import org.apache.spark.{SparkConf, SparkContext}
// import com.softmaes.utils.UtilsSpark

// // Spark SQL Cassandra imports
// import com.datastax.spark.connector._
// import com.datastax.spark.connector.rdd._
// import org.apache.spark.sql.cassandra._

// import com.datastax.driver.core.Cluster


// object SparkAndCassandra {

//   def main(args: Array[String]): Unit = {
//     System.setProperty("hadoop.home.dir", "C:/Hadoop")
//     val sparkConf = new SparkConf()
//       .setAppName("E-Shopping")
//       .setMaster("local")
//       .set("spark.cassandra.connection.host", "localhost")

//     val sparkContext = new SparkContext(sparkConf)

//     val sparkSession = UtilsSpark.sparkSession(true)

//     showDataFromDb(sparkSession)
//     //testCassandra(sparkContext)

//   }

//   def testCassandra(sparkContext: SparkContext) = {
//     val rdd = sparkContext.cassandraTable("jea_db", "orders")
//     rdd.take(10).foreach(println)
//   }

//   def showDataFromDb(sparkSession: SparkSession) = {
//     val dfCassandra = sparkSession.read
//       .cassandraFormat("orders", "jea_db", "").load()
//     dfCassandra.explain()
//     dfCassandra.show()
//   }

// }
