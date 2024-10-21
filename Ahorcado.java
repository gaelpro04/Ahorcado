import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Collections;


public class Ahorcado {

    BancoPalabras banco;
    BancoPalabras bancoPalabrasUsadas;
    HashSet<Character> letrasUsadas = new HashSet<>();
    String fraseActual;
    ArrayList<HashMap<Character, Boolean>> letrasModeladas = new ArrayList<>();
    ArrayList<HashMap<Character, Boolean>> letrasModeladasUsadas = new ArrayList<>();
    int puntuacionMaxima;
    int cantidadJugadores;
    ArrayList<Jugador> jugadores = new ArrayList<>();


    public Ahorcado(int puntuacionMaxima, int cantidadJugadores)
    {
        if (cantidadJugadores > 1 && cantidadJugadores < 5) {
            this.puntuacionMaxima = puntuacionMaxima;
            this.cantidadJugadores = cantidadJugadores;
            banco = new BancoPalabras(true);
            bancoPalabrasUsadas = new BancoPalabras(false);
            jugadores = new ArrayList<>(cantidadJugadores);
        }
    }

    private void escogerFrase(BancoPalabras bancoPalabras)
    {
        Collections.shuffle(banco.getBancoPalabras());
        fraseActual = banco.getBancoPalabras().getFirst();
        bancoPalabrasUsadas.getBancoPalabras().add(banco.getBancoPalabras().removeFirst());
    }

    private void modelarFrase()
    {
        int cantPalabras = 0;
        for (Character letra : fraseActual.toCharArray()) {
            if (letra == ' ') {
                ++cantPalabras;
                letrasModeladas.add(
            } else {
                letrasUsadas.add(letra);
            }
        }
        letrasModeladasUsadas = new ArrayList<>(cantPalabras);

    }

    private void determinarFraseTerminada()
    {

    }


    private void hacerJugadores(int cantidadJugadores)
    {
        for (int i = 0; i < cantidadJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + (i+1)));
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
        if (verificarLetra(letra, frase).equals("acerto")) {
            letrasUsadas.add(letra);
        }
    }


}
