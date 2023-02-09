import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.plans.Inner
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{col, concat_ws, current_timestamp, lit, max, round, sum, when}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.storage.StorageLevel

import java.io.FileNotFoundException


object SparkBigData {

  var ss : SparkSession = null
  val traceLog: Logger = LogManager.getLogger("Spark_Big_Data_Logger")

  def main(args: Array[String]): Unit = {

    // val sparkSession = SparkSession.builder.appName("application simple").getOrCreate()
    val sparkSession = session_spark(true)
    val sparkContext = sparkSession.sparkContext
    sparkContext.setLogLevel("OFF")

    // rddPart(sparkContext)
    // dataFramePart(sparkSession)
    // useCase1(sparkContext)
    // advancedRequests(sparkSession)
    // getOrdersByClient(sparkSession)
    // hdfsPart(sparkContext)

    val df = readDataFrameFromFile(sparkSession, "C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt",
    "\t",true,null)
    if (df != null) {
      df.show()
    }
  }

  def hdfsPart(sparkContext: SparkContext): Unit = {
    // Instancier HDFS
    val hadoopConfiguration = new Configuration() // Méthode 1
    val hadoopConf = sparkContext.hadoopConfiguration // Méthode 2
    // Charger le système de fichier à partir de la configuration
    val fs = FileSystem.get(hadoopConfiguration)
    // Définir les chemins des fichiers
    val sourcePath = new Path("")
    val destinationPath = new Path("")
    val renameFile = new Path("")
    val destFile = new Path("")
    val localPath = new Path("")

    // Liste ou lecture des fichiers dans un dossier
    val fileList = fs.listStatus(sourcePath)
    fileList.foreach(println)

    /*//Méthode 2
    val files_list1 = fs.listStatus(src_path).map(x => x.getPath)
    for (i <- 1 to files_list1.length) {
      println(files_list1(i))
    }*/

    //renommer des fichiers
    fs.rename(renameFile, destinationPath)

    //supprimer des fichiers dans un dossier
    fs.delete(destFile, true)

    //copier des fichiers
    fs.copyFromLocalFile(localPath, destinationPath)

    fs.copyToLocalFile(destinationPath, localPath)
  }

  def session_spark(env : Boolean = true): SparkSession ={
    try{
      if (env == true) {
        System.setProperty("hadoop.home.dir", "C:/Hadoop") // sur windows = "C:/Hadoop/"
        ss = SparkSession.builder()
          .master("local[*]")
          .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
          .config("spark.sql.crossJoin.enabled", "true")
          //.enableHiveSupport()
          .getOrCreate()
      } else {
        ss = SparkSession.builder()
          .appName("Mon application Spark")
          .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
          .config("spark.sql.crossJoin.enabled", "true")
          .enableHiveSupport()
          .getOrCreate()
      }
    } catch {
      case ex : FileNotFoundException => println("Nous n'avons pas trouvé le winutils dans le chemin indiqué " + ex.printStackTrace())
      case ex: Exception => println("Erreur dans l'initialisation de la session Spark " + ex.printStackTrace())
    }

    ss

  }

