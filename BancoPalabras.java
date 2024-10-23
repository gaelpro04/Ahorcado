import java.util.ArrayList;
import java.util.Arrays;

public class BancoPalabras {

    //Se crea el atributo de la clase
    ArrayList<String> bancoFrases;

    /**
     * Constructor de la clase donde se generan las frases
     */
    public BancoPalabras(boolean vacio)
    {

        //Se inicializa la colección y se hacen todas la frases.
        if (vacio) {
            bancoFrases = new ArrayList<>();
        } else {
            bancoFrases = new ArrayList<>();
            String[] vectorFrases = {"ayer comi muchas papas","me gusta programar en java", "hoy me pondre a estudiar", "estoy haciendo la practica de java",
                    "el avion blanco vuela", "programar es mejor que hacer circuitos", "no tengo carro azul", "Jugar futbol me gusta mucho", "programacion orientada a objetos",
                    "No me gusta poo"};

            //Un vez elaborado esto se mete cada elemento del vector a la colección
            bancoFrases.addAll(Arrays.asList(vectorFrases).subList(0, 10));
        }


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
