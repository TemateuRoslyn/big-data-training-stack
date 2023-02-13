package com.softmaes.transformers.impl;


import com.softmaes.transformers.DataFrameTransformer
import org.apache.spark.sql.DataFrame

class IdentityDataFrameTransformer extends DataFrameTransformer {

  override def transform(inputDF: DataFrame): DataFrame = {
    inputDF
  }

}
