
import org.scalatest.flatspec.AnyFlatSpec
import sparkbdd.SparkAndMysql
import com.softmaes.utils.UtilsSpark
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

class FlatSpecTestData extends AnyFlatSpec {

  "If connection to Mysql Database is not succeeded" should("return") in {
    val sparkSession = UtilsSpark.sparkSession(true)
    val df = SparkAndMysql.readDataFrameFromMySqlDb(
      sparkSession, "localhost:3361", "spark_order_db","orders", "root",
      "PassWord23!")
    assertResult(null)(df)
  }

  it should "Have count 0" in {
    val sparkSession = UtilsSpark.sparkSession(true)
    val df = SparkAndMysql.readDataFrameFromMySqlDb(
      sparkSession, "localhost:3361", "spark_order_db","orders", "root",
      "PassWord23!")
    assert(df.filter(col("orderid")===null).count() == 0)
  }

  it should("Have same data") in {
    val sparkSession = UtilsSpark.sparkSession(true)
    val df1 = SparkAndMysql.readDataFrameFromMySqlDb(
      sparkSession, "localhost:3361", "spark_order_db","orders", "root",
      "PassWord23!")
    val df2 = SparkBigData.readDataFrameFromFile(sparkSession,"C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt",
    "\t",true, null)
    assert(df1.count() == df2.count())
  }

  it should("Content one element") in {
    val sparkSession = UtilsSpark.sparkSession(true)
    val dataFrame = SparkAndMysql.readDataFrameFromMySqlDb(
      sparkSession, "localhost:3361", "spark_order_db","orders", "root",
      "PassWord23!")
    assert(dataFrame.select(max("totalprice")).count() == 1)
  }

  "Data saved successfully" should("return dataframe not null") in {
    val schemaSong = StructType(Array(
      StructField("title", StringType,false),
      StructField("id_song",IntegerType, false)
    ))
    val sparkSession = UtilsSpark.sparkSession(true)
    val dataSongs = SparkBigData.readDataFrameFromFile(sparkSession,
    "C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\songs.txt"," ",false,
      schemaSong)
    SparkAndMysql.writeDataFrameToMySqlDb(dataSongs,"localhost:3361","db_test","song_t",
      "root","PassWord23!")
    val dataResult = SparkAndMysql.readDataFrameFromMySqlDb(
      sparkSession, "localhost:3361", "db_test","song_t", "root",
      "PassWord23!")
    assert(dataResult != null && dataResult.count() != 0)
  }
}
