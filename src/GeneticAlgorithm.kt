import objects.Subject
import operators.crossover.Crossover
import operators.selection.Selection
import utils.readFromFile

class GeneticAlgorithm(
    val maxGenerationNumber: Int = 50,
    val populationSize: Int = 100,
    val crossOverAmount: Int = populationSize / 4,
    val selectionMethod: Selection,
    val crossoverMethod: Crossover
) {

    private var population = ArrayList<Subject>()

    init {
        initializePopulation()
//        printPopulation()
        selectionMethod.setPopulation(population)
    }

    fun execute() {
        for (i in 0 until maxGenerationNumber) {
            for (j in 0 until crossOverAmount) {
                val father1 = selectionMethod.select()
                val father2 = selectionMethod.select()
                father1?.let {f1 ->
                    father2?.let { f2 ->
                        val sons = crossoverMethod.execute(f1, f2)
                        population.addAll(sons)
                    }
                }
            }
            population.sortByDescending {
                it.fitness()
            }
            println("Melhor da geração $i = ${population[0]}")
//            println("População ordenada")
//            printPopulation()
            val bests = population.dropLast(crossOverAmount * 2)
            population.clear()
            population.addAll(bests.shuffled())
//            println("Melhores 100")
//            printPopulation()
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
            print("subject ${index+1} = $subject\n")
        }
    }
}