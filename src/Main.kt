import operators.crossover.PMX
import operators.mutation.BitFlipMutation
import operators.selection.Roulette

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        GeneticAlgorithm(
            maxGenerationNumber = 50,
            populationSize = 10,
            selectionMethod = Roulette(),
            crossoverMethod = PMX(),
            mutation = BitFlipMutation(),
            shouldPrint = true
        ).execute()
    }
}