package ru.niner.oneplusll

import java.util
import collection.JavaConversions._
import ru.ifmo.ctd.ngp.demo.testgen.flows.solvers.Dinic

import scala.util.Random

object Main extends App {
  //val run = new OnePlusOne(100,5000,10000, new NGPAlgorithmFitness(new Dinic()), true, 100000, 1)
  //run.run()
  // val run2 = new OnePlusTwoLambda(100,5000,10000, 8, 1.0/5000, new NGPAlgorithmFitness(new Dinic()), true, 100000, 2)
  //run2.run()
  val run = new OnePlusLambdaLambdaAdaptive(100,5000,10000, 1.5, new NGPAlgorithmFitness(new Dinic()), true, 500000, 1)
  run.run()
}
