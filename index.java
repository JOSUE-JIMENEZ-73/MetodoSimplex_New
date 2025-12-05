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
                    System.out.println("Ingrese el numero de variables: ");
                    int numVariables = sc.nextInt();
                    System.out.println("Ingrese el numero de restricciones: ");
                    int numRestricciones = sc.nextInt();

                    // Matriz de coeficientes de la funcion objetivo
                    double[] coeficientesObjetivo = new double[numVariables];
                    System.out.println("Ingresa los coeficientes de la funcion objetivo: ");
                    for (int i = 0; i < numVariables; i++) {// Ciclo para llenar los coeficientes de la funcion objetivo
                        System.out.print("Coeficiente " + (i + 1) + ": ");
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

                    }

                    maximizar(numVariables, numRestricciones);
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
    public static void maximizar(int numVariables, int numRestricciones) {

    }

    // Metodo para minimizar
    public static void minimizar(int numVariables, int numRestricciones) {

    }

}
