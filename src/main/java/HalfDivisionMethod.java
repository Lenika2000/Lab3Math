package main.java;

import org.jfree.ui.RefineryUtilities;

import java.util.function.Function;

public class HalfDivisionMethod {

    static String findSolution(double[] parameters) throws MethodException {
        int n = 0;
        double b = parameters[0];
        double a = parameters[1];
        double accuracy = parameters[2];
        double x;
        Function<Double, Double> fun = ((Double x0) -> (Math.pow(x0, 3) - 3.125 * Math.pow(x0, 2) - 3.5 * x0 + 2.458));

        if (fun.apply(a)*fun.apply(b)>0){
            throw new MethodException(String.format("На концах отрезка [%f,%f] функция имеет одинаковые знаки. Значит на отрезке либо нет " +
                    "корней, либо их четное количество.Пожалуйста, уточните интервал\n",a,b));
        }

        do {
            x = (b + a) / 2;
            if (fun.apply(x) * fun.apply(a) > 0) {
                a = x;
            } else {
                b = x;
            }
            n++;
        } while (Math.abs(a - b) > accuracy);
        x = (b + a) / 2;

        final LineChart demo = new LineChart("Метод половинного деления", a, b);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом половинного деления\n " +
                "Х=%f, количество итераций n=%d, критерий окончания итерационного процесса |a-b|=%f f(%f)=%f", x, n, Math.abs(a - b), x, fun.apply(x));
    }
}
