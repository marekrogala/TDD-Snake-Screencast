package snakegame

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameTest extends FunSuite with ShouldMatchers {
 
  test("constructed game has given dimensions") {
	  val game = new Game(5, 6, 2)
	  assert(game.dimensions === (5, 6))
  }
  
  test("constructed game has actually given dimensions") {
	  val game = new Game(3, 7, 2)
	  assert(game.dimensions === (3, 7))
  }
  
  test("cannot create game with snake of length equal to board width"){
    intercept[IllegalArgumentException]{
      new Game(5, 6, 5)
    }
  }
  
  test("cannot create game with snake of length equal to board width for other values"){
    intercept[IllegalArgumentException]{
      new Game(2, 6, 2)
    }
  }
  
  test("cannot create game with snake of length greater than board width"){
    intercept[IllegalArgumentException]{
      new Game(2, 6, 4)
    }
  }
  
  def assertSnakeAfterMoves(width: Int, height: Int, snakeLength: Int, moves: List[Direction.Value], 
		  					expectedSnake: List[Field]){
    val game = new Game(width, height, snakeLength)
    for(move <- moves)
      game.makeMove(move)
    game.snake should equal(expectedSnake)
  }
  
  def assertInitialSnake(width: Int, height: Int, snakeLength: Int, expectedSnake: List[Field]){
	  assertSnakeAfterMoves(width, height, snakeLength, List(), expectedSnake)
  }
  
  test("snake of length 1 is initially placed in the middle"){
	  assertInitialSnake(3, 3, 1, List(new Field(1, 1)))
  }
  
  test("snake of length 1 is initially placed in the middle for other values"){
	  assertInitialSnake(5, 7, 1, List(new Field(2, 3)))
  }
  
  test("snake of length 1 is initially placed in the middle, rounding up"){
	  assertInitialSnake(6, 4, 1, List(new Field(3, 2)))
  }
  
  test("snake of length 2 begins of the leftmost field"){
    val game = new Game(4, 3, 2)
    game.snake.first should equal(new Field(1, 1))
  }
  
  test("snake of length 3 begins of the leftmost field"){
    val game = new Game(4, 3, 2)
    game.snake.first should equal(new Field(1, 1))
  }
  
  test("snake should have given length"){
    val game = new Game(5, 5, 3)
    game.snake should have length (3)
  }
  
  test("snake of length 2 is placed horizontally in the middle"){
    assertInitialSnake(4, 3, 2, List(new Field(1, 1), new Field(2, 1)))
  }
  
  test("snake of length 3 is placed horizontally in the middle"){
    assertInitialSnake(5, 3, 3, List(new Field(1, 1), new Field(2, 1), new Field(3, 1)))
  }
  
  test("move with no turn moves snake 1 field to the left"){
    assertSnakeAfterMoves(3, 3, 1, List(Direction.None), List(new Field(0, 1)))
  }
  
  test("move with no turn moves snake 1 field to the left for other dimensions"){
    assertSnakeAfterMoves(5, 5, 1, List(Direction.None), List(new Field(1, 2)))
  }
  
  test("move with no turn moves snake of length 2 one field to the left"){
    assertSnakeAfterMoves(3, 3, 2, List(Direction.None), List(new Field(0, 1), new Field(1, 1)))
  }

  test("initial game is not over"){
    val game = new Game(3, 3, 2)
    game should not be ('over)
  }
  
  def assertGameOverAfter(width: Int, height: Int, length: Int, moves: List[Direction.Value]){
	  val game = new Game(width, height, length)
	  for(move <- moves) 
	    game.makeMove(move)
	  game should be ('over)
  }
  
  test("game after hitting the wall is over"){
    assertGameOverAfter(3, 3, 2, List(Direction.None, Direction.None))
  }

  test("game after hitting the top wall is over"){
    assertGameOverAfter(3, 3, 2, List(Direction.Up, Direction.None))
  }
  
  test("game after hitting the bottom wall is over"){
    assertGameOverAfter(3, 3, 2, List(Direction.Down, Direction.None))
  }
  
  test("game after hitting the right wall is over"){
    assertGameOverAfter(3, 3, 2, List(Direction.Down, Direction.Right, Direction.None))
  }
  
  test("game after move but before hitting the wall is not over"){
    val game = new Game(5, 3, 2)
    game.makeMove(Direction.None)
    game.makeMove(Direction.None)
    game should not be ('over)
  }
  
  test("turn up makes move in the given direction"){
    assertSnakeAfterMoves(3, 3, 2, List(Direction.Up), List(new Field(1, 0), new Field(1, 1)))
  }
  
  test("turn down makes move in the given direction"){
    assertSnakeAfterMoves(3, 3, 2, List(Direction.Down), List(new Field(1, 2), new Field(1, 1)))
  }
  
  test("after turn remembers new direction for next moves"){
    assertSnakeAfterMoves(5, 5, 2, List(Direction.Up, Direction.None), 
        List(new Field(2, 0), new Field(2, 1)))
  }
  
  test("when moving left changing direction to right should be treated as none"){
    assertSnakeAfterMoves(3, 3, 2, List(Direction.Right, Direction.Up),
    		List(new Field(0, 0), new Field(0, 1)))
  }
  
  test("when moving up changing direction to down should be treated as none"){
    assertSnakeAfterMoves(5, 5, 2, List(Direction.Up, Direction.Down),
    		List(new Field(2, 0), new Field(2, 1)))
  }
  
  test("when moving down changing direction to up should be treated as none"){
    assertSnakeAfterMoves(5, 5, 2, List(Direction.Down, Direction.Up),
    		List(new Field(2, 4), new Field(2, 3)))
  }
  
  test("when moving right changing direction to left should be treated as none"){
    assertSnakeAfterMoves(5, 5, 2, List(Direction.Up, Direction.Right, Direction.Left),
    		List(new Field(4, 1), new Field(3, 1)))
  }
  
  test("after hitting own body game over"){
    assertGameOverAfter(7, 7, 5, List(Direction.Up, Direction.Right, Direction.Down))
  }
}