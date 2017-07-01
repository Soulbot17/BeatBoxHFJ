package Swing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by SuperDenissio on 30.06.2017.
 */
public class JLayouts extends JFrame{
    JLayouts frame;

    public static void main(String[] args) {
        JLayouts layouts = new JLayouts();
        layouts.createFrame();
    }
    public void createFrame() {
        frame = new JLayouts();
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Java is cool");
        JPanel panel = new JPanel();
        JButton button = new JButton("Press me?");
        JButton button1 = new JButton("Yo, dude!");
        JButton button2 = new JButton("Yo, dude!!!!!!!!!");
        Font font = new Font(Font.DIALOG,Font.BOLD,28);
        button2.setFont(font);
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); //BOX Layout - ахуенная штука

        String[] array = {"Go","Man","You","Are","The","Best!",}; //Список ёба
        JList<String> list = new JList<String>(array);
        Font font1 = new Font(Font.MONOSPACED,Frame.ERROR,24);
        list.setFont(font1);
        JScrollPane pane = new JScrollPane(list);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //всегда скрывать горизонтальную линию и показывать вертикальную
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //возможность выбора только одной строки
        list.addListSelectionListener(new ListSelectionListener() { // обработка события
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selection = list.getSelectedValue();
                    System.out.println(selection);
                }
            }
        });
        frame.add(pane,BorderLayout.WEST);


        panel.add(button);
        panel.add(button1);
        panel.add(button2);
        frame.add(panel,BorderLayout.EAST);

        frame.setVisible(true);
    }
 }
