package main.java;

import org.jfree.ui.RefineryUtilities;

import java.util.function.Function;

public class SimpleIteration {

    private double appr;
    private double accuracy;
    private double λ;

    SimpleIteration(double[] parameters) {
        this.appr = parameters[0]; //начальное приближение
        this.accuracy = parameters[1];

    }

    public String findSolution() throws MethodException {


        double fappr = 3*Math.pow(appr,2)-6.25*appr-3.5;
        λ=-1/fappr;

        if (Math.abs(3*λ*Math.pow(appr,2)-6.25*λ*appr+1-3.5*λ)<1 && (appr>0 ||appr<-1)) {
            return getAnswer(((Double x) -> x+λ*(Math.pow(x, 3) - 3.125 * Math.pow(x, 2) - 3.5 * x + 2.458) ));//достаточное условие
            //сходимости выполнено и начальное приближение не лежит в интервале от -1 до 0
        } else {
            double φ1 = (6.25 * appr + 3.5) / 3 / Math.cbrt(Math.pow(3.125 * appr * appr + 3.5 * appr - 2.458, 2));
            if (Math.abs(φ1) < 1) {
                return getAnswer((Double x) -> Math.cbrt(3.125 * x * x + 3.5 * x - 2.458));
            } else {
                throw new MethodException("Начальное приближение выбрано неверно, не выполняется достаточное условие сходимости метода");
            }
        }

    }

    private String getAnswer(Function<Double, Double> fun) {
        double x0 = appr;
        double x1;
        int n = 0;
        double f;
        while (true) {
            x1 = fun.apply(x0);
            n++;
            f = (Math.pow(x1, 3) - 3.125 * Math.pow(x1, 2) - 3.5 * x1 + 2.458);
            if (Math.abs(f) <= accuracy) { //для более точного ответа
                break;
            }
            x0 = x1;
        }
        final LineChart demo = new LineChart("Метод простых итераций", appr, x1);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом простой итерации\n " +
                "Х=%f, количество итераций n=%d, f(%f)=%f", x1, n, x1, f);

    }
}
