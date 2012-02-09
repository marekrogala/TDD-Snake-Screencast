package snakegame

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldTest extends FunSuite with ShouldMatchers {
 
  test("can create field with given coordinates") {
	  val field = new Field(1, 3)
	  field should have ('x (1), 'y (3))
  }
  
  test("fields with same x and y are equal"){
    new Field(1, 3) should equal (new Field(1, 3))
  }
  
  test("field is not equal to null"){
    new Field(1, 3) should not equal (null)
  }
  
  test("fields with different x are not equal"){
    new Field(1, 3) should not equal (new Field(2, 3))
  }
  
  test("fields with different y are not equal"){
    new Field(1, 3) should not equal(new Field(1, 4))
  }
  
  def assertPositionAfterMove(initialX: Int, initialY: Int, direction: Direction.Value,
      							expectedX: Int, expectedY: Int){
	  val field = new Field(initialX, initialY)
	  val next = field.move(direction)
	  next should equal(new Field(expectedX, expectedY))
  }
  
  test("move left decreases x"){
    assertPositionAfterMove(3, 4, Direction.Left, 2, 4)
  }
  
  test("move left decreases x from the actual value"){
    assertPositionAfterMove(5, 7, Direction.Left, 4, 7)
  }
  
  test("move right increases x by one"){
    assertPositionAfterMove(2, 3, Direction.Right, 3, 3)
  }
  
  test("move down increases y by one"){
    assertPositionAfterMove(2, 3, Direction.Down, 2, 4)    
  }
  
  test("move up decreases y by one"){
	  assertPositionAfterMove(2, 3, Direction.Up, 2, 2)  
  }
}