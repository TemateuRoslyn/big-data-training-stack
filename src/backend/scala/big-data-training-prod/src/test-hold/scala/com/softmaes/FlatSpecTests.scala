import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.startWith
import org.scalatest.matchers.should.Matchers._

class FlatSpecTests extends AnyFlatSpec {

  "La session Spark" should(" doit être non null") in {
    assert(utilities.UtilsSpark.sparkSession(true) != null)
  }

  it should("Il doit renvoyer l'exception OutofBound error") in {
    var l_fruits: List[String] = List("banne","orange", "pomme")
    assertThrows[IndexOutOfBoundsException](l_fruits(4))
  }

  it should("Doit returner la chaine de début") in {

    val chaine: String ="chaine de caractères"

    chaine should startWith("c")
  }
}
