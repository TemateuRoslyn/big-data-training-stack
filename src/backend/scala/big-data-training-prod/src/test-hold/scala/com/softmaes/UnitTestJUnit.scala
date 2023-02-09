import org.junit.Test
import junit.framework.TestCase._

class UnitTestJUnit {

  @Test
  def testValidSparkSession(): Unit = {
    val sparkSession = utilities.UtilsSpark.sparkSession()
    assertNotNull("Cette fonction doit retourner un objet non vide", sparkSession)
  }

  @Test
  def testDivise(): Unit = {
    val valAc = HelloWorld.divide(24, 12)
    val valAt = 5
    assertEquals(s"La valeur attendue de la division est $valAt", valAc, valAt)
  }

  @Test
  def testCompteur(): Unit = {
    val l1 = HelloWorld.comptage_caracteres2("Serigne")
    val l2 = HelloWorld.comptage_caracteres3("SERIGNE")

    assertEquals("Les deux méthodes donnent le même résultat", l1, l2)
  }

  @Test
  def testConversion(): Unit = {
    val a = HelloWorld.convert_entier("25")
    assertTrue(a == 25)
  }
}
