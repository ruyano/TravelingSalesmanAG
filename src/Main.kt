import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import operators.crossover.PMX
import operators.mutation.BitFlipMutation
import operators.selection.Roulette
import javax.swing.JFrame

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val results = GeneticAlgorithm(
            maxGenerationNumber = 50,
            populationSize = 5,
            crossOverAmount = 2,
            selectionMethod = Roulette(),
            crossoverMethod = PMX(),
            mutation = BitFlipMutation(0.1),
            shouldPrint = true
        ).execute()

        apresentaGrafico(results)
    }

    private fun apresentaGrafico(resultados: MutableMap<Int, Double>) {
        val jf = JFrame("Epoca X melhor distância")
        jf.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        val xy = XYSeries("Melhor distância")
        for (v in resultados!!.keys) {
            xy.add(v, resultados!![v])
        }
        val col = XYSeriesCollection(xy)
        val jfc = ChartFactory.createXYLineChart("Epoca X melhor distância", "Epoca", "Distância", col, PlotOrientation.VERTICAL,
            true, true, false)
        val cp = ChartPanel(jfc)
        jf.add(cp)
        jf.pack()
        jf.setLocationRelativeTo(null)
        jf.isVisible = true
    }
}