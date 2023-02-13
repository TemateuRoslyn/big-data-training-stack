package com.softmaes.writers.impl;

import org.apache.spark.sql.{DataFrame, SparkSession};

import com.softmaes.writers.DataFrameWriter;

object DataFrameTableWriter {

  case class Config(tableName: String, outputFormat: String) {
    require(Seq("orc", "parquet").contains(outputFormat), s"unsupported output format $outputFormat")
  }

}

class DataFrameTableWriter(spark: SparkSession, config: DataFrameTableWriter.Config) extends DataFrameWriter {

  override def write(df: DataFrame): Unit = {
    require(df != null, "df session must be specified")
    df.write
      .format(config.outputFormat)
      .insertInto(config.tableName)
  }

}

