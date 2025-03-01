import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibretaDeNotas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HashMap<String, ArrayList<Double>> calificacionesEstudiantes = new HashMap<>();

        int cantidadAlumnos;
        int cantidadNotas;

        System.out.println("Ingrese la cantidad de alumnos:");
        cantidadAlumnos = validarEnteroPositivo(scanner);

        System.out.println("Ingrese la cantidad de notas por alumno:");
        cantidadNotas = validarEnteroPositivo(scanner);

        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.println("Ingrese el nombre del alumno " + (i + 1) + ":");
            String nombreAlumno = scanner.next();

            ArrayList<Double> notasAlumno = new ArrayList<>();

            for (int j = 0; j < cantidadNotas; j++) {
                System.out.println("Ingrese la nota " + (j + 1) + " para " + nombreAlumno + ":");
                double nota = validarNota(scanner);
                notasAlumno.add(nota);
            }
            calificacionesEstudiantes.put(nombreAlumno, notasAlumno);
        }

        HashMap<String, Double> promediosEstudiantes = new HashMap<>();
        HashMap<String, Double> notasMaximas = new HashMap<>();
        HashMap<String, Double> notasMinimas = new HashMap<>();

        double sumaTotal = 0;
        int cantidadNotasTotal = 0;

        // Recorrer el HashMap para calcular estadísticas
        for (Map.Entry<String, ArrayList<Double>> entrada : calificacionesEstudiantes.entrySet()) {
            String nombreAlumno = entrada.getKey();
            ArrayList<Double> notasAlumno = entrada.getValue();

            double suma = 0;
            double max = notasAlumno.get(0);
            double min = notasAlumno.get(0);

            for (double nota : notasAlumno) {
                suma += nota;
                if (nota > max) {
                    max = nota;
                }
                if (nota < min) {
                    min = nota;
                }

                sumaTotal += nota;
                cantidadNotasTotal++;
            }

            double promedio = suma / notasAlumno.size();

            promediosEstudiantes.put(nombreAlumno, promedio);
            notasMaximas.put(nombreAlumno, max);
            notasMinimas.put(nombreAlumno, min);

            System.out.println("\nEstadísticas para " + nombreAlumno + ":");
            System.out.println("Promedio: " + promedio);
            System.out.println("Nota máxima: " + max);
            System.out.println("Nota mínima: " + min);
        }


        double promedioGeneral = sumaTotal / cantidadNotasTotal;
        System.out.println("\nPromedio general del curso: " + promedioGeneral);

        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:

                    System.out.println("\nPromedio de notas por estudiante:");
                    for (Map.Entry<String, Double> entrada : promediosEstudiantes.entrySet()) {
                        System.out.println(entrada.getKey() + ": " + entrada.getValue());
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el nombre del estudiante:");
                    String nombreEstudiante = scanner.next();

                    if (calificacionesEstudiantes.containsKey(nombreEstudiante)) {
                        System.out.println("Ingrese el número de la nota (1-" + calificacionesEstudiantes.get(nombreEstudiante).size() + "):");
                        int numeroNota = scanner.nextInt();

                        if (numeroNota >= 1 && numeroNota <= calificacionesEstudiantes.get(nombreEstudiante).size()) {
                            double nota = calificacionesEstudiantes.get(nombreEstudiante).get(numeroNota - 1);

                            if (nota >= 4.0) {
                                System.out.println("La nota " + nota + " es APROBATORIA");
                            } else {
                                System.out.println("La nota " + nota + " es REPROBATORIA");
                            }
                        } else {
                            System.out.println("Número de nota inválido");
                        }
                    } else {
                        System.out.println("Estudiante no encontrado");
                    }
                    break;

                case 3:
                    // Mostrar si la nota está por sobre o por debajo del promedio
                    System.out.println("Ingrese el nombre del estudiante:");
                    String nombreAlumno = scanner.next();

                    if (calificacionesEstudiantes.containsKey(nombreAlumno)) {
                        System.out.println("Ingrese el número de la nota (1-" + calificacionesEstudiantes.get(nombreAlumno).size() + "):");
                        int numeroNota = scanner.nextInt();

                        if (numeroNota >= 1 && numeroNota <= calificacionesEstudiantes.get(nombreAlumno).size()) {
                            double nota = calificacionesEstudiantes.get(nombreAlumno).get(numeroNota - 1);

                            if (nota > promedioGeneral) {
                                System.out.println("La nota " + nota + " está POR ENCIMA del promedio general del curso (" + promedioGeneral + ")");
                            } else if (nota < promedioGeneral) {
                                System.out.println("La nota " + nota + " está POR DEBAJO del promedio general del curso (" + promedioGeneral + ")");
                            } else {
                                System.out.println("La nota " + nota + " es IGUAL al promedio general del curso (" + promedioGeneral + ")");
                            }
                        } else {
                            System.out.println("Número de nota inválido");
                        }
                    } else {
                        System.out.println("Estudiante no encontrado");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n----- MENÚ DE OPCIONES -----");
        System.out.println("1. Mostrar el Promedio de Notas por Estudiante");
        System.out.println("2. Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante");
        System.out.println("3. Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante");
        System.out.println("0. Salir del Menú");
        System.out.print("Ingrese una opción: ");
    }

    public static int validarEnteroPositivo(Scanner scanner) {
        int numero;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Error: Debe ingresar un número entero positivo");
                scanner.next();
            }
            numero = scanner.nextInt();
            if (numero <= 0) {
                System.out.println("Error: Debe ingresar un número entero positivo");
            }
        } while (numero <= 0);
        return numero;
    }

    public static double validarNota(Scanner scanner) {
        double nota;
        do {
            while (!scanner.hasNextDouble()) {
                System.out.println("Error: Debe ingresar un número entre 1.0 y 7.0");
                scanner.next();
            }
            nota = scanner.nextDouble();
            if (nota < 1.0 || nota > 7.0) {
                System.out.println("Error: La nota debe estar entre 1.0 y 7.0");
            }
        } while (nota < 1.0 || nota > 7.0);
        return nota;
    }
}
