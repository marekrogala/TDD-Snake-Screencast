package snakegame

class Field(val x: Int, val y: Int) {
	final override def equals(other: Any) = {
	  if(other == null) false
	  else {
	    val that = other.asInstanceOf[Field]
	    that.x == x && that.y == y
	  }
	}
	
	def move(direction : Direction.Value) = 
		direction match {
			case Direction.Left => new Field(x - 1, y)
			case Direction.Right => new Field(x + 1, y)
			case Direction.Down => new Field(x, y + 1)
			case _ => new Field(x, y - 1)
	}
}