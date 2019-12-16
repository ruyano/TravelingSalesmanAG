import objects.Subject
import operators.Operator
import operators.crossover.Crossover
import operators.mutation.Mutation
import operators.selection.Selection
import utils.readFromFile

class GeneticAlgorithm (
    val maxGenerationNumber: Int = 50,
    val populationSize: Int = 100,
    val crossOverAmount: Int = populationSize / 4,
    val selectionMethod: Selection,
    val crossoverMethod: Crossover,
    val mutation: Mutation,
    val shouldPrint: Boolean = false
) : Operator(shouldPrint) {

    private var population = ArrayList<Subject>()

    init {
        initializePopulation()
        selectionMethod.setPopulation(population)
    }

    fun execute() {
        for (i in 0 until maxGenerationNumber) {
            executeGeneration(i)
        }
    }

    private fun executeGeneration(i: Int) {
        for (j in 0 until crossOverAmount) {
            generateSons()
        }
        oderPopulationByFitness()
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
        for (i in 0 until populationSize) {
            population.add(Subject(distanceMatrix))
        }
    }

    private fun printPopulation() {
        population.forEachIndexed() { index, subject ->
            myPrint("subject ${index+1} = $subject")
        }
    }
}