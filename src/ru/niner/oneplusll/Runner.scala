package ru.niner.oneplusll

import java.util
import collection.JavaConversions._
import ru.ifmo.ctd.ngp.demo.testgen.flows.solvers.{ImprovedShortestPath, DinicSlow, Dinic}

object Runner extends App {
  val numberOfNodes = 100
  val numberOfEdges = 5000
  val maximumCapacity = 10000
  val lambdaValues = Array(8,16,25,50)
  val isAcyclic = true
  val computationsLimit = 500000
  val numberOfLaunches = 2
  val adaptationCoefficient = 1.5
  //val onePlusTwoLambdaProbabilities = Array(1.0/numberOfEdges)

  val runs = new util.ArrayList[Runnable]()
  val idAssigner = new RunIDAssigner()

  for (i <- 0 until numberOfLaunches) {

    runs.add(new NiceGeneticsRunnable(numberOfNodes,numberOfEdges,maximumCapacity,100,
      45,new NGPAlgorithmFitness(new ImprovedShortestPath()),isAcyclic,computationsLimit,idAssigner.getNextID))

    //runs.add(new OnePlusOneRunnable(numberOfNodes, numberOfEdges, maximumCapacity, new NGPAlgorithmFitness(new ImprovedShortestPath()),
    //  isAcyclic, computationsLimit, idAssigner.getNextID))
    //runs.add(new OnePlusOneRunnable(numberOfNodes, numberOfEdges, maximumCapacity, new NGPAlgorithmFitness(new DinicSlow()),
    //  isAcyclic, computationsLimit, idAssigner.getNextID))

    //runs.add(new OnePlusLambdaLambdaAdaptiveRunnable(numberOfNodes,numberOfEdges, maximumCapacity, adaptationCoefficient,
    //  new NGPAlgorithmFitness(new ImprovedShortestPath()), isAcyclic, computationsLimit, idAssigner.getNextID))
    //runs.add(new OnePlusLambdaLambdaAdaptiveRunnable(numberOfNodes,numberOfEdges, maximumCapacity, adaptationCoefficient,
    //  new NGPAlgorithmFitness(new DinicSlow()), isAcyclic, computationsLimit, idAssigner.getNextID))


    //for (lambda <- lambdaValues) {
      //runs.add(new OnePlusLambdaLambdaRunnable(numberOfNodes, numberOfEdges, maximumCapacity, lambda,
      //  1.0*lambda/numberOfEdges, 1.0/lambda, new NGPAlgorithmFitness(new ImprovedShortestPath()),
      //  isAcyclic, computationsLimit, idAssigner.getNextID))

      //runs.add(new OnePlusTwoLambdaRunnable(numberOfNodes, numberOfEdges, maximumCapacity, lambda,
      //  1.0/numberOfEdges, new NGPAlgorithmFitness(new ImprovedShortestPath()), isAcyclic,
      //   computationsLimit, idAssigner.getNextID ))

      //runs.add(new OnePlusLambdaLambdaRunnable(numberOfNodes, numberOfEdges, maximumCapacity, lambda,
      //  1.0*lambda/numberOfEdges, 1.0/la  mbda, new NGPAlgorithmFitness(new DinicSlow()),
      //  isAcyclic, computationsLimit, idAssigner.getNextID))

      //runs.add(new OnePlusTwoLambdaRunnable(numberOfNodes, numberOfEdges, maximumCapacity, lambda,
      //  1.0/numberOfEdges, new NGPAlgorithmFitness(new DinicSlow()), isAcyclic,
      //  computationsLimit, idAssigner.getNextID ))
    //}
  }

  println("Total Runs: " + runs.size())
  util.Collections.shuffle(runs)

  for (i : Runnable <- runs) {
    new Thread(i).start
  }
}

class RunIDAssigner() {
  var ID = 1
  def getNextID : Int = {
    val id = ID
    ID += 1
    //println("Assigning ID " + id)
    id
  }
}

class OnePlusOneRunnable(nodeNumber : Int, edgeNumber : Int, maximumCapacity : Int,
                         fitnessFunction : FitnessFunction, isAcyclic : Boolean,
                         computationsLimit : Int, runID : Int)  extends Runnable {
  def run(): Unit = {
    println("Started 1+1 RunID: " + runID)
    new OnePlusOne(nodeNumber, edgeNumber, maximumCapacity, fitnessFunction,
      isAcyclic, computationsLimit, runID).run()
    println("Completed 1+1 RunID: " + runID)
  }
}

class OnePlusTwoLambdaRunnable(nodeNumber : Int, edgeNumber : Int, maximumCapacity : Int,
                               lambda : Int, mutationProbability : Double, fitnessFunction : FitnessFunction,
                               isAcyclic : Boolean, computationsLimit : Int, runID : Int) extends Runnable {
  def run(): Unit = {
    println("Started 1+2L RunID: " + runID)
    new OnePlusTwoLambda(nodeNumber, edgeNumber, maximumCapacity, lambda, mutationProbability,
      fitnessFunction, isAcyclic, computationsLimit, runID).run()
    println("Completed 1+2L RunID: " + runID)
  }
}

class OnePlusLambdaLambdaRunnable(nodeNumber : Int, edgeNumber : Int, maximumCapacity : Int,
                                  lambda : Int, mutationProbability : Double, crossoverProbabilityForA : Double,
                                  fitnessFunction : FitnessFunction, isAcyclic : Boolean, computationsLimit : Int,
                                  runID : Int) extends Runnable {
  def run(): Unit = {
    println("Started 1+LL RunID: " + runID)
    new OnePlusLambdaLambda(nodeNumber, edgeNumber, maximumCapacity, lambda, mutationProbability,
      crossoverProbabilityForA, fitnessFunction, isAcyclic, computationsLimit, runID).run()
    println("Completed 1+LL RunID: " + runID)
  }
}

class OnePlusLambdaLambdaAdaptiveRunnable(nodeNumber : Int, edgeNumber : Int, maximumCapacity : Int,
                                          adaptationCoefficient : Double, fitnessFunction : FitnessFunction,
                                          isAcyclic : Boolean, computationsLimit : Int, runID : Int) extends Runnable {
  def run(): Unit = {
    println("Started 1+LL Adaptive RunID: " + runID)
    new OnePlusLambdaLambdaAdaptive(nodeNumber, edgeNumber, maximumCapacity, adaptationCoefficient,
      fitnessFunction, isAcyclic, computationsLimit, runID).run()
    println("Completed 1+LL Adaptive RunID: " + runID)
  }
}

class NiceGeneticsRunnable(nodeNumber : Int, edgeNumber : Int, maximumCapacity : Int,
                            val generationSize : Int, val crossoverSize : Int, val fitnessFunction: FitnessFunction,
                            val isAcyclic : Boolean, val computationsLimit : Int, runID : Int) extends  Runnable {
  def run() : Unit = {
    println("Started NiceGenetics RunID: " + runID)
    new NiceGenetics(nodeNumber,edgeNumber,maximumCapacity,generationSize,crossoverSize,fitnessFunction,isAcyclic,computationsLimit,runID).run()
    println("Completed NiceGenetics RunID: " + runID)
  }
}