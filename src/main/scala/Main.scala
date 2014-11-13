import java.math.BigDecimal
import akka.actor.{Props, ActorSystem, Actor}
object Main extends App{
  var pie=0.0
  var threads=0
  var othreads=0
  var time=0.0

  class PiCollector(key: String) extends Actor {
    val system = ActorSystem("pi")
    val pi = system.actorOf(Props(new Pi("pi")), name = "picollect")
    val system1 = ActorSystem("pi")
    val pi1=system1.actorOf(Props(new Pi("pi")), name = "picollect")
    /*val system2 = ActorSystem("pi")
    val system3 = ActorSystem("pi")
    val system4 = ActorSystem("pi")
    val pi = system.actorOf(Props(new Pi("pi")), name = "picollect")
    val pi1=system1.actorOf(Props(new Pi("pi")), name = "picollect")
    val pi2=system2.actorOf(Props(new Pi("pi")), name = "picollect")
    val pi3=system3.actorOf(Props(new Pi("pi")), name = "picollect")
    val pi4=system4.actorOf(Props(new Pi("pi")), name = "picollect")*/
    def receive = {
      case data:Int=>
        val value=data/2
        othreads=data
        for(i<-0 to value)
        {
          pi!i
          pi1!(i+1+value)
          /*pi1!(i+1+value)
          pi2!(i+2+value*2)
          pi3!(i+3+value*3)
          pi4!(i+4+value*4)*/
        }
      case data:Double =>
       pie=pie+data
       threads=threads+1
       if(threads==othreads) {
         println("pi=" + new BigDecimal(pie * 4))
         println("time taken:" + (System.currentTimeMillis() - time) / 1000)
       }

      case _ => println(key)
    }
  }

  class Pi(key:String) extends Actor{
    def receive=
    {
      case data:Int => sender ! (Math.pow(-1,data)/(2*data + 1))

    }
  }

  val system = ActorSystem("uWotM8")
  val letsPi = system.actorOf(Props(new PiCollector("noname01.cpp")), name = "noname01.cpp")
  time=System.currentTimeMillis()
  println("started...")
  letsPi ! 1000000

}
