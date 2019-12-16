package operators.mutation

import objects.Subject
import operators.Operator

abstract class Mutation(mutationRate: Double, shouldPrint: Boolean) : Operator(shouldPrint) {

    abstract fun mutate(subject: Subject) : Subject

}