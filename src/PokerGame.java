// Omeed Magness
// 9/13/15
//
// Plays a single round of 5-card draw poker (stores and loads scores from save files)

// TODO: save and load current hand

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PokerGame {
   private static final int STARTING_SCORE = 100;
   private static final int SCORE_MULTIPLIER = 5;
   private static final String SAVE_FILE_NAME = "src/saveFile.txt";
   private int score;
   private Deck deck;
   
   
   // pre: save file is valid (throws FileNotFoundException if not)
   // post: constructs a new PokerGame
   public PokerGame() {
      loadScore();
      deck = new Deck();
   }
     
   // post: returns an array of 5 cards dealt from the deck
   public Card[] deal() {
      deck.shuffle();
      Card[] hand = new Card[5];
      for (int i = 0; i <= 4; i++) {
         hand[i] = deck.deal();
      }
      return hand;
   }
   
   // pre: is given a valid array of cards with the "held" cards marked
   // post: deals a new card to each card that was held and returns this updated array
   public Card[] dealAgain(Card[] heldMarked) {
      for (int i = 0; i <= 4; i++) {
         if (!heldMarked[i].getHeld()) {
            heldMarked[i] = deck.deal();
         }
      }         
      deck.resetDeck();
      return heldMarked;
   }
      
   // pre: is given an array representing the re-dealt hand
   // post: returns whether or not the given array of cards was
   // a winning hand, and updates the score if so
   
   // NOTE: updateScore must be called by the client after dealAgain (although it seems
   // as if this should be automatically taken care of by the PokerGame class)
   // The reason for this is because a client wants to know the final hand, as well as whether
   // or not the hand has won. Thus there must be two methods to have two returns, and each
   // must be called by the client
   public boolean updateScore (Card[] finalHand) {
      score -= 5; // 5-point bet each time
      Card[] hand = new Card[5];
      hand = Arrays.copyOf(finalHand, 5);
      Arrays.sort(hand);
      int[] handValues = new int[5];
      Suit[] handSuits = new Suit[5];
      for (int i = 0; i <= 4; i++) {
         handValues[i] = hand[i].getRank();
         handSuits[i] = hand[i].getSuit();
      }
      if (handValues[0] == 10 && handValues[1] == 11 && handValues[2] == 12
                 && handValues[3] == 13 && handValues[4] == 14 && handSuits[0]
                 == handSuits[1] && handSuits[1] == handSuits[2] && handSuits[2]
                 == handSuits[3] && handSuits[3] == handSuits[4]) { // Royal Flush
         score += (SCORE_MULTIPLIER * 250);
      } else if (((handValues[0] + 1 == handValues[1] && handValues[1] + 1
                 == handValues[2] && handValues[2] + 1 == handValues[3] && handValues[3] + 1
                 == handValues[4]) || (handValues[0] == 2 && handValues[1] == 3 &&
                 handValues[2] == 4 && handValues[3] == 5 && handValues[4] == 14))
                 && handSuits[0] == handSuits[1] && handSuits[1] == handSuits[2]
                 && handSuits[2] == handSuits[3] && handSuits[3] == handSuits[4]) { // Straight Flush
         score += (SCORE_MULTIPLIER * 50);
      } else if ((handValues[0] == handValues[1] && handValues[1] == handValues[2]
                && handValues[2] == handValues[3]) || handValues[1] == handValues[2]
                && handValues[2] == handValues[3] && handValues[3] == handValues[4]) { // 4-of-a-kind
         score += (SCORE_MULTIPLIER * 25);
      } else if ((handValues[0] == handValues[1] && handValues[2] == handValues[3]
                && handValues[3] == handValues[4]) || (handValues[0] == handValues[1]
                && handValues[1] == handValues[2] && handValues[3] == handValues[4])) { // Full House
         score += (SCORE_MULTIPLIER * 8);
      } else if (handSuits[0]== handSuits[1] && handSuits[1] == handSuits[2] && handSuits[2]
                 == handSuits[3] && handSuits[3] == handSuits[4]) { // Flush
         score += (SCORE_MULTIPLIER * 5);
      } else if ((handValues[0] + 1 == handValues[1] && handValues[1] + 1
                 == handValues[2] && handValues[2] + 1 == handValues[3] && handValues[3] + 1
                 == handValues[4]) || (handValues[0] == 2 && handValues[1] == 3 &&
                 handValues[2] == 4 && handValues[3] == 5 && handValues[4] == 14)) { // Straight
         score += (SCORE_MULTIPLIER * 4);
      } else if ((handValues[0] == handValues[1] && handValues[1] == handValues[2]) ||
                (handValues[1] == handValues[2] && handValues[2] == handValues[3]) ||
                (handValues[2]  == handValues[3] && handValues[3] == handValues[4])) { // 3-of-a-kind
         score += (SCORE_MULTIPLIER * 3);
      } else if ((handValues[0] == handValues[1] && handValues[2] == handValues[3]) ||
                (handValues[1] == handValues[2] && handValues[3] == handValues[4]) || 
                (handValues[0] == handValues[1] && handValues[3] == handValues[4])) { // 2-pair
         score += (SCORE_MULTIPLIER * 2);
      } else if ((handValues[0] == handValues[1] && handValues[0] > 10) ||
                (handValues[1] == handValues[2] && handValues[1] > 10) ||
                (handValues[2] == handValues[3] & handValues[2] > 10) || 
                (handValues[3] == handValues[4] && handValues[3] > 10)) { // 1- pair
         score += (SCORE_MULTIPLIER);
      } else if (score == 0) {
         score = 100;
         return false;      
      } else {
         return false;
      }
      return true;         
   }
   
   // post: returns the current score of the game
   public int getScore() {
      return this.score;
   }
       
   // post: saves the score of the existing game to the file <SAVE_FILE_NAME>
   // (is assumed that it won't throw an exception because the file name
   // is tied to a string value constant within this program)
   public void saveScore() {
      try {
         PrintStream output = new PrintStream(new File(SAVE_FILE_NAME));
         output.print(score);
      } catch (FileNotFoundException e) {
         System.out.println("Illegal save file");
         System.exit(1);
      }
   }
  
   // post: loads the score from an existing file <SAVE_FILE_NAME> (is assumed that it won't throw
   // an exception because the file name is tied to a string value constant within this program)
   @SuppressWarnings("resource")
   private void loadScore() {
      try {
         Scanner input = new Scanner(new File(SAVE_FILE_NAME));
         if (input.hasNext()) {
            score = input.nextInt();
         } else {
            score = STARTING_SCORE;
         }
      } catch (FileNotFoundException e) {
         System.out.println("Ilegal save file");
         System.exit(1);
      }
   }
      
   // Represents a deck of cards
   private class Deck {
      private static final int NUMBER_OF_CARDS = 52;
      private List<Card> deck;
      private List<Card> currentHand;
      
      // post: creates a new deck (pre-shuffled)
      public Deck() {
         currentHand = new ArrayList<Card>();
         deck = new ArrayList<Card>();
         for (int i = 2; i <= (NUMBER_OF_CARDS / 4 + 1); i++) {
            for (int j = 1; j < 4; j++) {
               deck.add(new Card(j, i));
            }
         }
         shuffle();
      }
      
      // post: rearranges the order of the cards in the deck
      public void shuffle() {
         Collections.shuffle(deck);
      }
      
      // post: returns a single card drawn from the deck
      public Card deal() {
         Card drawn = deck.remove(0);
         currentHand.add(drawn);
         return drawn;
      }
      
      // post: restores the full 52 cards to the deck and empties the temporary hand
      public void resetDeck() {
         Deck complete = new Deck();
         this.deck = complete.deck;
         this.currentHand = null;
         this.currentHand = new ArrayList<Card>();
      }
   }
}
