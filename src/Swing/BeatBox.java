package Swing;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by SuperDenissio on 30.06.2017.
 */
public class BeatBox {
    JPanel mainPanel;
    ArrayList<JCheckBox> checkbotList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;

    String[] instrumentNames = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat",
            "Acoustic Snare", "Crash Cymbal","Hand Clap","High Hom","Hi Bango",
            "Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public void buildGUI(){
        theFrame = new JFrame("SuperUltraMega Swing.BeatBox");
        theFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkbotList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS); //коробка?

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start); //запихал кнопку в коробку?

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serialize = new JButton("Serialize");
        serialize.addActionListener(new MySaveListener());
        buttonBox.add(serialize);

        JButton open = new JButton("Open");
        open.addActionListener(new MyReadListener());
        buttonBox.add(open);

        JButton clear = new JButton("Clear");
        clear.addActionListener(new MyClearListener());
        buttonBox.add(clear);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i<16;i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST,buttonBox);
        background.add(BorderLayout.WEST,nameBox);

        theFrame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);

        for (int i = 0; i<256; i++) { //хуячим 256 флажков. циклы - сила!
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkbotList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() { //подготовка синтезатора и дорожки
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart(){
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i<16;i++) {
            trackList = new int[16];

           int key = instruments[i];

            for (int j = 0; j<16;j++) {
                JCheckBox jc = checkbotList.get(j+(16*i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else trackList[j] = 0;
            }

            makeTracks(trackList);
            track.add(makeEvent(176,1,127,0,16));
        }

        track.add(makeEvent(192,9,1,0,15));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyStartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor*1.3));
        }
    }

    public class MyDownTempoListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor*0.93));
        }
    }

    public void makeTracks(int[] list) {
        for (int i = 0; i<16; i++) {
            int key = list[i];

            if (key!=0) {
               track.add(makeEvent(144,9,key,100,i));
               track.add(makeEvent(128,9,key ,100,i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public class MySaveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean[] checkBoxState = new boolean[256];

            for (int i = 0; i<256;i++) {
                JCheckBox check = (JCheckBox) checkbotList.get(i);
                if (check.isSelected()) {
                    checkBoxState[i] = true;
                }
            }

            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showSaveDialog(theFrame);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                os.writeObject(checkBoxState);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class MyReadListener implements ActionListener{
        boolean[] checkboxstate = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(theFrame);
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
                checkboxstate = (boolean[]) os.readObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            for (int i = 0; i<256;i++) {
                JCheckBox checkBox = checkbotList.get(i);
                if (checkboxstate[i]) {
                    checkBox.setSelected(true);
                } else checkBox.setSelected(false);
            }
            sequencer.stop();
            buildTrackAndStart();
        }

    }

    public class MyClearListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i<256;i++) {
                checkbotList.get(i).setSelected(false);
            }
        }
    }
}