  def rddPart(sparkContext: SparkContext): Unit = {
    println("**************************************** RDD 1 *******************************************")
    val rddTest: RDD[String] = sparkContext.parallelize(List("Roslyn", "Serigne", "Mamadou", "Jaures"))
    rddTest.foreach(elt=>println(elt))
    println("*********************************** RDD 2 ***********************************************")
    val rdd2: RDD[String] = sparkContext.parallelize(Array("Lucie", "fabien", "jules"))
    rdd2.foreach { l => println(l) }
    println("********************************* RDD 3 *************************************************")
    val rdd3 = sparkContext.parallelize(Seq(("Julien", "Math", 15), ("Aline", "Math", 17), ("Juvénal", "Math", 19)))
    println("Premier élément de mon RDD 3")
    rdd3.take(1).foreach { l => println(l) }
    println("****************************** RDD 3 / Affichage des éléments ******************************")
    if (rdd3.isEmpty()) {
      println("le RDD est vide")
    } else {
      rdd3.foreach {
        l => println(l)
      }
    }

    println("******************************** Save RDD 3 in text file ********************************")
    try {
      rdd3.saveAsTextFile("C:\\rdds\\rdd1")
      println("RDD successfully saved")
    } catch {
      case ex: Exception => println(f"Debut Erreur RDD3 ${ex.getMessage}")
    }

    println("******************************** Save RDD 3 Repartition 1 in text file ********************************")
    try {
      rdd3.repartition(1).saveAsTextFile("C:\\rdds\\rdd2")
      println("RDD successfully saved")
    } catch {
      case ex: Exception => println(f"Error: ${ex.getMessage}")
    }

    println("**************************** RDD3 Display *********************************")
    rdd3.foreach { l => println(l) }
    println("**************************** RDD3 Display with collect *********************************")
    rdd3.collect().foreach { l => println(l) }

    // création d'un RDD à partir d'une source de données
    println("************************* RDD from text file *****************************************")
    val rdd4 = sparkContext.textFile("C:\\rdds\\rdd2")
    println("lecture du contenu du RDD4")
    rdd4.foreach { l => println(l) }

    val rdd5 = sparkContext.textFile("C:\\rdds\\*")
    println("lecture du contenu du RDD5")
    rdd5.foreach { l => println(l) }

    println("*********************************** Transformation RDD **********************************")
    // transformation des RDD
    val rdd_trans: RDD[String] = sparkContext.parallelize(List("alain mange une banane", "la banane est un bon aliment pour la santé", "acheter une bonne banane"))
    rdd_trans.foreach(l => println("ligne de mon RDD : " + l))

    val rdd_map = rdd_trans.map(x => x.split(" "))
    println("Nbr d'elements de mon RDD Map : " + rdd_map.count())

    val rdd6 = rdd_trans.map(w => (w, w.length, w.contains("banane"))).map(x => (x._1.toLowerCase, x._2, x._3))
    rdd6.foreach(println)

    val rdd8 = rdd6.map(x => (x._1.split(" "), 1))
    rdd8.foreach(l => println(l._1(0), l._2))

    val rdd_fm = rdd_trans.flatMap(x => x.split(" ")).map(w => (w, 1))

    val rdd_compte = rdd5.flatMap(x => x.split(" ")).map(m => (m, 1)).reduceByKey((x, y) => x + y)
    println("******************************** Comptage RDD ********************************")
    try {
      rdd_compte.repartition(1).saveAsTextFile("C:\\rdds\\ComptageRDD")
    } catch {
      case ex: Exception => println(f"Debut Erreur RDD3 ${ex.getMessage}")
    }
    rdd_compte.foreach(l => println(l))

    val rdd_filtered = rdd_fm.filter(x => x._1.contains("banane"))

    val rdd_reduced = rdd_fm.reduceByKey((x, y) => x + y)
    rdd_reduced.foreach(l => println(l))

    rdd_fm.cache()
    // rdd_fm.persist(StorageLevel.MEMORY_AND_DISK)
    // rdd_fm.unpersist()
  }

