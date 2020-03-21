import org.jfree.ui.RefineryUtilities;

public class SecantMethod {
    private static final double x1 = -1.25;
    private static final double x2 = 0.508;
    private static final double x3 = 3.865;
    static String findSolution(double[] parameters) throws MethodException {
        double x0;
        double b = parameters[0];
        double a = parameters[1];
        double accuracy = parameters[2];
        int n=0;

        boolean condition = (x1 > b || x1 < a) && (x2 > b || x2 < a) && (x3 > b || x3 < a);
        if (condition) {
            throw new MethodException(String.format("На введенном интервале (%f,%f) отсутствует корень уравнения.", a, b));
        }

        if ((Math.pow(a,3)-3.125*Math.pow(a,2)-3.5*a+2.458)*(Math.pow(b,3)-3.125*Math.pow(b,2)-3.5*b+2.458)>0){
            throw new MethodException(String.format("На концах отрезка [%f,%f] функция имеет одинаковые знаки",a,b));
        }

        if ((Math.pow(a,3)-3.125*Math.pow(a,2)-3.5*a+2.458)*(6*a-6.25)>0) {
            x0=a;
        }else {
            x0=b;
        }

        double k;
        double x = x0+accuracy*2; //чтобы не было проблем, если в качестве приближения выбираем один из концов отрезка
        double f;
        while (Math.abs(x-x0)>accuracy) {
            k=x;
            f=Math.pow(x,3)-3.125*Math.pow(x,2)-3.5*x+2.458;
            x = x - (x-x0)*f/(f-(Math.pow(x0,3)-3.125*Math.pow(x0,2)-3.5*x0+2.458));
            x0=k;
            n++;
        }
        f=Math.pow(x,3)-3.125*Math.pow(x,2)-3.5*x+2.458;

        final LineChart demo = new LineChart("Метод секущих", a,b);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

        return String.format("Выполнено решение уравнения x^(3)-3.125x^(2)-3.5x+2.458 методом секущих\n " +
                "Х=%f, количество итераций n=%d, f(%f)=%f",x,n,x,f);
    }
}
