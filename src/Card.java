// Omeed Magness
// 9/13/15
//
// Represents an individual playing card

public class Card implements Comparable<Card> {
   // 2-10 --> 2-10, J-A --> 11-14
   private int rank;
   private boolean held;
   private Suit myCardSuit;
   
   // pre: assumes valid input
   // post: constructs a card with the given suit and rank
   public Card(Suit s, int rank) {
      myCardSuit = s;
      this.rank = rank;
   }
   
   // pre: assumes valid input
   // post: Makes a card with the given rank and suit
   // corresponding to the given ordinal value of the enum Card
   public Card(int ordinal, int rank) {
      myCardSuit = Suit.values()[ordinal];
      this.rank = rank;
   }
   
   // post: returns the String representation of a card(e.g. "4-CLUBS")
   public String toString() {
      String display;
      if (rank < 11) {
         display = rank + "-" + myCardSuit;
      } else if (rank == 11) {
         display = "J-" + myCardSuit;
      } else if (rank == 12) {
         display = "Q-" + myCardSuit;
      } else if (rank == 13) {
         display = "K-" + myCardSuit;
      } else { // rank == 14
         display = "A-" + myCardSuit;
      }
      return display;
   }
   
   // post: returns the suit of a card
   public Suit getSuit() {
      return myCardSuit;
   }
   
   // post: returns the rank of a card
   public int getRank() {
      return this.rank;
   }
   
   // post: sets this card to either be held or not
   public void setHeld(boolean held) {
      this.held = held;
   }
   
   // post: returns whether or not this card is held
   public boolean getHeld() {
      return this.held;
   }
   
   // pre: assumes valid input
   // post: returns an integer representing the relationship between this card and given card
   public int compareTo (Card other) {
      if (this.rank < other.rank) {
         return -1;
      } else if (this.rank > other.rank) {
         return 1;
      } else { // this.rank == other.rank
         return 0;
      }
   }
}