import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import java.awt.*;


public class LineChart extends ApplicationFrame {
    private static final long serialVersionUID = 1L;
    private double a;
    private double b;

    public LineChart(final String title, double a, double b) {
        super(title);
        this.a = a;
        this.b = b;
        start();
    }

    private void start() {
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(560, 480));
        setContentPane(chartPanel);
    }


    private JFreeChart createChart() {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "График x^(3)-3.125x^(2)-3.5x+2.458",
                null,                        // x axis label
                null,                        // y axis label
                null,                        // data
                PlotOrientation.VERTICAL,
                true,                        // include legend
                false,                       // tooltips
                false                        // urls
        );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(159, 190, 237));

        plot.setDomainGridlinePaint(Color.gray);//сетка
        plot.setRangeGridlinePaint(Color.gray);

        // Определение отступа меток делений
        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        // Скрытие осевых линий и меток делений
        ValueAxis axis = plot.getDomainAxis();
        axis.setAxisLineVisible(false);    // осевая линия

        // Настройка NumberAxis
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Настройка XYSplineRenderer
        // Precision: the number of line segments between 2 points [default: 5]
        XYSplineRenderer r = new XYSplineRenderer(); //характеристики графика
        r.setPrecision(8);
        r.setSeriesShapesVisible(0, false);
        r.setSeriesPaint(0, Color.blue);

        // Набор данных
        XYDataset dataset = Dataset.createDataset(a, b);
        XYDataset X = Dataset.createDatasetX(a, b);

        XYSplineRenderer r1 = new XYSplineRenderer();//характеристики оси абсцисс
        r.setSeriesPaint(1, Color.black);
        r.setSeriesShapesVisible(1, false);

        plot.setDataset(0, dataset);
        plot.setDataset(1, X);

        // Подключение Spline Renderer к набору данных
        plot.setRenderer(0, r);
        plot.setRenderer(1, r1);

        return chart;
    }

}