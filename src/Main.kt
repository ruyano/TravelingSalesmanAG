import operators.crossover.PMX
import operators.selection.Roulette

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        GeneticAlgorithm(
            maxGenerationNumber = 50,
            populationSize = 10,
            selectionMethod = Roulette(),
            crossoverMethod = PMX()
        ).execute()
    }
}