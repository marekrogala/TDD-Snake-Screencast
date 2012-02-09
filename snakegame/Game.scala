package snakegame

class Game(width: Int, height: Int, length: Int) {
	if(length >= width) 
	  throw new IllegalArgumentException
	  
	def dimensions() = (width, height)
	
	private var snakeBody = for(i <- (1 to length).toList)
						yield new Field((width - length + 1)/2 + (i - 1), height/2)
	
	private var over = false
	
	def snake() = snakeBody

	var movingDirection = Direction.Left
	
	def updateMovingDirection(direction: Direction.Value){
	  if(direction != Direction.None)
	    if(!areOpposite(direction, movingDirection))
	      movingDirection = direction
	}
	
	def areOpposite(dir1 : Direction.Value, dir2: Direction.Value) = {
	    (dir1 == Direction.Right && dir2 == Direction.Left) ||
	    (dir1 == Direction.Down && dir2 == Direction.Up) ||
	    (dir1 == Direction.Up && dir2 == Direction.Down) ||
	    (dir1 == Direction.Left && dir2 == Direction.Right)
	}
	
	def hitsOwnBody(field: Field) = snakeBody.contains(field)
	def outOfBoard(field: Field) = field.x < 0 || field.y < 0 || field.y >= height || field.x >= width
	
	def makeMove(direction: Direction.Value) {
	  updateMovingDirection(direction)
	  val next = snakeBody.first.move(movingDirection)

	  if(hitsOwnBody(next) || outOfBoard(next)) over = true
	  
	  snakeBody = next :: snakeBody.dropRight(1)
	}
	
	def isOver() = over
}