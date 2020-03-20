import java.io.*;


public class DataReceiver {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    void receiveData() {
        boolean success = false;
        while (!success) {
            System.out.println("Решение уравнения x^(3)-3.125x^(2)-3.5x+2.458\n"
                    + "Для выбора Метода половинного деления нажмите 1\n"
                    + "Для выбора Метода секущих нажмите 2\n"
                    + "Для выбора Метода простой итерации нажмите 3\n"
                    + "Для выхода напишите exit");

            try {
                switch (in.readLine()) {
                    case "1":
                        success = true;
                        answer(HalfDivisionMethod.findSolution(getData(false)));
                        break;
                    case "2":
                        success = true;
                        answer(SecantMethod.findSolution(getData(true)));
                        break;
                    case "3":
                        success = true;
                        SimpleIteration simpleIteration = new SimpleIteration();
                        answer(simpleIteration.findSolution(getData(true)));
                        break;
                    case "exit":
                        success = true;
                        break;
                    default:
                        System.out.println("Произошла ошибка, повторите ввод еще раз");
                        success = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Неверный ввод!");
                success = false;
            } catch (MethodException e) {
                System.out.println(e.getMessage());
                success = false;
            }
        }

    }


    void answer(String answer) {
        boolean success = false;
        while (!success) {
            System.out.println("Для вывода ответа в консоль нажмите 1\n"
                    + "Для вывода в файл нажмите 2");
            try {
                switch (in.readLine()) {
                    case "1":
                        success = true;
                        System.out.println(answer);
                        break;
                    case "2":
                        success = true;
                        writeToFile(answer);
                        System.out.println("Ответ находится в файле answer.txt");
                        break;
                    default:
                        System.out.println("Произошла ошибка, повторите ввод еще раз");
                        success = false;
                }
            } catch (IOException e) {
                System.out.println("Неверный ввод!");
                success = false;
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


    double[] getData(boolean isInitialApproximation) throws IOException {
        boolean success = false;
        while (!success) {
            System.out.println("Для ввода данных из консоли нажмите 1\n"
                    + "Чтобы загрузить данные из файла нажмите 2");
            System.out.println("Содержимое файла:\n"
                    + "Параметры указывать через точку-запятую\n"
                    + "Для методов половинного деленияи и простой итерации в виде: границы интервала(через точку-запятую;погрешность вычисления\n"
                    + "Для метода секущих в виде:границы интервала(через точку-запятую);начальное приближение;погрешность вычисления");
            switch (in.readLine()) {
                case "1":
                    return readFromConsole(isInitialApproximation);
                case "2":
                    return readFromFile(isInitialApproximation);
                default:
                    System.out.println("Произошла ошибка, повторите ввод еще раз");
                    success = false;
            }
        }

        return new double[2];
    }


    double getParameterFromConsole(String text) {
        boolean exit = false;
        while (!exit) {
            System.out.println(text);
            try {
                String number = in.readLine().replace(",", ".");
                return Double.parseDouble(number);
            } catch (Exception e) {
                exit = false;
                System.out.println("Введите требуемое число.");
            }
        }
        return 0;
    }


    double[] readFromConsole(boolean isInitialApproximation) throws IOException {
        double[] parameters = new double[4];
        parameters[0] = getParameterFromConsole("Введите правую границу интервала");
        parameters[1] = getParameterFromConsole("Введите левую границу интервала");
        if (parameters[0] < parameters[1]) {
            double k = parameters[0];
            parameters[0] = parameters[1];
            parameters[1] = k;
            System.out.println("Правая граница меньше левой. Данные скорректированы.");
        }
        if (isInitialApproximation) {
            parameters[2] = getParameterFromConsole("Введите начальное приближение");
            parameters[3] = getParameterFromConsole("Введите погрешность вычисления");
        } else {
            parameters[2] = getParameterFromConsole("Введите погрешность вычисления");
        }
        return parameters;
    }

    double[] readFromFile(boolean isInitialApproximation) throws IOException {
        try {
            //делать проверку на количество параметров в файле

            System.out.println("Введите полный путь к файлу");
            String path = in.readLine();
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            if (line != null) {
                String[] param = line.split(";");
//                if (param.length!=4 && !isInitialApproximation) {
//                    throw new ArrayIndexOutOfBoundsException();
//                }
                double[] doubleParam = new double[param.length];

                for (int i = 0; i < param.length; i++) {
                    doubleParam[i] = Double.parseDouble(param[i]);
                }
                if (doubleParam[0] < doubleParam[1]) {
                    double k = doubleParam[0];
                    doubleParam[0] = doubleParam[1];
                    doubleParam[1] = k;
                    System.out.println("Правая граница меньше левой. Данные скорректированы.");
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
        return new double[2];
    }
}