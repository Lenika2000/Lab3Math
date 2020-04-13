package main.java;

import java.io.*;


public class DataReceiver {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    void receiveData() {
        while (true) {
            System.out.println("Решение уравнения x^(3)-3.125x^(2)-3.5x+2.458\n"
                    + "Для выбора Метода половинного деления нажмите 1\n"
                    + "Для выбора Метода секущих нажмите 2\n"
                    + "Для выбора Метода простой итерации нажмите 3\n"
                    + "Для выхода напишите exit");

            try {
                switch (in.readLine()) {
                    case "1":
                        answer(HalfDivisionMethod.findSolution(getData(false)));
                        return;
                    case "2":
                        answer(SecantMethod.findSolution(getData(false)));
                        return;
                    case "3":
                        SimpleIteration simpleIteration = new SimpleIteration(getData(true));
                        answer(simpleIteration.findSolution());
                        return;
                    case "exit":
                        break;
                    default:
                        System.out.println("Произошла ошибка, повторите ввод еще раз");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Неверный ввод!");
            } catch (MethodException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    void answer(String answer) {
        while (true) {
            System.out.println("Для вывода ответа в консоль нажмите 1\n"
                    + "Для вывода в файл нажмите 2");
            try {
                switch (in.readLine()) {
                    case "1":
                        System.out.println(answer);
                        return;
                    case "2":
                        writeToFile(answer);
                        System.out.println("Ответ находится в файле answer.txt");
                        return;
                    default:
                        System.out.println("Произошла ошибка, повторите ввод еще раз");
                }
            } catch (IOException e) {
                System.out.println("Неверный ввод!");
            }
        }
    }


    void writeToFile(String s) {
        try (FileWriter writer = new FileWriter("answer.txt", false)) {
            writer.write(s);
            writer.flush();
        } catch (IOException ex) {
            System.out.println("Произошла ошибка записи в файл");
        }
    }


    double[] getData(boolean isInitialApproximation) throws IOException, MethodException {
        while (true) {
            System.out.println("Для ввода данных из консоли нажмите 1\n"
                    + "Чтобы загрузить данные из файла нажмите 2");
            System.out.println("Содержимое файла:\n"
                    + "Параметры указывать через точку-запятую\n"
                    + "Для методов половинного деления и метод секущей в виде: границы интервала(через точку-запятую);погрешность вычисления\n"
                    + "Для метода простой итерации в виде:начальное приближение;погрешность вычисления");
            switch (in.readLine()) {
                case "1":
                    return readFromConsole(isInitialApproximation);
                case "2":
                    return readFromFile(isInitialApproximation);
                default:
                    System.out.println("Произошла ошибка, повторите ввод еще раз");
            }
        }
    }


    double getParameterFromConsole(String text) {
        while (true) {
            System.out.println(text);
            try {
                String number = in.readLine().replace(",", ".");
                return Double.parseDouble(number);
            } catch (Exception e) {
                System.out.println("Введите требуемое число.");
            }
        }
    }


    double[] readFromConsole(boolean isInitialApproximation) throws MethodException {
        double[] parameters = new double[3];
        if (isInitialApproximation) {
            parameters[0] = getParameterFromConsole("Введите начальное приближение");
            parameters[1] = getParameterFromConsole("Введите погрешность вычисления");
            if (parameters[1]<0) throw new MethodException("Значение погрешности вычисления не может быть меньше 0");
        } else {
            parameters[0] = getParameterFromConsole("Введите правую границу интервала");
            parameters[1] = getParameterFromConsole("Введите левую границу интервала");
            if (parameters[0] < parameters[1]) {
                double k = parameters[0];
                parameters[0] = parameters[1];
                parameters[1] = k;
                System.out.println("Правая граница меньше левой. Данные скорректированы.");
            }
            parameters[2] = getParameterFromConsole("Введите погрешность вычисления");
            if (parameters[2]<0) throw new MethodException("Значение погрешности вычисления не может быть меньше 0");
        }
        return parameters;
    }

    double[] readFromFile(boolean isInitialApproximation) throws IOException, MethodException {
        while (true) {
            try {
                System.out.println("Введите полный путь к файлу");
                String path = in.readLine();
                File file = new File(path);
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);

                String line = reader.readLine();
                if (line != null) {
                    String[] param = line.replace(",", ".").split(";");
                    if (param.length != 2 && isInitialApproximation) {
                        throw new MethodException("Количество парметров в файле не совпадает с требуемым количеством для выбранного метода");
                    }

                    if (param.length != 3 && !isInitialApproximation) {
                        throw new MethodException("Количество парметров в файле не совпадает с требуемым количеством для выбранного метода");
                    }
                    double[] doubleParam = new double[param.length];

                    for (int i = 0; i < param.length; i++) {
                        doubleParam[i] = Double.parseDouble(param[i]);
                    }
                    if (doubleParam[0] < doubleParam[1]) {
                        double k = doubleParam[0];
                        doubleParam[0] = doubleParam[1];
                        doubleParam[1] = k;
                    }

                    return doubleParam;
                } else {
                    System.out.println("Файл пуст!");
                }

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Данные некорректны.Проверьте содержимое файла!");
            }

        }
    }
}
