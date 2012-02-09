package snakegame

import scala.swing._;
import event._
import javax.swing._
import java.awt.image.{BufferedImage}
import java.awt.{Graphics, Color}

class View(width: Int, height: Int, gameToPaint : Game) {
	var lastDecision : Direction.Value = Direction.None

	val bgColor = new Color(5, 5, 5)
	val snakeColor = new Color(0x41, 0x69, 0xE1)

	class Frame(width: Int, height: Int) extends MainFrame {
	   val b = new Panel {
	      listenTo( keys )
	      reactions += {
	         case KeyPressed(_, key,_,_) =>
	            lastDecision = key match {
	              case Key.Up => Direction.Up
	              case Key.Down => Direction.Down
	              case Key.Left => Direction.Left
	              case Key.Right => Direction.Right
	              case _ => lastDecision
	            }
	      }

	      override def paintComponent(g: Graphics2D) {
	    	  val (w, h) = gameToPaint.dimensions
	    	  val dotSize = Math.min(height/h, width/w)

	    	  g.setColor(bgColor)
			  g.fillRect(0, 0, w*dotSize, h*dotSize)

	    	  g.setColor(snakeColor)
			  for(snakeField <- gameToPaint.snake()){
				val x = snakeField.x
				val y = snakeField.y
			    g.fillRect(x*dotSize + 1, y*dotSize + 1, dotSize - 2, dotSize - 2)
			  }
	      }
	   }

	   contents = b
	   peer.setSize(width, height)
	}

   var w : Frame = null

   def show() {
	   w = new Frame(height, width)
	   w.visible = true
	   w.b.requestFocus
   }

   def close() {
     if(w != null)
       w.dispose()
   }

   def getDecision() = lastDecision

   def refresh() {
     w.b.repaint()
   }
}
