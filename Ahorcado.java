import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Ahorcado {

    //Banco
    private BancoPalabras banco;
    private BancoPalabras bancoPalabrasUsadas;

    //Atributos para el control de las letras
    private HashSet<Character> letrasUsadas = new HashSet<>();
    private ArrayList<LinkedHashMap<Integer, Character>> letrasModeladas;
    private ArrayList<LinkedHashMap<Integer, Character>> letrasModeladasUsadas;

    //Atributos poara el control del juego
    private String fraseActual;
    private int puntuacionMaxima;
    private int turnoActual;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private boolean adivino;
    private boolean parametrosValidos;

    //Atributos para la interfaz
    private JFrame frame;

    private JPanel panelPrincipal;
    private JPanel panelLetras;
    private JPanel panelArriba;
    private JPanel panelArribaCentro;
    private JPanel panelArribaIzq;
    private JPanel panelArribaDer;
    private JPanel panelAbajo;
    private JPanel frases;
    private JPanel panelPuntuaciones;

    private JLabel indicadorFraseEtiqueta;
    private JLabel ingresaEtiqueta;
    private JLabel estadoEtiqueta;
    private JLabel turnoJugador;
    private JLabel puntosJugador;
    private JLabel fraseJuego;
    private JLabel letrasUsadasJ;

    private JTextField letraALlenar;

    public Ahorcado(int puntuacionMaxima, int cantidadJugadores)
    {
        if (cantidadJugadores > 1 && cantidadJugadores < 5) {
            this.puntuacionMaxima = puntuacionMaxima;
            banco = new BancoPalabras(false);
            bancoPalabrasUsadas = new BancoPalabras(true);
            jugadores = new ArrayList<>(cantidadJugadores);
            hacerJugadores(cantidadJugadores);
            adivino = false;
            parametrosValidos = true;
        } else {
            parametrosValidos = false;
        }
    }

    private void hacerFrame()
    {
        frame = new JFrame("Ahorcado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        indicadorFraseEtiqueta = new JLabel("Adivina la frase: ", SwingConstants.CENTER);
        indicadorFraseEtiqueta.setFont(new Font("Cascadia",Font.BOLD, 14));
        estadoEtiqueta = new JLabel("Estado Juego");
        estadoEtiqueta.setFont(new Font("Cascadia",Font.BOLD, 12));
        turnoJugador = new JLabel("Turno Jugador");
        turnoJugador.setFont(new Font("Cascadia",Font.BOLD, 12));
        puntosJugador = new JLabel("Puntos Jugador");
        puntosJugador.setFont(new Font("Cascadia",Font.BOLD, 12));
        ingresaEtiqueta = new JLabel("Ingresa una letra: ", SwingConstants.CENTER);
        ingresaEtiqueta.setFont(new Font("Cascadia",Font.BOLD, 12));
        fraseJuego = new JLabel("___ ____ ___ ____", SwingConstants.CENTER);
        fraseJuego.setFont(new Font("Cascadia",Font.BOLD, 14));

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelLetras = new JPanel(new GridLayout(27,2));
        panelPuntuaciones = new JPanel(new GridLayout(20,2));
        letrasUsadasJ = new JLabel("Letras usadas", SwingConstants.CENTER);
        panelLetras.add(letrasUsadasJ);
        frases = new JPanel(new GridLayout(2,1));
        frases.add(indicadorFraseEtiqueta);
        frases.add(fraseJuego);

        panelPrincipal.add(frases, BorderLayout.CENTER);

        panelArriba = new JPanel();
        panelArribaCentro = new JPanel();
        panelArribaCentro.setBackground(new Color(253,253,150));
        panelArribaIzq = new JPanel();
        panelArribaIzq.setBackground(new Color(253,253,150));
        panelArribaDer = new JPanel();
        panelArribaDer.setBackground(new Color(253,253,150));
        panelArriba.setLayout(new BorderLayout());
        panelArribaCentro.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelArribaIzq.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelArribaDer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelArribaIzq.add(estadoEtiqueta);
        panelArribaCentro.add(turnoJugador);
        panelArribaDer.add(puntosJugador);

        panelArriba.add(panelArribaCentro, BorderLayout.CENTER);
        panelArriba.add(panelArribaIzq, BorderLayout.WEST);
        panelArriba.add(panelArribaDer, BorderLayout.EAST);


        panelAbajo = new JPanel();
        panelAbajo.setBackground(new Color(253,253,150));
        panelAbajo.setLayout(new FlowLayout(FlowLayout.CENTER));
        letraALlenar = new JTextField(5);
        letraALlenar.addActionListener(evento -> { try {
            lecturaDeJugador();

        } catch (StringIndexOutOfBoundsException e) {
            indicadorFraseEtiqueta.setText("Debes ingresar una letra valida");
        }});
        panelAbajo.add(ingresaEtiqueta, FlowLayout.LEFT);
        panelAbajo.add(letraALlenar);


        frame.add(panelPrincipal, BorderLayout.CENTER);
        frame.add(panelArriba, BorderLayout.NORTH);
        frame.add(panelAbajo, BorderLayout.SOUTH);
        frame.add(panelLetras, BorderLayout.EAST);
        frame.add(panelPuntuaciones, BorderLayout.WEST);
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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
        int palabras = 0;
        ArrayList<Character> array = new ArrayList<>();
        for (LinkedHashMap<Integer, Character> modelada : letrasModeladasUsadas) {
            if (palabras > 0) {
                array.add(' ');
            }
            for (Character letra : modelada.values()) {
                if (letra == null) {
                    array.add('_');
                } else {
                    array.add(letra);
                }
            }
            palabras++;
        }

        StringBuilder frase = new StringBuilder();
        for (Character letra : array) {
            frase.append(letra);
            frase.append(" ");
        }
        fraseJuego.setText(frase.toString());
    }




    private String verificarLetra(char letra, String frase)
    {
        if (!letrasUsadas.contains(letra) && frase.contains(String.valueOf(letra))) {
            return "acerto";

        } else if (letrasUsadas.contains(letra) && frase.contains(String.valueOf(letra))) {
            return "acertado";
        } else if (letrasUsadas.contains(letra) && !frase.contains((String.valueOf(letra)))) {
            return "noacertado";
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
            estadoEtiqueta.setText("Has ganado " + (cantidadLetras*3) + " puntos!!!");
        } else if (verificarLetra(letra, frase).equals("acertado")) {
            estadoEtiqueta.setText("Has perdido 3 puntos!!!");
            jugadorActual.acumularPuntuacion(-3);
        } else if (verificarLetra(letra, frase).equals("noacertado")) {
            estadoEtiqueta.setText("Has perdido 3 puntos!!!");
            jugadorActual.acumularPuntuacion(-3);
        } else if (verificarLetra(letra, frase).equals("erroneo")){
            estadoEtiqueta.setText("Has perdido un punto!!!");
            jugadorActual.acumularPuntuacion(-1);
        }
    }

    private void colocarLetra(char letra, String frase, Jugador jugadorActual)
    {
        int contadorLetras = 0;
        int contadorPalabras = 0;
        determinarPuntuacion(jugadorActual, letra, frase);

        if (verificarLetra(letra, frase).equals("acerto")) {
            estadoEtiqueta.setText("Has acertado!!!");
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
            estadoEtiqueta.setText("Esa letra ya se puso!!!");
        } else if (verificarLetra(letra, frase).equals("noacertado")) {
            estadoEtiqueta.setText("Esa letra ya ha sido utilizada!!!");
        } else if (verificarLetra(letra, frase).equals("erroneo")){
            estadoEtiqueta.setText("No está en la frase!!!");
            letrasUsadas.add(letra);
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
        if (parametrosValidos) {
            turnoActual = 0;
            hacerFrame();
            escogerFrase(banco);
            determinarFrase();
            imprimirFrase();
            estadoEtiqueta.setText("Puntos para ganar: " + puntuacionMaxima);
            turnoJugador.setText("Turno: " + jugadores.get(turnoActual).getNombre());
            puntosJugador.setText("Puntos: " + jugadores.get(turnoActual).getPuntuacion());
        } else {
            hacerFrame();
            letraALlenar.setEnabled(false);
            indicadorFraseEtiqueta.setText("Has ingresado un parametro mal...");
        }
    }

    private void lecturaDeJugador(){
        Jugador jugadorActual = jugadores.get(turnoActual);
        char letra = obtenerLetra(letraALlenar.getText());

        if (!verificarLetra(String.valueOf(letra))) {
            indicadorFraseEtiqueta.setText("Debes ingresar una letra valida");
        } else {
            if (verificarLetra(letra, fraseActual).equals("acerto")) {
                adivino = true;
            } else {
                adivino = false;
            }
            imprimirLetrasUsadas(letra);
            colocarLetra(letra,fraseActual,jugadorActual);
            imprimirFrase();


            if (yaHayGanador(jugadores) && seLLenoLaPalabra(letrasModeladasUsadas)) {
                Jugador ganador = determinarGanador(jugadores);

                indicadorFraseEtiqueta.setText("El " + ganador.getNombre() + " ha ganado!!!");
                letraALlenar.setEnabled(false);
                panelAbajo.remove(letraALlenar);
                panelAbajo.remove(ingresaEtiqueta);
                panelPuntuaciones.add(new JLabel("Puntuaciones finales", SwingConstants.CENTER));
                imprimirPuntuaciones(jugadores);
            } else if (seLLenoLaPalabra(letrasModeladasUsadas)) {

                indicadorFraseEtiqueta.setText("Se escogió nueva frase");
                escogerFrase(banco);
                determinarFrase();
                letrasUsadas.clear();
                imprimirFrase();
                panelLetras.removeAll();
                panelLetras.revalidate();
                panelLetras.repaint();

                panelLetras.add(letrasUsadasJ);
            } else {
                if (!adivino) {
                    turnoActual = (turnoActual + 1) % jugadores.size();
                }
                actualizar(turnoActual);
            }
        }
        letraALlenar.setText("");
    }

    private void actualizar(int turnoActual)
    {
        Jugador jugadorActual = jugadores.get(turnoActual);
        turnoJugador.setText("Turno de: " + jugadorActual.getNombre());
        puntosJugador.setText("Puntos: " + jugadorActual.getPuntuacion());
        indicadorFraseEtiqueta.setText("Frase a adivinar: ");
    }

    private void imprimirLetrasUsadas(char letra)
    {
        if (!letrasUsadas.contains(letra)) {
            JLabel etiqueta = new JLabel(String.valueOf(letra), SwingConstants.CENTER);
            etiqueta.setOpaque(true);
            etiqueta.setBackground(new Color(207,207, 196));
            panelLetras.add(etiqueta);
        }
    }

    private void imprimirPuntuaciones(ArrayList<Jugador> jugadoress)
    {
        for (Jugador jugador : jugadoress) {
            JLabel jugadorEtiqueta = new JLabel(jugador.getNombre() + ": " + jugador.getPuntuacion() + " puntos", SwingConstants.CENTER);
            jugadorEtiqueta.setOpaque(true);
            jugadorEtiqueta.setBackground(new Color(207,207,196));

            panelPuntuaciones.add(jugadorEtiqueta);
        }
    }
}
