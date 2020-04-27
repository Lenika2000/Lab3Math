package main.java;
import org.jfree.ui.RefineryUtilities;
import java.util.function.Function;

public class SecantMethod {
    static String findSolution(double[] parameters) throws MethodException {
        double x0;
        double b = parameters[0];
        double a = parameters[1];
        double accuracy = parameters[2];
        int n=0;
        Function<Double, Double> fun = ((Double x) -> (Math.pow(x, 3) - 3.125 * Math.pow(x, 2) - 3.5 * x + 2.458));
        //условие применимости метода Ньютона
        if (fun.apply(a)*fun.apply(b)>0){
            throw new MethodException(String.format("На концах отрезка [%f,%f] функция имеет одинаковые знаки. Значит на отрезке либо нет " +
                    "корней, либо их четное количество.Пожалуйста, уточните интервал\n",a,b));
        }

        if (fun.apply(a)*(6*a-6.25)>0) { //выбор х0
            x0=a;
        }else {
            x0=b;
        }

        double k;
        double x = x0+accuracy*2; //x1 выбирается рядом с х0 самостоятельно
        while (Math.abs(x-x0)>accuracy) {
            k=x;
            x = x - (x-x0)*fun.apply(x)/(fun.apply(x)-fun.apply(x0));
            x0=k;
            n++;
        }

        final LineChart demo = new LineChart("Метод секущих", a,b);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом секущих\n " +
                "Х=%f, количество итераций n=%d, , критерий окончания итерационного процесса |a-b|=%f, f(%f)=%f",x,n,Math.abs(x-x0),x,fun.apply(x));
    }
}
