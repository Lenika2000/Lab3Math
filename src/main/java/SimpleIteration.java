package main.java;

import org.jfree.ui.RefineryUtilities;

import java.util.function.Function;
import static java.lang.Math.*;

public class SimpleIteration {

    private double appr;
    private double accuracy;
    private double λ;

    SimpleIteration(double[] parameters) {
        this.appr = parameters[0]; //начальное приближение
        this.accuracy = parameters[1];

    }

    public String findSolution() throws MethodException {

        double φ1 = (6.25 * appr + 3.5) / (3 * Math.cbrt(Math.pow(3.125 * appr * appr + 3.5 * appr - 2.458, 2)));
        double φ2 = (3 * appr * appr - 6.25 * appr) / 3.5;
        double φ3 = (3 * appr * appr - 3.5) / (3.125 * 2 * Math.sqrt(appr * appr * appr - 3.5 * appr + 2.458)); // делала в аэропорту , проверь!!!
        double fappr = 3 * Math.pow(appr, 2) - 6.25 * appr - 3.5;

        λ = -1 / fappr;

        double φλ = ( appr + λ * (Math.pow(appr, 3) - 3.125 * Math.pow(appr, 2) - 3.5 * appr + 2.458));

        φ1 = Double.isNaN(φ1) ? Double.MAX_VALUE : φ1;
        φ2 = Double.isNaN(φ2) ? Double.MAX_VALUE : φ2;
        φ3 = Double.isNaN(φ3) ? Double.MAX_VALUE : φ3;
        φλ = Double.isNaN(φλ) ? Double.MAX_VALUE : φλ;

        double min = Math.min(Math.min(Math.abs(φ1), Math.abs(φ2)), Math.min(Math.abs(φλ),Math.abs(φ3)));

        if (min < 1)
            return getAnswer(((Double x) -> x + λ * (Math.pow(x, 3) - 3.125 * Math.pow(x, 2) - 3.5 * x + 2.458)));
        else
            throw new MethodException("Начальное приближение выбрано неверно, не выполняется достаточное условие сходимости метода");
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
            if (Math.abs(x1-x0) <= accuracy) {
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

//    private String lymbda(Function<Double, Double> fun){
//        double fappr = 3*Math.pow(appr,2)-6.25*appr-3.5;
//        double λ=-1/fappr;
//
//    }

}

//    static String findSolution(double[] parameters) throws MethodException { Используем границы интервала
//
//        double b = parameters[0];
//        double a = parameters[1];
//        double approximation = parameters[2];
//        double accuracy = parameters[3];
//        int n=0;
//        double fa = 3*Math.pow(a,2)-6.25*a-3.5; //значение производной от концов отрезка
//        double fb = 3*Math.pow(b,2)-6.25*b-3.5;
//        double λ=0;
//
//        double x0=a;
//        double x1;
//
//        if (fa>fb) {
//            λ=-1/fa; //определяем множитель лямбда
//        }else  {
//            λ=-1/fb;
//        }
//
//        if (Math.abs(3*λ*Math.pow(a,2)-6.25*λ*a+1-3.5*λ)>=1){
//            throw new MethodException("Интервал задан неверно, не выполняется достаточное условие сходимости метода");
//        }
//
//        x1= x0+λ*(Math.pow(x0, 3) - 3.125 * Math.pow(x0, 2) - 3.5 * x0 + 2.458);
//        x0=x1;
//        while (true){
//
//            double k =x1;
//            x1 = x0+λ*(Math.pow(x0, 3) - 3.125 * Math.pow(x0, 2) - 3.5 * x0 + 2.458);
//            x0 =k;
//            n++;
//            if (Math.abs(x1-x0)<=accuracy) {
//                break;
//            }
//        }
//        double f = (Math.pow(x1, 3) - 3.125 * Math.pow(x1, 2) - 3.5 * x1 + 2.458);
//        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом простой итерации\n " +
//                "Х=%f, количество итераций n=%d, f(%f)=%f", x1, n, x1, f);
//
//    }
