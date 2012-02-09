package snakegame

class Controller(view : View, game : Game, moveTrigger : MoveTrigger) {
	def run(){
	  view.show()
	  while(!game.isOver()){
		  view.refresh()
		  moveTrigger.waitForMove()
		  game.makeMove(view.getDecision())
	  }
	  view.close()
	}
}