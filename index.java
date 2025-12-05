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
}
