import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


public class Ahorcado {

    BancoPalabras banco;
    HashSet<Character> letrasUsadas = new HashSet<>();
    HashMap<Character, Integer> letras = new HashMap<>();
    int puntuacionMaxima;
    int cantidadJugadores;
    ArrayList<Jugador> jugadores = new ArrayList<>();


    public Ahorcado(int puntuacionMaxima, int cantidadJugadores)
    {
        this.puntuacionMaxima = puntuacionMaxima;
        this.cantidadJugadores = cantidadJugadores;
        banco = new BancoPalabras();
        jugadores = new ArrayList<>(cantidadJugadores);
    }

    public void jugar()
    {


    }
}
