import java.util.Scanner;

//Metodo simplex 
public class index {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n\t========METODO SIMPLEX========");
            System.out.println(" \n========MENU========");
            System.out.println("1.-Maximizar");
            System.out.println("2.-Minimizar");
            System.out.println("3.-Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: {
                    System.out.print("Ingrese el numero de variables: ");
                    int numVariables = sc.nextInt();
                    System.out.print("Ingrese el numero de restricciones: ");
                    int numRestricciones = sc.nextInt();
                    System.out.println(" ");

                    // Matriz de coeficientes de la funcion objetivo
                    double[] coeficientesObjetivo = new double[numVariables];
                    System.out.println("Ingresa los coeficientes de la funcion objetivo: ");
                    for (int i = 0; i < numVariables; i++) {// Ciclo para llenar los coeficientes de la funcion objetivo
                        System.out.print("Coeficiente X" + (i + 1) + ": ");
                        coeficientesObjetivo[i] = sc.nextDouble();
                    }

                    // Matriz de coeficientes de las restricciones
                    double[][] coeficientesRestricciones = new double[numRestricciones][numVariables];
                    // Almacenara los terminos independientes de las restricciones
                    double[] terminosIndependientes = new double[numRestricciones];

                    System.out.println("Ingresa los coeficientes de las restricciones (Cx1<= b): ");

                    // Ciclo para llenar los coeficientes de las restricciones
                    for (int i = 0; i < numRestricciones; i++) {
                        System.out.println("Restriccion " + (i + 1) + ": ");
                        for (int j = 0; j < numVariables; j++) {
                            System.out.print("   Coeficiente X" + (j + 1) + ": ");
                            coeficientesRestricciones[i][j] = sc.nextDouble();
                        }
                        System.out.print("   Termino independiente b: ");
                        terminosIndependientes[i] = sc.nextDouble();
                    }

                    maximizar(numVariables, numRestricciones, coeficientesObjetivo, coeficientesRestricciones,
                            terminosIndependientes);
                    break;
                }

