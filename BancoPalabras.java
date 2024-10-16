import java.util.ArrayList;
import java.util.Arrays;

public class BancoPalabras {

    //Se crea el atributo de la clase
    ArrayList<String> bancoFrases;

    /**
     * Constructor de la clase donde se generan las frases
     */
    public BancoPalabras()
    {

        //Se inicializa la colección y se hacen todas la frases.

        bancoFrases = new ArrayList<>();
        String[] vectorFrases = {"ayer comí muchas papas","me gusta programar en java", "hoy me pondre a estudiar", "Estoy haciendo la practica de java",
                "fortnite el mejor juego", "atentado en EU del 911", "no tengo carro azul", "jugar futbol me gusta mucho", "programacion orientada a objetos",
                "no me gusta poo"};

        //Un vez elaborado esto se mete cada elemento del vector a la colección
        bancoFrases.addAll(Arrays.asList(vectorFrases).subList(0, 10));

    }

    /**
     * Método que retorna la colección
     * @return
     */
    public ArrayList<String> getBancoPalabras()
    {
        return bancoFrases;
    }
}
