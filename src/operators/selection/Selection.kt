package operators.selection

import operators.Operator
import objects.Subject

abstract class Selection(shouldPrint: Boolean) : Operator(shouldPrint) {

    val population = ArrayList<Subject>()

    fun setPopulation(population: ArrayList<Subject>) {
        this.population.clear()
        this.population.addAll(population)
    }

    abstract fun select() : Subject?
}