                case 2: {
                    System.out.print("Ingrese el numero de variables: ");
                    int numVariables = sc.nextInt();
                    System.out.print("Ingrese el numero de restricciones: ");
                    int numRestricciones = sc.nextInt();
                    System.out.println(" ");
                    double[] coeficientesObjetivo = new double[numVariables];

                    System.out.println("\nIngrese los coeficientes de la funcion objetivo: ");
                    for (int i = 0; i < numVariables; i++) {
                        System.out.print("Coeficiente X" + (i + 1) + ": ");
                        coeficientesObjetivo[i] = sc.nextDouble();
                    }

                    double[][] coeficientesRestricciones = new double[numRestricciones][numVariables];
                    double[] terminosIndependientes = new double[numRestricciones];

                    System.out.println("\nIngrese los coeficientes de las restricciones (AX >= b):");
                    for (int i = 0; i < numRestricciones; i++) {
                        System.out.println("\nRestriccion " + (i + 1) + ":");

                        for (int j = 0; j < numVariables; j++) {
                            System.out.print("  Coeficiente X" + (j + 1) + ": ");
                            coeficientesRestricciones[i][j] = sc.nextDouble();
                        }

                        System.out.print("  Valor b" + (i + 1) + ": ");
                        terminosIndependientes[i] = sc.nextDouble();
                    }

                    minimizar(numVariables, numRestricciones, coeficientesObjetivo, coeficientesRestricciones,
                            terminosIndependientes);

                    break;
                }
                case 3: {
                    System.out.println("Saliendo del programa...");
                    break;
                }
                default:
                    System.out.println("Opcion no valida...");
                    break;
            }
        } while (opcion != 3);
        sc.close();

    }

    // Metodo para maximizar
    public static void maximizar(int numVariables, int numRestricciones, double[] coeficientesObjetivo,
            double[][] coeficientesRestricciones, double[] terminosIndependientes) {
        // Calculo del numero de columnas y filas de la tabla simplex
        int numColumnas = numVariables + numRestricciones + 1; // +1 para la columna de los terminos independientes
        int numFilas = numRestricciones + 1; // +1 para la fila de la funcion objetivo
        double[][] tablaSimplex = new double[numFilas][numColumnas];// Tabla simplex

        // Llenado de la tabla simplex
        for (int i = 0; i < numRestricciones; i++) {
            for (int j = 0; j < numVariables; j++) {
                tablaSimplex[i][j] = coeficientesRestricciones[i][j];// Coeficientes de las restricciones
            }
            tablaSimplex[i][numVariables + i] = 1; // Variables de holgura
            tablaSimplex[i][numColumnas - 1] = terminosIndependientes[i]; // Terminos independientes
        }

        // Llenado de la fila de la funcion objetivo
        for (int j = 0; j < numVariables; j++) {
            tablaSimplex[numFilas - 1][j] = -coeficientesObjetivo[j]; // Coeficientes de la funcion objetivo
        }
        // varables de holgura que inician en 0
        for (int i = numVariables; i < numColumnas - 1; i++) {
            tablaSimplex[numRestricciones][i] = 0;
        }
        tablaSimplex[numRestricciones][numColumnas - 1] = 0; // Termino independiente de la funcion objetivo

        // Algoritmo simplex
        while (true) {
            int columnaPivote = ColumnaPivote(tablaSimplex, numRestricciones, numColumnas);
            if (columnaPivote == -1) {
                break; // Solucion optima encontrada
            }

            int filaPivote = FilaPivote(tablaSimplex, numRestricciones, numColumnas, columnaPivote);
            if (filaPivote == -1) {
                System.out.println("Solucion ilimitada");
                return;
            }
            pivoteo(tablaSimplex, filaPivote, columnaPivote);

        }

        // Extraccion de la solucion
        double[] solucion = new double[numVariables];// almacenar la solucion
        for (int i = 0; i < numVariables; i++) {// recorre las variables
            int filaBaica = ColumnaBasica(tablaSimplex, i, numRestricciones);// verifica si la columna es basica
            if (filaBaica != -1) {
                solucion[i] = tablaSimplex[filaBaica][numColumnas - 1];// asigna el valor de la variable
            }
        }

        double valorOptimo = tablaSimplex[numRestricciones][numColumnas - 1];// valor optimo de la funcion objetivo

        System.out.println("==================RESULTADOS=================");
        for (int i = 0; i < numVariables; i++) {
            System.out.println("Valor de X" + (i + 1) + ": " + solucion[i]);
        }
        System.out.println("Valor optimo de la funcion objetivo: " + valorOptimo);
    }

    // Metodo para minimizar
    public static void minimizar(int numVariables, int numRestricciones, double[] coeficientesObjetivo,
            double[][] coeficientesRestricciones, double[] terminosIndependientes) {
        // Convertir el problema de minimizacion a maximizacion
        for (int i = 0; i < coeficientesObjetivo.length; i++) {
            coeficientesObjetivo[i] = -coeficientesObjetivo[i];// Negar los coeficientes de la funcion objetivo
        }

        int numExceso = numRestricciones;// Numero de variables de exceso
        int numArtificiales = numRestricciones;// Numero de variables artificiales

        int totalVariablesExtra = numExceso + numArtificiales;
        int columnas = numVariables + totalVariablesExtra + 1;
        int filas = numRestricciones + 1;
        double[][] tablaSimplex = new double[filas][columnas];

        
        final double M = 1000000;

        int colExceso = numVariables;// Indice inicial de las variables de exceso
        int colArtificial = numVariables + numExceso;// Indice inicial de las variables artificiales

        for (int i = 0; i < numRestricciones; i++) {// Llenado de la tabla simplex
            // Copiar coeficientes de las variables originales
            for (int j = 0; j < numVariables; j++) {
                tablaSimplex[i][j] = coeficientesRestricciones[i][j];
            }

            tablaSimplex[i][colExceso + i] = -1;// Variable de exceso
            tablaSimplex[i][colArtificial + i] = 1;// Variable artificial

            tablaSimplex[i][columnas - 1] = terminosIndependientes[i];// Termino independiente
        }

        for (int j = 0; j < numVariables; j++) {// Llenado de la fila de la funcion objetivo
            tablaSimplex[numRestricciones][j] = -coeficientesObjetivo[j];
        }

        for (int j = 0; j < numExceso; j++) {// Variables de exceso que inician en 0
            tablaSimplex[numRestricciones][colExceso + j] = 0;
        }

        for (int j = 0; j < numArtificiales; j++) {// Variables artificiales con penalizacion M
            tablaSimplex[numRestricciones][colArtificial + j] = M;
        }

        tablaSimplex[numRestricciones][columnas - 1] = 0;// Termino independiente de la funcion objetivo

        for (int i = 0; i < numRestricciones; i++) {// Ajuste inicial por variables artificiales
            for (int j = 0; j < columnas; j++) {//
                tablaSimplex[numRestricciones][j] -= M * tablaSimplex[i][j];// Ajuste de la fila de la funcion objetivo
            }
        }

        while (true) {// Algoritmo simplex
            int columnaPivote = ColumnaPivote(tablaSimplex, numRestricciones, columnas);
            if (columnaPivote == -1)
                break;

            int filaPivote = FilaPivote(tablaSimplex, numRestricciones, columnas, columnaPivote);
            if (filaPivote == -1) {
                System.out.println("\nEl problema no tiene solucion acotada (ilimitado).");
                return;
            }

            pivoteo(tablaSimplex, filaPivote, columnaPivote);
        }

        for (int j = colArtificial; j < colArtificial + numArtificiales; j++) {// Verificacion de variables artificiales en la solucion
            int filaBasica = ColumnaBasica(tablaSimplex, j, numRestricciones);//
            if (filaBasica != -1 && Math.abs(tablaSimplex[filaBasica][columnas - 1]) > 1e-6) {// Si una variable artificial es basica y su valor no es cero
                System.out.println("\nEl problema no tiene solucion factible.");
                return;
            }
        }

        double[] solucion = new double[numVariables];// Extraccion de la solucion
        for (int j = 0; j < numVariables; j++) {//
            int filaBasica = ColumnaBasica(tablaSimplex, j, numRestricciones);// Verifica si la columna es basica
            if (filaBasica != -1) {// Si es basica, asigna el valor correspondiente
                solucion[j] = tablaSimplex[filaBasica][columnas - 1];// Valor de la variable
            }
        }

        double valorOptimo = -tablaSimplex[numRestricciones][columnas - 1];// Valor optimo de la funcion objetivo (negado)
        System.out.println("==================RESULTADOS=================");
        for (int i = 0; i < numVariables; i++) {// Imprime los resultados
            System.out.println("Valor de X" + (i + 1) + ": " + solucion[i]);
        }
        System.out.println("Valor optimo de la funcion objetivo: " + valorOptimo);
    }

    // metodo para encontrar la columna pivote
    public static int ColumnaPivote(double[][] tablaSimplex, int numRestricciones, int numColumnas) {
        int columnaPivote = -1;// indice de la columna pivote
        double valorMinimo = 0;

        for (int j = 0; j < numColumnas - 1; j++) {// recorre las columnas menos la de terminos independientes
            if (tablaSimplex[numRestricciones][j] < valorMinimo) {// si el valor es menor al minimo actual
                valorMinimo = tablaSimplex[numRestricciones][j];// actualiza el valor minimo
                columnaPivote = j;// actualiza el indice de la columna pivote
            }
        }
        return columnaPivote;
    }

    public static int FilaPivote(double[][] tablaSimplex, int numRestricciones, int columnas, int columnaPivote) {
        int filaPivote = -1;
        double razonMinima = Double.MAX_VALUE;

        for (int i = 0; i < numRestricciones; i++) {
            if (tablaSimplex[i][columnaPivote] > 0) {
                double razon = tablaSimplex[i][columnas - 1] / tablaSimplex[i][columnaPivote];
                if (razon < razonMinima) {
                    razonMinima = razon;
                    filaPivote = i;
                }
            }
        }
        return filaPivote;
    }

    // Realiza el pivoteo en la tabla simplex
    public static void pivoteo(double[][] tablaSimplex, int filaPivote, int columnaPivote) {
        double elementoPivote = tablaSimplex[filaPivote][columnaPivote];// elemento pivote

        for (int i = 0; i < tablaSimplex[0].length; i++) {
            tablaSimplex[filaPivote][i] /= elementoPivote;// normaliza la fila pivote
        }

        for (int i = 0; i < tablaSimplex.length; i++) {
            if (i != filaPivote) {
                double factor = tablaSimplex[i][columnaPivote];

                for (int j = 0; j < tablaSimplex[0].length; j++) {// recorre las columnas
                    tablaSimplex[i][j] -= factor * tablaSimplex[filaPivote][j];// actualiza las demas filas
                }
            }
        }

    }

    // verificar si una columna es basica
    public static int ColumnaBasica(double[][] tablaSimplex, int columna, int numRestricciones) {
        int filaConUno = -1;
        int contadorUnos = 0;

        for (int i = 0; i < numRestricciones; i++) {
            double valor = Math.abs(tablaSimplex[i][columna]);

            if (Math.abs(valor - 1.0) < 1e-10) {
                contadorUnos++;
                filaConUno = i;
            } else if (valor > 1e-10) {
                // Si hay un valor diferente de 0 o 1, no es columna basica
                return -1;
            }
        }

        // Solo es basica si hay exactamente un 1 y el resto son 0
        return (contadorUnos == 1) ? filaConUno : -1;
    }

}
