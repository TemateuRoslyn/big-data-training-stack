package com.softmaes.readers.impl;

import org.apache.spark.sql.{DataFrame, SparkSession}

import com.softmaes.readers.DataFrameReader;

object DataFrameTableReader {

  case class Config(tableName: String)

}

class DataFrameTableReader(spark: SparkSession, config: DataFrameTableReader.Config) extends DataFrameReader {

  override def read(): DataFrame = {
    spark.sql(s"select * from ${config.tableName}")
  }

}
