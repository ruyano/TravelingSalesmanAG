package operators.crossover

import operators.Operator
import objects.Subject

abstract class Crossover(shouldPrint: Boolean) : Operator(shouldPrint) {
    abstract fun execute(father1: Subject, father2: Subject) : ArrayList<Subject>
}