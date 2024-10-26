import javax.swing.*;
import java.awt.*;

public class TestInterfaz {

    private JFrame frame;

    private JPanel panelPrincipal;
    private JPanel panelArriba;
    private JPanel panelAbajo;

    private JLabel enunciadoJuego;
    private JLabel enunciadoJuego1;
    private JLabel fraseAdivinar;
    private JLabel estadoJuego;

    private JTextField lecturaDeJugador;

    public TestInterfaz()
    {
        frame = new JFrame("Ahorcado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        enunciadoJuego1 = new JLabel("frase a adivinar; ");
        estadoJuego = new JLabel("Estado del juego");
        estadoJuego.setOpaque(true);
        estadoJuego.setBackground(new Color(255,105,97));
        estadoJuego.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(enunciadoJuego1, BorderLayout.CENTER);


        panelAbajo = new JPanel();
        panelAbajo.setBackground(new Color(150,153,162));
        panelAbajo.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelArriba = new JPanel();
        panelArriba.setBackground(new Color(150,153,162));
        panelArriba.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelArriba.add(estadoJuego);

        lecturaDeJugador = new JTextField(5);
        lecturaDeJugador.addActionListener(evento -> lecturaDeJugador());

        enunciadoJuego = new JLabel("Ingresa la letra: ");
        enunciadoJuego.setOpaque(true);
        enunciadoJuego.setBackground(new Color(255,105,97));
        enunciadoJuego.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));



        panelAbajo.add(lecturaDeJugador);
        panelAbajo.add(enunciadoJuego, FlowLayout.LEFT);
        frame.add(panelArriba, BorderLayout.NORTH);
        frame.add(panelAbajo, BorderLayout.SOUTH);
        frame.add(panelPrincipal, BorderLayout.CENTER);


        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void lecturaDeJugador()
    {
        String letra = lecturaDeJugador.getText();
        System.out.println("Letra ingresada: " + letra);
        lecturaDeJugador.setText("");
    }
}
