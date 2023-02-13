package com.softmaes.jobs.mysql;

import com.google.common.annotations.VisibleForTesting;

import org.apache.spark.sql.SparkSession;

import com.softmaes.readers.impl.DataFrameTableReader;
import com.softmaes.writers.impl.DataFrameTableWriter;
import com.softmaes.utils.DataFrameTransformerFactory;


case class TableTransformerConfig(transformerClass: String, readerConfig: DataFrameTableReader.Config, writerConfig: DataFrameTableWriter.Config)

/**
  * This object reads data from one Spark table, transforms it and writes output to another Spark table.
  *
  * @param spark  the spark session to use for the transformations
  * @param config the config describing transformations to perform
  */
class TableTransformerJob(spark: SparkSession, config: TableTransformerConfig) {

  def run(): Unit = {
    // read data
    val inputDF = newReader().read()

    // transform data
    val outputDF = newTransformer().transform(inputDF)

    // write transformed data out
    newWriter().write(outputDF)
  }

  @VisibleForTesting
  protected def newTransformer(): DataFrameTransformer = {
    DataFrameTransformerFactory.getTransformer(config.transformerClass)
  }

  @VisibleForTesting
  protected def newReader(): DataFrameReader = {
    new DataFrameTableReader(spark, config.readerConfig)
  }

  @VisibleForTesting
  protected def newWriter(): DataFrameWriter = {
    new DataFrameTableWriter(spark, config.writerConfig)
  }
}
