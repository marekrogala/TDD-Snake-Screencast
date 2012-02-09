package snakegame

class MoveTrigger(interval:Int) {
	def waitForMove(){
	  Thread.sleep(interval)
	}
}