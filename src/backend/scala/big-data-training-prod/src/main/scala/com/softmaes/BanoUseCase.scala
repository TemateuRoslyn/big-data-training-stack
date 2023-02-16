// import org.apache.hadoop.conf.Configuration
// import org.apache.hadoop.fs.{FileSystem, Path}
// import org.apache.spark.sql.SaveMode
// import org.apache.spark.sql.functions.{col, lit, substring, when}
// import org.apache.spark.sql.types.{StringType, StructField, StructType}

// import com.softmaes.utilities.UtilsSpark

// import java.io.File

// object BanoUseCase {

//   def main(args: Array[String]): Unit = {
//     val schema_bano = StructType(Array(
//       StructField("id_bano", StringType, false),
//       StructField("numero_voie", StringType, false),
//       StructField("nom_voie", StringType, false),
//       StructField("code_postal", StringType, false),
//       StructField("nom_commume", StringType, false),
//       StructField("code_source_bano", StringType, false),
//       StructField("latitude", StringType, true),
//       StructField("longitude", StringType, true)
//     ))

//     val hadoopConfiguration = new Configuration()
//     val fileSystem = FileSystem.get(hadoopConfiguration)

//     val destinationPath = new Path("C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data")
//     // val sourcePath = new Path("")

//     val sessionSpark = UtilsSpark.sparkSession(true)

//     val dfBanoBrut = sessionSpark.read
//       .format("com.databricks.spark.csv")
//       .option("delimiter", ",")
//       .option("header", "false")
//       .schema(schema_bano)
//       .load("C:\\Users\\hp\\Downloads\\DataBano.csv")
//     //dfBanoBrut.show(10)

//     val dfBano = dfBanoBrut
//       .withColumn("code_departement", substring(col("code_postal"), 1, 2))
//       .withColumn("libelle_source", when(col("code_source_bano")===lit("OD"), lit("OpenData"))
//       .otherwise(when(col("code_source_bano")===lit("OSM"), lit("OpenData GSM"))
//       .otherwise(when(col("code_source_bano")===lit("CAD"), lit("Cadastre"))
//       .otherwise(when(col("code_source_bano")===lit("C+O"), lit("Cadastre GSM"))))))
//     // dfBano.show()

//     val dfDepartement = dfBano
//       .select(col("code_departement"))
//       .distinct()
//       .filter(col("code_departement").isNotNull)
//     // dfDepartement.show()

//     val liste_departement = dfBano
//       .select(col("code_departement"))
//       .distinct()
//       .filter(col("code_departement").isNotNull)
//       .collect()
//       .map(x => x(0)).toList

//     //liste_departement.foreach(e => println (e.toString))

//     liste_departement.foreach{
//       x =>
//         val sourcePath = new Path(f"C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\bano_${x.toString}")
//         dfBano.filter((col("code_departement")===x.toString))
//         .coalesce(1)
//         .write
//         .format("com.databricks.spark.csv")
//         .option("delimiter", ",")
//         .option("header", "true")
//         .mode(SaveMode.Overwrite)
//         .csv(sourcePath.toString)

//         val destPath= new Path(f"C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\copy\\bano_${x.toString}\\")

//         val file = new File(destPath.toString)
//         file.mkdir()

//         fileSystem.listStatus(sourcePath).foreach(u => fileSystem.copyFromLocalFile(u.getPath, destPath))

//     }


//     dfDepartement.foreach {
//       dep =>dfBano.filter((col("code_departement") === dep.toString))
//         .repartition(1)
//         .write
//         .format("com.databricks.spark.csv")
//         .option("delimiter", ",")
//         .option("header", "true")
//         .mode(SaveMode.Overwrite)
//         .csv(f"C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\bano_${dep.toString}_df")

//         val chemin_src = new Path(f"C:\\Users\\hp\\IdeaProjects\\BigdataTest\\src\\main\\resources\\data\\bano_${dep.toString}_df")

//         // fileSystem.copyFromLocalFile(chemin_src, destinationPath)
//     }
//   }
// }
