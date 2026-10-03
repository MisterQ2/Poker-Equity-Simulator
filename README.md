# Texas Hold'em Poker Simulator

A complete heads-up Texas Hold’em poker simulator written in Java, featuring an AI opponent and a Monte Carlo–based equity calculator for probabilistic decision-making.

## Motivation

Built this project to see how far I could push my self-taught programming skills while building something meaningful. Coming from a competitive chess background, I was especially interested in modeling logic-based decision-making under uncertainty, and I attempted to achieve this through the AI bot and equity calculator.

## Features

- **Full Poker Game**: Play heads-up poker against an AI opponent with complete betting rounds (pre-flop, flop, turn, river)
- **Intelligent AI**: Computer opponent makes decisions based on hand strength evaluation
- **Monte Carlo Simulator**: Calculate hand equity by running thousands of simulations
- **Complete Hand Evaluation**: Accurately determines all poker hands from high card to royal flush, including proper kicker comparisons

## Demo

### 1. Full Poker Game:

```text
Enter Buy In:
100

Your stack: $100
Opponent's stack: $100

The blind for each round is $2.

@@@@@@@@@@@@@@@@@
    NEW ROUND
@@@@@@@@@@@@@@@@@

Dealing hole cards...

Your cards: 2 ♠, 6 ♠

$4 in blinds has been added to the pot.

Choose an option:

1: Fold
2: Check
3: Bet
2

You check.

Opponent bets $47.

Choose an option:

1: Fold
2: Call
3: Raise
3
How much do you want to raise? (You can raise up to $51)
20

Opponent Calls

[ ... ]

@@@@@@@@@@@@@@@@
    SHOWDOWN
@@@@@@@@@@@@@@@@

The Board: 10 ♦, Q ♠, 5 ♦, A ♥, 7 ♠

Your Hand: 2 ♠, 6 ♠
Opponent's Hand: A ♠, 10 ♠

You lose to your opponent's Two Pair.
You lost $90.
Your stack is now $10.
Opponent's stack is now $190.
```

---

### 2. Monte Carlo Simulator:

```text
Player Hand: A ♥, A ♦
Opponent Hand: K ♣, K ♠
Trials: 10000

Player Wins: 8164
Opponent Wins: 1798
Chops: 38

Player Equity: 81.83%
Opponent Equity: 18.17%
```

---
## Accuracy Validation
To verify the Monte Carlo simulator's correctness, I tested it against well-known poker equity calculations:

|      Scenario      | Expected Result | Simulator Result (100K trials) |    Status    |
|--------------------|-----------------|--------------------------------|--------------|
| A-A vs K-K         |  ~82% vs. ~18%  |        81.55% vs 18.45%        | ✅ Verified |
| J-J vs A-K offsuit |  ~57% vs. ~43%  |        57.22% vs 42.78%        | ✅ Verified |
| J-J vs A-K suited  |  ~54% vs. ~46%  |        53.76% vs 46.24%        | ✅ Verified |
| 2-2 vs A-K suited  |  ~50% vs. ~40%  |        49.96% vs 50.04%        | ✅ Verified |

These results match commercial poker software (PokerStove, Equilab) within statistical margin of error (±0.50% for 100,000 trials), confirming the hand evaluation algorithm correctly handles all poker hands including edge cases like wheel straights (A-2-3-4-5).

**Validation Process:**
1. Manually configured hole cards in the Monte Carlo section
2. Ran 100,000 simulations for each scenario
3. Compared results against established poker theory and commercial tools
4. All results accurate within expected variance

## What I Learned

- Managing complex state across multiple game phases
- Implementing comprehensive game logic with many edge cases
- Building a basic AI using hand strength evaluation
- Using Monte Carlo methods for probability calculations

## How to Run

1. Clone this repository
2. Compile: `javac poker/Poker.java`
3. Run: `java poker.Poker`
4. Choose option 1 for Heads-Up Poker Game, or option 2 for Monte Carlo Simulations

## Technical Implementation

**Hand Evaluation Algorithm**: 
- Evaluates all poker hands using card frequency counting and suit tracking
- Handles edge cases like wheel straights (A-2-3-4-5)
- Properly compares kickers for tied hands

**AI Decision Making**:
- Evaluates pre-flop hand strength based on card values, pairs, suited cards, and connectors
- Adjusts betting behavior based on hand strength tiers
- Implements variable bet sizing and bluffing frequency

**Monte Carlo Simulation**:
- Runs N simulations with random community cards
- Calculates win probability, loss probability, and chop probability
- Can test specific hand matchups (e.g., A-A vs K-K), as well as any possible scenario in a heads-up poker game

## Future Improvements

If I were to continue this project, I would:
- Refactor to use object-oriented design (Card, Hand, Player classes)
- Add post-flop hand strength evaluation for smarter AI
- Implement confidence intervals for Monte Carlo results
- Add expected value (EV) calculations for decision-making

## Project Stats

- **Lines of Code**: ~2,800
- **Development Time**: 6 months
- **Language**: Java

---

## Author
Adarsh N.
