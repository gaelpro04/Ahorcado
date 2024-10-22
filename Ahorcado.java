import java.util.*;


public class Ahorcado {

    BancoPalabras banco;
    BancoPalabras bancoPalabrasUsadas;
    HashSet<Character> letrasUsadas = new HashSet<>();
    String fraseActual;
    ArrayList<LinkedHashMap<Integer, Character>> letrasModeladas;
    ArrayList<LinkedHashMap<Integer, Character>> letrasModeladasUsadas;
    int puntuacionMaxima;
    int cantidadJugadores;
    ArrayList<Jugador> jugadores = new ArrayList<>();


    public Ahorcado(int puntuacionMaxima, int cantidadJugadores)
    {
        if (cantidadJugadores > 1 && cantidadJugadores < 5) {
            this.puntuacionMaxima = puntuacionMaxima;
            this.cantidadJugadores = cantidadJugadores;
            banco = new BancoPalabras(false);
            bancoPalabrasUsadas = new BancoPalabras(true);
            jugadores = new ArrayList<>(cantidadJugadores);
            hacerJugadores(cantidadJugadores);
        }
    }

    private void hacerJugadores(int cantidadJugadores)
    {
        for (int i = 0; i < cantidadJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + (i+1)));
        }
    }

    public void determinarFrase()
    {
        int cantPalabras = 0;
        escogerFrase(banco);
        int contador = 0;

        for (Character letra : fraseActual.toCharArray()) {
            if (letra == ' ') {
                ++cantPalabras;
            }
        }

        ++cantPalabras;
        //BANDERA
        System.out.println("cantidad palabras " + cantPalabras);
        System.out.println("Palabra: " + fraseActual);

        letrasModeladas = new ArrayList<>(cantPalabras);
        letrasModeladasUsadas = new ArrayList<>(cantPalabras);

        for (int i = 0; i < cantPalabras; i++) {
            letrasModeladas.add(new LinkedHashMap<>());
            letrasModeladasUsadas.add(new LinkedHashMap<>());
        }
        cantPalabras = 0;

        for (Character letra : fraseActual.toCharArray()) {
            if (letra == ' ') {
                letrasModeladas.get(cantPalabras).put(contador, letra);
                letrasModeladasUsadas.get(cantPalabras).put(contador, ' ');
                ++cantPalabras;
            } else {
                letrasModeladas.get(cantPalabras).put(contador, letra);
                letrasModeladasUsadas.get(cantPalabras).put(contador, null);
            }
            ++contador;
        }

        //BANDERA 2
        System.out.println("contador: " + contador);
    }

    private void escogerFrase(BancoPalabras bancoPalabras)
    {
        Collections.shuffle(bancoPalabras.getBancoPalabras());
        fraseActual = bancoPalabras.getBancoPalabras().getFirst();
        bancoPalabrasUsadas.getBancoPalabras().add(bancoPalabras.getBancoPalabras().removeFirst());
    }

    public void imprimirFrase()
    {
        System.out.println("===PALABRA A ADIVINAR===\n");
        for (HashMap<Integer, Character> modelada : letrasModeladasUsadas) {
            for (Character letra : modelada.values()) {
                if (letra == null) {
                    System.out.print("_");
                } else {
                    System.out.print(letra);
                }
            }
        }
    }




    private String verificarLetra(char letra, String frase)
    {
        if (!letrasUsadas.contains(letra) && frase.contains(String.valueOf(letra))) {
            return "acerto";

        } else if (letrasUsadas.contains(letra) && frase.contains(String.valueOf(letra))) {
            return "acertado";

        } else if (!frase.contains(String.valueOf(letra))) {
            return "erroneo";

        }
        return null;
    }

    private void determinarPuntuacion(Jugador jugadorActual, char letra, String frase)
    {
        if (verificarLetra(letra, frase).equals("acerto")) {
            jugadorActual.acumularPuntuacion(3);
        } else if (verificarLetra(letra, frase).equals("acertado")) {
            jugadorActual.acumularPuntuacion(-3);
        } else {
            jugadorActual.acumularPuntuacion(-1);
        }
    }

    private void colocarLetra(char letra, String frase)
    {
        int contadorLetras = 0;
        int contadorPalabras = 0;

        if (verificarLetra(letra, frase).equals("acerto")) {
            letrasUsadas.add(letra);
            for (HashMap<Integer, Character> modelada : letrasModeladas) {
                for (Character letraModelada : modelada.values()) {
                    if (letraModelada.equals(' ')) {
                        ++contadorLetras;
                        ++contadorPalabras;
                        break;
                    } else if (letraModelada.equals(letra)) {
                        letrasModeladasUsadas.get(contadorPalabras).put(contadorLetras, letraModelada);
                    }
                    ++contadorLetras;
                }
            }
        }
    }

    private boolean finDelJuego()
    {
        return true;
    }

    public void jugar()
    {
        int turnoActual = 0;
        boolean juegoTerminado = false;

        while (!juegoTerminado) {
            Jugador jugadorActual = jugadores.get(turnoActual);

            System.out.println("===Turno de " + jugadorActual.getNombre() + "===");

            if (turnoActual == 3) {
                juegoTerminado = true;
            }




            turnoActual = (turnoActual + 1) % jugadores.size();
        }
    }
}