  def dataFramePart(sparkSession: SparkSession): Unit = {
    val dfTest = sparkSession.read.format("com.databricks.spark.csv")
      .option("delimiter",",")
      .option("header",true)
      .csv("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2010-12-06.csv")
    // dfTest.show()

    val dfGroup = sparkSession.read.format("csv")
      .option("inferSchema",true)
      .option("header",true)
      .csv("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\csvs\\*")
    // dfGroup.show()

    val dfGroupDif = sparkSession.read.format("csv")
      .option("inferSchema","true")
      .option("header","true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2010-12-06.csv",
      "C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2011-01-20.csv",
      "C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2011-12-08.csv")
    // dfGroupDif.show()

    println("dfTest count: " + dfTest.count())
    println("dfGroup count: " + dfGroup.count())
    println("dfGroupDif count: " + dfGroupDif.count())
    println("Schema dfTest:")
    dfTest.printSchema()

    val dfView =  dfTest.select(
      col("InvoiceNo").cast(StringType),
      col("_c0").alias("ID du client"),
      col("StockCode").cast(IntegerType).alias("code de la marchandise"),
      col("Invoice".concat("No")).alias("ID de la commande")
    )
    // dfView.show()

    val dfWithColumns = dfTest.withColumn("InvoiceNo", col("InvoiceNo").cast(StringType))
      .withColumn("StockCode", col("StockCode").cast(IntegerType))
      .withColumn("valeur_constante", lit(50))
      .withColumnRenamed("_c0", "ID_client")
      .withColumn("ID_commande", concat_ws("|", col("InvoiceNo"), col("ID_client")))
      .withColumn("total_amount", round(col("Quantity") * col("UnitPrice"), 2))
      .withColumn("Created_dt", current_timestamp())
      .withColumn("reduction_test", when(col("total_amount") > 15, lit(3)).otherwise(lit(0)))
      .withColumn("reduction", when(col("total_amount") < 15, lit(0))
        .otherwise(when(col("total_amount").between(15, 20), lit(3))
          .otherwise(when(col("total_amount") > 15, lit(4)))))
      .withColumn("net_income", col("total_amount") - col("reduction"))

    // dfWithColumns.printSchema()
    // dfWithColumns.show(10)

    val dfNotReduction = dfWithColumns.filter(col("reduction")===lit(0)&&col("country").
      isin("USA"))
    // dfNotReduction.show()

    //jointures de data frame

    val schema_order = StructType(Array(
      StructField("orderid", IntegerType, false),
      StructField("customerid", IntegerType, false),
      StructField("campaignid", IntegerType, true),
      StructField("orderdate", TimestampType, true),
      StructField("city", StringType, true),
      StructField("state", StringType, true),
      StructField("zipcode", StringType, true),
      StructField("paymenttype", StringType, true),
      StructField("totalprice", DoubleType, true),
      StructField("numorderlines", IntegerType, true),
      StructField("numunit", IntegerType, true)
    ))


    val df_orders = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .schema(schema_order)
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

    val df_ordersGood = df_orders.withColumnRenamed("numunits", "numunits_order")
      .withColumnRenamed("totalprice", "totalprice_order")

    val df_products = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\product.txt")

    val df_orderlines = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orderline.txt")


    val df_joinOrders = df_orderlines.join(df_ordersGood, df_ordersGood.col("orderid") === df_orderlines.col("orderid"), "inner")
      .join(df_products, df_products.col("productid") === df_orderlines.col("productid"), Inner.sql)

    println(f"Affiche du dataframe df_joinOrders :")

    df_joinOrders.printSchema()

    df_joinOrders.show(10)
  }

  def useCase1(sparkContext: SparkContext): Unit = {
    val schema_order = StructType(Array(
      StructField("orderid", IntegerType, false),
      StructField("customerid", IntegerType, false),
      StructField("campaignid", IntegerType, true),
      StructField("orderdate", TimestampType, true),
      StructField("city", StringType, true),
      StructField("state", StringType, true),
      StructField("zipcode", StringType, true),
      StructField("paymenttype", StringType, true),
      StructField("totalprice", DoubleType, true),
      StructField("numorderlines", IntegerType, true),
      StructField("numunit", IntegerType, true)
    ))

    val df_orders = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .schema(schema_order)
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")
    // println("Schema df_oders")
    // df_orders.printSchema()

    val df_ordersGood = df_orders.withColumnRenamed("numunits", "numunits_order")
      .withColumnRenamed("totalprice", "totalprice_order")

    val df_products = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\product.txt")
    // println("Schema df_products")
    // df_products.printSchema()

    val df_orderlines = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orderline.txt")
    // println("Schema df_oderlines")
    // df_orderlines.printSchema()


    val df_joinOrders = df_orderlines.join(df_ordersGood, df_ordersGood.col("orderid") ===
      df_orderlines.col("orderid"), "inner").groupBy("productid","state").count()
      .sort(col("count").desc)
      .dropDuplicates("state").sort(col("count").desc)
      //.join(df_products, df_products.col("productid") === df_orderlines.col("productid"), Inner.sql)
    val part = Window.partitionBy("state")

    println("Les produits les plus vendus par pays: ")
    df_joinOrders.printSchema()
    df_joinOrders.show()
  }

  def advancedRequests(sparkSession: SparkSession): Unit = {
    val df_fichier1 = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ",")
      .option("header", "true")
      .csv("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2010-12-06.csv")

