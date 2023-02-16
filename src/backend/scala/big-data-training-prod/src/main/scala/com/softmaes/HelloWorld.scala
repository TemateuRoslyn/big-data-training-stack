// package com.softmaes

// import java.io.FileNotFoundException
// import scala.io.Source

// object HelloWorld {

//   class Person (var nom : String, var prenom : String, var age : Int)

//   def run(): Unit = {
//     val maVal = "Anne"
//     var maVar = "Marie"
//     println(s"Ma variable immutable: ${maVal}")
//     println(s"Ma variable mutable: ${maVar}")

//     println("********************************** Première test de méthode/procédure *************************")
//     getResultat(10)
//     println("*************************** Test sur le comptatge de caractères *********************************")
//     println(s"Test de la fonction 1: ${comptage_caracteres(maVal)}")
//     println(s"Test de la fonction 2: ${comptage_caracteres2(maVal)}")
//     println(s"Test de la fonction 3: ${comptage_caracteres3(maVal)}")
//     println("*************************** Test de while et for *******************************************")
//     testWhile(7)
//     testFor()
//     println("*********************** Division / Exception *******************************************")
//     val diviseur : Double = try {
//       division(12, 0)
//     } catch {
//       case ex : ArithmeticException => 0
//       case ex2 : IllegalArgumentException => 0
//     }

//     println(s"la valeur de votre division est de ${diviseur}")

//     println("***************************** Lecture d'un fichier ********************************")
//     lecture_fichier(getClass.getClassLoader.getResource("data/persons.txt").getPath)
//     println("********************************** Conversion en nombre entier ***************************************")
//     println("Valeur convertie: " + convert_entier("25"))
//     println("*****************************Collections Scala***********************************")
//     collectionScala()
//     collectionTuples()
//   }

//   //ma première méthode/procédure
//   def getResultat (parametre : Any) : Unit = {
//     if (parametre == 10 ) {
//       println ("votre valeur est un entier")
//     } else {
//       println ("votre valeur n'est pas un entier")
//     }
//   }

//   def comptage_caracteres (texte : String) : Int = {

//     println("démarrage du traçage de la classe")
//     println(s"le paramètre tracé par Log4J pour cette fonction est : $texte")
//     println(s"Message d'avertissement Log4J interpolation de chaînes : ${10 + 15}")

//     if (texte.isEmpty) {
//       0
//     } else {
//       texte.trim.length()
//     }
//   }

//   //syntaxe 2
//   def comptage_caracteres2 (texte : String) : Int = {
//     return texte.trim.length()
//   }
//   //syntaxe 3
//   def comptage_caracteres3 (texte : String) : Int =  texte.trim.length()

//   // structures conditionnelles
//   def testWhile (valeur_cond : Int) : Unit = {
//     var i : Int = 0
//     while (i < valeur_cond) {
//       println("itération while N° " + i)
//       i = i + 1
//     }
//   }

//   def testFor () : Unit = {
//     var i : Int = 0
//     for (i <- 5 to 15 ) {
//       println("itération For N° " + i)
//     }
//   }

//   // utilisation d'un gestionnaire d'erreur
//   def convert_entier (nombre_chaine : String) : Int = {
//     try {
//       val nombre : Int = nombre_chaine.toInt
//       return nombre
//     } catch {
//       case ex : Exception => 0
//     }

//   }

//   def lecture_fichier(chemin_fichier : String) : Unit = {
//     try {
//       val fichier = Source.fromFile(chemin_fichier)
//       for (line <- fichier.getLines()) {
//         println(line)
//       }
//       fichier.getLines()
//       fichier.close()
//     } catch {
//       case ex : FileNotFoundException => println("votre fichier est introuvable. Vérifiez le chemin d'accès"+ ex.printStackTrace())
//     }

//   }

//   def division(numerateur : Int, denominateur : Int) : Double = {
//     val resultat = numerateur/denominateur
//     resultat
//   }

//   //les collections en scala
//   def collectionScala () : Unit = {

//     val maliste: List[Int] = List(1, 2, 3, 5, 10, 45, 15)
//     val liste_s: List[String] = List("julien", "Paul", "jean", "rac", "trec", "joel", "ed", "chris", "maurice")
//     val plage_v: List[Int] = List.range(1, 15, 2)

//     println(maliste(0))

//     for (i <- liste_s) {
//       println(i)
//     }

//     //manipulation des collections à l'aide des fonctions anonymes
//     val resultats: List[String] = liste_s.filter(e => e.endsWith("n"))

//     for (r <- resultats) {
//       println(r)
//     }

//     val res: Int = liste_s.count(i => i.endsWith("n"))
//     println("nombre d'éléments respectant la condition : " + res)

//     val maliste2: List[Int] = maliste.map(e => e * 2)

//     for (r <- maliste2) {
//       println(r)
//     }

//     val maliste3: List[Int] = maliste.map((e: Int) => e * 2)
//     val maliste4: List[Int] = maliste.map(_ * 2)

//     val nouvelle_liste: List[Int] = plage_v.filter(p => p > 5)

//     val new_list: List[String] = liste_s.map(s => s.capitalize)

//     new_list.foreach(e => println("nouvelle liste : " + e))
//     nouvelle_liste.foreach(e => println("nouvelle liste : " + e))
//     plage_v.foreach(println(_))
//   }

//   def collectionTuples () : Unit = {
//     val tuple_test = (45, "JVC", "False")
//     println(tuple_test._3)

//     val nouv_personne : Person = new Person ("CHOKOGOUE", "Juvenal", 40)

//     val tuple_2 = ("test", nouv_personne, 67 )

//     tuple_2.toString().toList
//   }


//   //table de hachage
//   val states = Map(
//     "AK" -> "Alaska",
//     "IL" -> "Illinois",
//     "KY" -> "Kentucky"
//   )

//   val personne = Map(
//     "nom" -> "CHOKOGOUE",
//     "prénom" -> "Juvénal",
//     "age" -> 45
//   )

//   /*// les tableaux ou Array
//   val montableau : Array[String] = Array("juv", "jvc", "test")
//   montableau.foreach(e => println(e))*/

//   def divide(a:Int, b: Int): Double = {
//     val resultat = a/b
//     resultat
//   }
// }
