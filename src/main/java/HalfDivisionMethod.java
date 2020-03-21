package main.java;

import org.jfree.ui.RefineryUtilities;

public class HalfDivisionMethod {
    private static final double x1 = -1.25;
    private static final double x2 = 0.508;
    private static final double x3 = 3.865;

    static String findSolution(double[] parameters) throws MethodException {
        int n = 0;
        double b = parameters[0];
        double a = parameters[1];
        double accuracy = parameters[2];
        double x;
        double f;
        boolean condition = (x1 > b || x1 < a) && (x2 > b || x2 < a) && (x3 > b || x3 < a);
        if (condition) {
            throw new MethodException(String.format("На введенном интервале (%f,%f) отсутствует корень уравнения.", a, b));
        }

        do {
            x = (b + a) / 2;
            f = (Math.pow(x, 3) - 3.125 * Math.pow(x, 2) - 3.5 * x + 2.458);
            if (f * (Math.pow(a, 3) - 3.125 * Math.pow(a, 2) - 3.5 * a + 2.458) > 0) {
                a = x;
            } else {
                b = x;
            }
            n++;
        } while (Math.abs(a - b) > accuracy);
        x = (b + a) / 2;
        f = (Math.pow(x, 3) - 3.125 * Math.pow(x, 2) - 3.5 * x + 2.458);

        final LineChart demo = new LineChart("Метод половинного деления", a, b);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом половинного деления\n " +
                "Х=%f, количество итераций n=%d, f(%f)=%f", x, n, x, f);


    }
}
