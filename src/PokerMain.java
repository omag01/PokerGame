// Omeed Magness
// 9/13/15
//
// Plays a game of five card draw poker with the user. 

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;

public class PokerMain implements ActionListener {
   @SuppressWarnings("unused")
   public static void main(String[] args) throws FileNotFoundException {
      PokerMain gui = new PokerMain();
   }
   
   private PokerGame game;
   private Card[] hand;
   private JFrame frame;
   private JPanel cardPanel;
   private JPanel cardPanelGrid;
   private JLabel card1;
   private JLabel card2;
   private JLabel card3;
   private JLabel card4;
   private JLabel card5;
   private JButton dealButton;
   private JCheckBox hold1;
   private JCheckBox hold2;
   private JCheckBox hold3;
   private JCheckBox hold4;
   private JCheckBox hold5;
   private JLabel text;
   
   // pre: assumes saveFileName is valid
   // post: constructs a new GUI interface for playing a poker game
   public PokerMain() {
      // set up game
      game = new PokerGame();
      hand = new Card[5];
      hand = game.deal();
      
      // set up frame
      frame = new JFrame("5-Card Draw");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(450, 350));
      frame.setResizable(false);
      FlowLayout f = new FlowLayout();
      f.setVgap(40); // to put more vertical space between components
      frame.setLayout(f);
      
      // position frame in center of screen
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double width = screenSize.getWidth();
      int xCoordinate = (int) (width / 2 - 450 / 2);
      double height = screenSize.getHeight();
      int yCoordinate = (int) (height / 2 - 350 / 2);
      frame.setLocation(xCoordinate, yCoordinate);
      
      // set up top panel (screen with cards and checkboxes)      
      cardPanel = new JPanel();
      cardPanel.setLayout(new BorderLayout());
      cardPanel.setBackground(Color.LIGHT_GRAY);
      text = new JLabel("                                                                                                          Hold or Draw?");
      cardPanel.add(text, BorderLayout.NORTH);
      cardPanelGrid = new JPanel(new GridLayout(2, 5));
      cardPanelGrid.setBackground(Color.LIGHT_GRAY);
      card1 = new JLabel();
      card2 = new JLabel();
      card3 = new JLabel();
      card4 = new JLabel();
      card5 = new JLabel();
      setCardText();
      cardPanelGrid.add(card1);
      cardPanelGrid.add(card2);
      cardPanelGrid.add(card3);
      cardPanelGrid.add(card4);
      cardPanelGrid.add(card5);
      hold1 = new JCheckBox();
      hold1.setBackground(Color.RED);
      cardPanelGrid.add(hold1);
      hold2 = new JCheckBox();
      hold2.setBackground(Color.RED);
      cardPanelGrid.add(hold2);
      hold3 = new JCheckBox();
      hold3.setBackground(Color.RED);
      cardPanelGrid.add(hold3);
      hold4 = new JCheckBox();
      hold4.setBackground(Color.RED);
      cardPanelGrid.add(hold4);
      hold5 = new JCheckBox();
      hold5.setBackground(Color.RED);
      cardPanelGrid.add(hold5);

      cardPanel.add(cardPanelGrid, BorderLayout.CENTER);      
      
      // set up button
      dealButton = new JButton();
      dealButton.setPreferredSize(new Dimension(60, 40));
      dealButton.addActionListener(this);
      
      // add panels to frame
      frame.add(cardPanel);
      frame.add(dealButton);
      frame.getContentPane().setBackground(Color.RED); // changes the color of the center of window
      frame.setVisible(true);
   }
   
   // post: responds to a button click on the dealButton
   
   // NOTE: I cannot add a throws clause to the method header or it will not correctly
   // implement ActionListener, and so instead I use a try/catch statement
   public void actionPerformed(ActionEvent event) {
      if (text.getText().equals("                                                                                                          Hold or Draw?")) {
         if (hold1.isSelected()) {
            hand[0].setHeld(true);
         }
         if (hold2.isSelected()) {
            hand[1].setHeld(true);
         }
         if (hold3.isSelected()) {
            hand[2].setHeld(true);
         }
         if (hold4.isSelected()) {
            hand[3].setHeld(true);
         }
         if (hold5.isSelected()) {
            hand[4].setHeld(true);
         }
         hand = game.dealAgain(hand);
         setCardText();
         boolean win = game.updateScore(hand);
         if (win) {
            text.setText("                                                                                                     You win!!!!!!!!!!!");
            frame.repaint();
            text.setText(game.getScore() + "                                                                                                 You win!!!!!!!!!!!");
            frame.repaint();
         } else {
            text.setText("                                                                                                              Deal again");
            frame.repaint();
         }
      } else {
         hand = game.deal();
         hold1.setSelected(false);
         hold2.setSelected(false);
         hold3.setSelected(false);
         hold4.setSelected(false);
         hold5.setSelected(false);
         text.setText("                                                                                                          Hold or Draw?");
         frame.repaint();
         setCardText();
      }
      game.saveScore();
   }
   
   // post: resets the GUI cards to reflect changes to the current hand (repaints as well)
   private void setCardText() {
      card1.setText(hand[0].toString());
      card2.setText(hand[1].toString());
      card3.setText(hand[2].toString());
      card4.setText(hand[3].toString());
      card5.setText(hand[4].toString());
      frame.repaint();
   }
}      