import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Dataset {
    public static XYDataset createDataset(double a, double b) {
        final XYSeries series1 = new XYSeries("x^(3)-3.125x^(2)-3.5x+2.458");

        if (a > b) {
            double k = b;
            b = a;
            a = k;
        }
        a -= 1;
        b += 1;
        for (double i = a; i < b; i += Math.abs(b - a) / 100) {
            series1.add(i, Math.pow(i, 3) - 3.125 * Math.pow(i, 2) - 3.5 * i + 2.458);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        return dataset;
    }

    public static XYDataset createDatasetX(double a, double b) {
        final XYSeries series1 = new XYSeries("y=0");
        if (a > b) {
            double k = b;
            b = a;
            a = k;
        }

        series1.add(a - 1, 0);
        series1.add(b + 1, 0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        return dataset;
    }


}