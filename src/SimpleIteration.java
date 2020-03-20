import org.jfree.ui.RefineryUtilities;

import java.util.function.Function;

public class SimpleIteration {
    public String findSolution(double[] parameters) throws MethodException {

        double b = parameters[0]; // не использую
        double a = parameters[1];
        double approximation = parameters[2];
        double accuracy = parameters[3];
        double x0=approximation;
        double φ1=(6.25*x0+3.5)/3/Math.cbrt(Math.pow(3.125*x0*x0+3.5*x0-2.458,2));
        double φ2=(3*x0*x0-6.25*x0)/3.5;

        if (Math.abs(φ1)<1){
            return getAnswer(x0,accuracy,approximation,(Double x) ->  Math.cbrt(3.125*x*x+3.5*x-2.458));
        } else if (Math.abs(φ2)<1){
            return getAnswer(x0,accuracy,approximation,(Double x) ->  (x*x*x-3.125*x*x+2.458)/3.5);
        } else {
            throw new MethodException("Начальное приближение выбрано неверно, не выполняется достаточное условие сходимости метода");
        }

    }

    private String getAnswer(double x0, double accuracy, double appr, Function<Double, Double> fun){
        double x1;
        int n=0;
        while (true){
            x1 = fun.apply(x0);
            n++;
            if (Math.abs(x1-x0)<=accuracy) {
                break;
            }
            x0 =x1;
        }
        double f = (Math.pow(x1, 3) - 3.125 * Math.pow(x1, 2) - 3.5 * x1 + 2.458);

        final LineChart demo = new LineChart("Метод секущих", appr, x1);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом простой итерации\n " +
                "Х=%f, количество итераций n=%d, f(%f)=%f", x1, n, x1, f);

    }

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