package snakegame

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito.{when, verify, inOrder}

@RunWith(classOf[JUnitRunner])
class ControllerTest extends FunSuite with ShouldMatchers with MockitoSugar with BeforeAndAfter {
 
  var view : View = null
  var moveTrigger : MoveTrigger = null
  
  before {
    view = mock[View]
    moveTrigger = mock[MoveTrigger]
  }
  
  test("controller shows view and closes it after game over") {
	  val game = mock[Game]
	  val controller = new Controller(view, game, moveTrigger)
	  
	  when(game.isOver).thenReturn(true)
	  
	  controller.run()
	  
	  verify(view).show()
	  verify(view).close()
	  verify(game).isOver()
  }
  
  def assertMoveCalledWithDecision(decision: Direction.Value){
    val game = mock[Game]
    val controller = new Controller(view, game, moveTrigger)
    
    when(game.isOver()).thenReturn(false, true)
    when(view.getDecision).thenReturn(decision)
    
    controller.run()
    verify(game).makeMove(decision)
  }
  
  test("controller calls game with move"){
    assertMoveCalledWithDecision(Direction.Up)
  }
  
  test("controller calls game with move actually given by view"){
    assertMoveCalledWithDecision(Direction.Down)
  }
  
  test("controller gets decision until game over, then closes view"){
    val game = new Game(5, 5, 1)
    val controller = new Controller(view, game, moveTrigger)
    val order = inOrder(view)
    
    when(view.getDecision()).thenReturn(Direction.None)
    
    controller.run()
    
    order.verify(view).show()
    for(i <- 1 to 3){
      order.verify(view).refresh()
      order.verify(view).getDecision()
    }
    order.verify(view).close()
  }
  
  test("controller uses move trigger before each move"){
    val game = new Game(5, 5, 1)
    val controller = new Controller(view, game, moveTrigger)
    val order = inOrder(view, moveTrigger)
    
    when(view.getDecision()).thenReturn(Direction.None)
    
    controller.run()
    
    for(i <- 1 to 3){
      order.verify(moveTrigger).waitForMove()
      order.verify(view).getDecision()
    }
  }
}