    val df_fichier2 = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ",")
      .option("header", "true")
      .csv("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2011-12-08.csv")

    val df_fichier3 = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ",")
      .option("header", "true")
      .csv("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\2011-01-20.csv")

    val df_unitedfiles = df_fichier1.union(df_fichier2.union(df_fichier3))
    // df_unitedfiles2 = df_fichier1.union(df_fichier2)
    //.union(df_fichier3)
    // println( df_fichier3.count() + " " + df_unitedfiles.count())

    //opération de GroupBy
    println("GroupBy : ")

    val schema_order = StructType(Array(
      StructField("orderid", IntegerType, false),
      StructField("customerid", IntegerType, false),
      StructField("campaignid", IntegerType, true),
      StructField("orderdate", TimestampType, true),
      StructField("city", StringType, true),
      StructField("state", StringType, true),
      StructField("zipcode", StringType, true),
      StructField("paymenttype", StringType, true),
      StructField("totalprice", DoubleType, true),
      StructField("numorderlines", IntegerType, true),
      StructField("numunit", IntegerType, true)
    ))


    val df_orders = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .schema(schema_order)
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

    val df_ordersGood = df_orders.withColumnRenamed("numunits", "numunits_order")
      .withColumnRenamed("totalprice", "totalprice_order")

    val df_products = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\product.txt")

    val df_orderlines = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orderline.txt")


    val df_joinOrders = df_orderlines.join(df_ordersGood, df_ordersGood.col("orderid") === df_orderlines.col("orderid"), "inner")
      .join(df_products, df_products.col("productid") === df_orderlines.col("productid"), Inner.sql)

    df_joinOrders.withColumn("total_amount", round(col("numunits") * col("totalprice"), 3))
      .groupBy("state", "city")
      .sum("total_amount").as("Commandes totales")
      .show()

    //opérations de fenêtrage
    val wn_specs = org.apache.spark.sql.expressions.Window.partitionBy(col("state"))
    val df_windows = df_joinOrders.withColumn("ventes_dep", sum(round(col("numunits") * col("totalprice"), 3)).over(wn_specs))
      .select(
        col("orderlineid"),
        col("zipcode"),
        col("PRODUCTGROUPNAME"),
        col("state"),
        col("ventes_dep").alias("Ventes_par_département")
      )
    df_windows.show(10)

    df_windows.write
      .format("com.databricks.spark.csv")
      .option("header", true)
      .mode(SaveMode.Overwrite)
      .save("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\saves")
  }

  def getOrdersByClient(sparkSession: SparkSession): Unit = {
    val schema_order = StructType(Array(
      StructField("orderid", IntegerType, false),
      StructField("customerid", IntegerType, false),
      StructField("campaignid", IntegerType, true),
      StructField("orderdate", TimestampType, true),
      StructField("city", StringType, true),
      StructField("state", StringType, true),
      StructField("zipcode", StringType, true),
      StructField("paymenttype", StringType, true),
      StructField("totalprice", DoubleType, true),
      StructField("numorderlines", IntegerType, true),
      StructField("numunit", IntegerType, true)
    ))


    val df_orders = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .schema(schema_order)
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orders.txt")

    val df_orderlines = ss.read
      .format("com.databricks.spark.csv")
      .option("delimiter", "\t")
      .option("header", "true")
      .load("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\orderline.txt")
    df_orderlines.printSchema()
    df_orderlines.show()

    val dfJoined = df_orderlines.join(df_orders, df_orders.col("orderid") ===
      df_orderlines.col("orderid"), "inner").groupBy("customerid").count().withColumnRenamed(
      "count", "totalorders"
    )
    dfJoined.show()
  }

  /**
   *
   * @param sparkSession   Session of Spark
   * @param file           Path of the file which we want to read
   * @param delimiter      Separate column in file
   * @param header         true if the file content header line and false if not
   * @return               Dataframe from data file
   */
  def readDataFrameFromFile(sparkSession: SparkSession,file: String,delimiter: String, header:Boolean,
                            schema: StructType): DataFrame = {
    try {
      val dataFrame = sparkSession.read
        .format("com.databricks.spark.csv")
        .option("delimiter", delimiter)
        .option("header", header)
        .schema(schema)
        .load(file)
      traceLog.info("****************** SUCCESS *************************")
      return dataFrame
    } catch {
      case exception: Exception => traceLog.error(s"Exception: ${exception.getMessage}")
    }
    null
  }
}