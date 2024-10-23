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
        System.out.println("=================================");
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
        System.out.println("================================");
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
        for (LinkedHashMap<Integer, Character> modelada : letrasModeladasUsadas) {
            for (Character letra : modelada.values()) {
                if (letra == null) {
                    System.out.print("_");
                } else {
                    System.out.print(letra);
                }
            }
        }
        System.out.println("\n");
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
            int cantidadLetras = 0;

            for (LinkedHashMap<Integer, Character> modelado : letrasModeladas) {
                for (Character letraModelada: modelado.values()) {
                    if (letraModelada.equals(letra)) {
                        ++cantidadLetras;
                    }
                }
            }

            jugadorActual.acumularPuntuacion(3*cantidadLetras);
            System.out.println("Has ganado " + (cantidadLetras*3) + " puntos!!!");
        } else if (verificarLetra(letra, frase).equals("acertado")) {
            System.out.println("Has perdido 3 puntos!!!");
            jugadorActual.acumularPuntuacion(-3);
        } else {
            System.out.println("Has perdido un punto!!!");
            jugadorActual.acumularPuntuacion(-1);
        }
    }

    private void colocarLetra(char letra, String frase, Jugador jugadorActual)
    {
        int contadorLetras = 0;
        int contadorPalabras = 0;
        determinarPuntuacion(jugadorActual, letra, frase);

        if (verificarLetra(letra, frase).equals("acerto")) {
            System.out.println("Has acertado!!!");
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
        } else if (verificarLetra(letra, frase).equals("acertado")) {
            System.out.println("Esa palabra ya se puso!!!");
        } else {
            System.out.println("No está en la frase!!!");
        }
    }

    private boolean yaHayGanador(ArrayList<Jugador> jugadores)
    {
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() >= puntuacionMaxima) {
                return true;
            }
        }
        return false;
    }

    private Jugador determinarGanador(ArrayList<Jugador> jugadores)
    {
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() >= puntuacionMaxima) {
                return jugador;
            }
        }
        return null;
    }

    private int determinarGanadorRonda(ArrayList<Jugador> jugadores)
    {

        int comparador = jugadores.getFirst().getPuntuacion();

        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() >= comparador) {
                comparador = jugador.getPuntuacion();
            }
        }

        int index = 0;
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntuacion() == comparador) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private boolean seLLenoLaPalabra(ArrayList<LinkedHashMap<Integer, Character>> letrasDePalabra)
    {
        for (LinkedHashMap<Integer, Character> modelada : letrasDePalabra) {
            for (Character letra : modelada.values()) {
                if (letra == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verificarLetra(String letra)
    {
        char letra1 = ' ';
        if (letra.length() == 1 && Character.isLetter(letra.charAt(0))) {
            letra1 = Character.toLowerCase(letra.charAt(0));
            return true;
        } else {
            return false;
        }
    }

    private char obtenerLetra(String letra)
    {
        return Character.toLowerCase(letra.charAt(0));
    }

    public void jugar()
    {
        int turnoActual = 0;
        boolean juegoTerminado = false;
        String letraIngresada;
        char letra;
        boolean adivino = false;
        Scanner res = new Scanner(System.in);
        determinarFrase();

        while (!yaHayGanador(jugadores) || !seLLenoLaPalabra(letrasModeladasUsadas)) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            System.out.println("===Turno de " + jugadorActual.getNombre() + "===");
            System.out.println("=Puntos para ganar: " + puntuacionMaxima + "=====\n");

            System.out.println("Puntos acumulados:" + jugadorActual.getPuntuacion());

            if (!seLLenoLaPalabra(letrasModeladasUsadas)) {

                letraIngresada = " ";

                imprimirFrase();
                while (!verificarLetra(letraIngresada)) {

                    System.out.println("Ingresa una letra: ");
                    letraIngresada = res.next();
                }

                letra = obtenerLetra(letraIngresada);

                //basandose en el juego del ahorcado, si un jugador acierta una letra
                //el siguiente turno sigue sinedo suyo hasta que se equivoque
                if (verificarLetra(letra, fraseActual).equals("acerto")) {
                    adivino = true;
                } else {
                    adivino = false;
                }
                colocarLetra(letra, fraseActual, jugadorActual);



            } else {
                int indexGanador = determinarGanadorRonda(jugadores);
                jugadores.get(indexGanador).acumularPuntuacion(5);
                System.out.println("El " + jugadores.get(indexGanador).getNombre() + " ha ganado la ronda!!!");
                System.out.println("Has ganado 5 puntos mas");
                System.out.println("Puntos acumulados: " + jugadores.get(indexGanador).getPuntuacion());

                System.out.println("Se escoge nueva frase...");
                escogerFrase(banco);
                determinarFrase();
                letrasUsadas.clear();

            }

            if (!adivino) {
                turnoActual = (turnoActual + 1) % jugadores.size();
            }

        }
        Jugador ganador = determinarGanador(jugadores);
        System.out.println("El " + ganador.getNombre() + "ha ganado el juego!!!\n");

        System.out.println("===Tablero de puntuaciones===");

        Collections.sort(jugadores, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return Integer.compare(o1.getPuntuacion(),o2.getPuntuacion());
            }
        });

        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ": " + jugador.getPuntuacion());
        }

    }
}
