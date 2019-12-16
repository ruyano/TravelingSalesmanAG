import objects.Subject
import operators.Operator
import operators.crossover.Crossover
import operators.mutation.Mutation
import operators.selection.Selection
import utils.readFromFile
import java.util.LinkedHashMap

class GeneticAlgorithm (
    val maxGenerationNumber: Int = 50,
    val populationSize: Int = 100,
    val crossOverAmount: Int = populationSize / 4,
    val selectionMethod: Selection,
    val crossoverMethod: Crossover,
    val mutation: Mutation,
    val shouldPrint: Boolean = false
) : Operator(shouldPrint) {

    private var results: MutableMap<Int, Double> = LinkedHashMap()
    private var population = ArrayList<Subject>()

    init {
        initializePopulation()
        selectionMethod.setPopulation(population)
    }

    fun execute() : MutableMap<Int, Double> {
        for (i in 0 until maxGenerationNumber) {
            executeGeneration(i)
        }
        return results
    }

    private fun executeGeneration(i: Int) {
        for (j in 0 until crossOverAmount) {
            generateSons()
        }
        oderPopulationByFitness()
        results[i] = population[0].totalDistance()
        println("Melhor da geração $i = ${population[0]}")
        selectNextPopulation()
    }

    private fun selectNextPopulation() {
        val bests = population.dropLast(crossOverAmount * 2)
        population.clear()
        population.addAll(bests.shuffled())
    }

    private fun oderPopulationByFitness() {
        population.sortByDescending {
            it.fitness()
        }
    }

    private fun generateSons() {
        val father1 = selectionMethod.select()
        val father2 = selectionMethod.select()
        father1?.let { f1 ->
            father2?.let { f2 ->
                val sons = crossoverMethod.execute(f1, f2)
                sons.forEach {
                    population.add(mutation.mutate(it))
                }
            }
        }
    }

    private fun initializePopulation() {
        val distanceMatrix = readFromFile()
        var i = 0
        while (i < populationSize) {
            val newSubject = Subject(distanceMatrix)
            population.forEach { subject ->
                if (isArraysEquals(subject.genes, newSubject.genes)) {
                    return
                }
            }
            population.add(newSubject)
            i++
        }
    }

    private fun isArraysEquals(array1: ArrayList<Int>, array2: ArrayList<Int>): Boolean {
        if (array1.size != array2.size) return false
        array1.forEachIndexed{index, value ->
            if (value != array2[index]) {
                return false
            }
        }
        return true
    }

    private fun printPopulation() {
        population.forEachIndexed() { index, subject ->
            myPrint("subject ${index+1} = $subject")
        }
    }
}