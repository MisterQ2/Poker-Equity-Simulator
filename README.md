# Texas Hold'em Poker Simulator

A complete heads-up Texas Hold’em poker simulator written in Java, featuring an AI opponent and a Monte Carlo–based equity calculator for probabilistic decision-making.

## Motivation

This was my first independent programming project, built without any formal CS coursework. I wanted to see how far I could push my self-taught programming skills while building something meaningful. Coming from a competitive chess background, I was especially interested in modeling logic-based decision-making under uncertainty, and I attempted to achieve this through the AI bot and equity calculator.

## Features

- **Full Poker Game**: Play heads-up poker against an AI opponent with complete betting rounds (pre-flop, flop, turn, river)
- **Basic AI**: Computer opponent makes decisions based on hand strength evaluation
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
**Note: Outputs have been reformatted for readability; actual console output uses plain text suit names.
---

### 2. Monte Carlo Simulator:

```text
Player Hand: A ♥, A ♦
Opponent Hand: K ♣, K ♠
Trials: 1000000

Player Wins: 810773.0
Opponent Wins: 185986.0
Chops: 3241.0

Player Equity: 81.24%
Opponent Equity: 18.76%
```

---
## Accuracy Validation
To verify the Monte Carlo simulator's correctness, I tested it against several heads-up poker scenarios:

**Note: First number/uppercase letter is the rank, and second lowercase letter is the suit.

Pre-flop Scenarios:

|      Scenario      |   Expected Result   |  Simulator Result (1M trials)  |            Status             |
|--------------------|---------------------|--------------------------------|-------------------------------|
|   Ah-Ad vs Ks-Kc   |  81.25% vs. 18.75%  |        81.24% vs 18.76%        | Within ±0.1% --> ✅ Verified |
|   Ah-Kd vs Js-Jc   |  42.74% vs. 57.26%  |        42.70% vs 57.30%        | Within ±0.1% --> ✅ Verified |
|   Ah-Kh vs Js-Jc   |  46.13% vs. 53.87%  |        46.12% vs 53.88%        | Within ±0.1% --> ✅ Verified |
|   Ah-Kh vs 2s-2c   |  50.09% vs. 49.91%  |        50.09% vs 49.91%        | Within ±0.1% --> ✅ Verified |
|   8h-9h vs 3s-3c   |  53.14% vs. 46.86%  |        53.22% vs 46.78%        | Within ±0.1% --> ✅ Verified |

Post-flop Scenarios:

|                 Scenario                 |   Expected Result   |  Simulator Result (1M trials)  |            Status             |
|------------------------------------------|---------------------|--------------------------------|-------------------------------|
|    As-Ks vs Qh-Qc, Board: 9s, 5s, 2d     |  55.05% vs. 44.95%  |        55.10% vs 44.90%        | Within ±0.1% --> ✅ Verified |
|    Jh-10h vs Ac-Ad, Board: 9h, 8c, 2h    |  56.26% vs. 43.74%  |        56.24% vs 43.76%        | Within ±0.1% --> ✅ Verified |
|    Qc-Jc vs Kd-10d, Board: Kc, 9c, 4h    |  43.03% vs. 56.97%  |        43.08% vs 56.92%        | Within ±0.1% --> ✅ Verified |
|  Ad-4d vs Ks-Kh, Board: 9d, 6d, 2c, 7s   |  27.27% vs. 72.73%  |        27.23% vs 72.77%        | Within ±0.1% --> ✅ Verified |
| 10s-10d vs 9c-8c, Board: 7h, 6d, 2s, 3c  |  86.36% vs. 13.64%  |        86.41% vs 13.59%        | Within ±0.1% --> ✅ Verified |

All ten results fell within their 95% confidence intervals (≈ ±0.1% at 1M trials; standard error ≈ 0.05%) of published equity calculators (CardPlayer, PokerNews), consistent with correct hand evaluation.

**Validation Process:**
1. Manually configured hole cards in the Monte Carlo section
2. Ran 1,000,000 simulations for each scenario
3. Compared results against established poker equity calculators
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
- Refactor to use object-oriented design (Card, Hand, Player classes) and more proper coding etiquette
- Further optimize hand-classification aglorithms
- Add more sophisticated expected value (EV) calculations for AI decision-making

## Project Stats

- **Lines of Code**: ~2,700
- **Development Time**: 6 months
- **Language**: Java

---

## Author
Adarsh N.
