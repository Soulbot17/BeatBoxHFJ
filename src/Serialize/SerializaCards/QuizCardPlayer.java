//package Serialize.SerializaCards;
//
//import sun.plugin.javascript.JSClassLoader;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.util.ArrayList;
//
///**
// * Created by SuperDenissio on 30.06.2017.
// */
//public class QuizCardPlayer {
//    private JTextArea display;
//    private JTextArea answer;
//    private ArrayList<QuizCard> cardList;
//    private QuizCard currentCard;
//    private int currentCardIndex;
//    private JFrame frame;
//    private JButton nextButton;
//    private boolean isShowAnswer;
//
//    public static void main(String[] args) {
//        QuizCardPlayer reader = new QuizCardPlayer();
//        reader.go();
//    }
//
//    public void go(){
//        frame = new JFrame("QuizCardPlayer");
//        JPanel mainPanel = new JPanel();
//        Font bigFont = new Font(Font.SANS_SERIF,Font.BOLD,24);
//
//        display = new JTextArea(10,20);
//        display.setFont(bigFont);
//
//        display.setLineWrap(true);
//        display.setWrapStyleWord(true);
//        display.setEditable(false);
//
//        JScrollPane qScroller = new JScrollPane(display);
//        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        nextButton = new JButton("Next Question");
//        mainPanel.add(qScroller);
//        mainPanel.add(nextButton);
//        nextButton.addActionListener(new NextCardListener());
//
//        JMenuBar menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//        JMenuItem loadMenuItem = new JMenuItem("Load card set");
//        loadMenuItem.addActionListener(new OpenMenuListener());
//        fileMenu.add(loadMenuItem);
//        menuBar.add(fileMenu);
//        frame.setJMenuBar(menuBar);
//        frame.add(BorderLayout.CENTER,mainPanel);
//        frame.setSize(640,500);
//        frame.setVisible(true);
//    }
//
//    public class NextCardListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (isShowAnswer) {
//                display.setText(currentCard.getAnster());
//                nextButton.setText("Next Card");
//                isShowAnswer = false;
//            } else {
//                if (currentCardIndex<cardList.size()) {
//                    showNextCard();
//                } else {
//                    display.setText("That was last card :(");
//                    nextButton.setEnabled(false);
//                }
//            }
//        }
//    }
//
//    public class OpenMenuListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.showSaveDialog(frame);
//            loadFile(fileChooser.getSelectedFile());
//        }
//    }
//
//    private void loadFile(File file){
//        cardList = new ArrayList<QuizCard>();
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line = null;
//            while ((line = reader.readLine())!=null) {
//                makeCard(line);
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void makeCard(String lineToParse) {
//        String[] result = lineToParse.split("/");
//        QuizCard card = new QuizCard(result[0], result[1]);
//        cardList.add(card);
//        System.out.println("Made a card");
//    }
//
//    private void showNextCard() {
//        currentCard = cardList.get(currentCardIndex);
//        currentCardIndex++;
//        display.setText(currentCard.getQuestion());
//        nextButton.setText("Show Answer");
//        isShowAnswer = true;
//    }
//}
