package snakegame

object SnakeApp extends App {
	val game = new Game(12, 18, 8)
	val controller = new Controller(new View(600, 400, game),
									game, new MoveTrigger(300))
	controller.run()
}