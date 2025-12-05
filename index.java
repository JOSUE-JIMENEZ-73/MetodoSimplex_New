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
        for (int i = 0; i < numVariables; i++) {
            tablaSimplex[numRestricciones][i] = 0;
        }
        tablaSimplex[numRestricciones][numColumnas - 1] = 0; // Termino independiente de la funcion objetivo

        // Algoritmo simplex
       
    }

    // Metodo para minimizar
    public static void minimizar(int numVariables, int numRestricciones) {

    }

    //metodo para encontrar la columna pivote
    public static int ColumnaPivote(double[][]tablaSimplex,int numRestricciones, int numColumnas){//metodo para encontrar la columna pivote
        int columnaPivote=-1;//indice de la columna pivote
        double valorMinimo=0;

        for(int j=0;j<numColumnas-1;j++){//recorre las columnas menos la de terminos independientes
            if(tablaSimplex[numRestricciones][j]<valorMinimo){//si el valor es menor al minimo actual
                valorMinimo=tablaSimplex[numRestricciones][j];//actualiza el valor minimo
                columnaPivote=j;//actualiza el indice de la columna pivote
            }
        }
        return columnaPivote;
    }
}
