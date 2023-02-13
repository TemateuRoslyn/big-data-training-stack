package com.softmaes.utils

object DataFrameTransformerFactory {
  /**
    * @param transformerClass the fully qualified class name of the transformer to instantiate
    * @return an instance of transformer of the given class
    */
  def getTransformer(transformerClass: String): DataFrameTransformer = {
    getClass.getClassLoader.loadClass(transformerClass).newInstance().asInstanceOf[DataFrameTransformer]
  }
}
