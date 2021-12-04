# 🎄 Advent of Code 2021 🎄

Solutions for [Advent of Code 2021](https://adventofcode.com/2021) puzzles in [Kotlin](https://kotlinlang.org/).

## Solutions

| Day | Puzzle                                                             | Solution                                                                               |
|-----|--------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| 1   | 📡 [Sonar Sweep](https://adventofcode.com/2021/day/1)              | ⭐⭐ [`SonarSweep`](src/main/kotlin/aoc2021/day01/SonarSweep.kt)                         |
| 2   | 🌊 [Dive!](https://adventofcode.com/2021/day/2)                    | ⭐⭐ [`Dive`](src/main/kotlin/aoc2021/day02/Dive.kt)                                     |
| 3   | 💾 [Binary Diagnostic](https://adventofcode.com/2021/day/3)        | ⭐⭐ [`BinaryDiagnostic`](src/main/kotlin/aoc2021/day03/BinaryDiagnostic.kt)             |
| 4   | 🦑 [Giant Squid](https://adventofcode.com/2021/day/4)              | ⭐⭐ [`GiantSquid`](src/main/kotlin/aoc2021/day04/GiantSquid.kt)                         |
| 5   | 🌪 [Hydrothermal Venture](https://adventofcode.com/2021/day/5)     | ⭐⭐ [`HydrothermalVenture`](src/main/kotlin/aoc2021/day05/HydrothermalVenture.kt)       |
| 6   | 🐠 [Lanternfish](https://adventofcode.com/2021/day/6)              | ⭐⭐ [`Lanternfish`](src/main/kotlin/aoc2021/day06/Lanternfish.kt)                       |
| 7   | 🦀 [The Treachery of Whales](https://adventofcode.com/2021/day/7)  | ⭐⭐ [`TheTreacheryOfWhales`](src/main/kotlin/aoc2021/day07/TheTreacheryOfWhales.kt)     |
| 8   | 🎰 [Seven Segment Search](https://adventofcode.com/2021/day/8)     | ⭐⭐ [`SevenSegmentSearch`](src/main/kotlin/aoc2021/day08/SevenSegmentSearch.kt)         |
| 9   | 🌋 [Smoke Basin](https://adventofcode.com/2021/day/9)              | ⭐⭐ [`SmokeBasin`](src/main/kotlin/aoc2021/day09/SmokeBasin.kt)                         |
| 10  | 💯 [Syntax Scoring](https://adventofcode.com/2021/day/10)          | ⭐⭐ [`SyntaxScoring`](src/main/kotlin/aoc2021/day10/SyntaxScoring.kt)                   |
| 11  | 🐙 [Dumbo Octopus](https://adventofcode.com/2021/day/11)           | ⭐⭐ [`DumboOctopus`](src/main/kotlin/aoc2021/day11/DumboOctopus.kt)                     |
| 12  | 🧭 [Passage Pathing](https://adventofcode.com/2021/day/12)         | ⭐⭐ [`PassagePathing`](src/main/kotlin/aoc2021/day12/PassagePathing.kt)                 |
| 13  | 📂 [Transparent Origami](https://adventofcode.com/2021/day/13)     | ⭐⭐ [`TransparentOrigami`](src/main/kotlin/aoc2021/day13/TransparentOrigami.kt)         |
| 14  | 🧪 [Extended Polymerization](https://adventofcode.com/2021/day/14) | ⭐⭐ [`ExtendedPolymerization`](src/main/kotlin/aoc2021/day14/ExtendedPolymerization.kt) |
| 15  | 🐚 [Chiton](https://adventofcode.com/2021/day/15)                  | ⭐⭐ [`Chiton`](src/main/kotlin/aoc2021/day15/Chiton.kt)                                 |
| 16  | 🔢 [Packet Decoder](https://adventofcode.com/2021/day/16)          | ⭐⭐ [`PacketDecoder`](src/main/kotlin/aoc2021/day16/PacketDecoder.kt)                   |
| 17  | ⛳ [Trick Shot](https://adventofcode.com/2021/day/17)               | ⭐⭐ [`TrickShot`](src/main/kotlin/aoc2021/day17/TrickShot.kt)                           |
| 18  | 🐌 [Snailfish](https://adventofcode.com/2021/day/18)               | ⭐⭐ [`Snailfish`](src/main/kotlin/aoc2021/day18/Snailfish.kt)                           |
| 19  | 🗼 [Beacon Scanner](https://adventofcode.com/2021/day/19)          | [❌](src/main/kotlin/aoc2021/day19/BeaconScanner.kt)                                    |
| 20  | 🧩 [Trench Map](https://adventofcode.com/2021/day/20)              | ⭐⭐ [`TrenchMap`](src/main/kotlin/aoc2021/day20/TrenchMap.kt)                           |
| 21  | 🎲 [Dirac Dice](https://adventofcode.com/2021/day/21)              | ⭐⭐ [`DiracDice`](src/main/kotlin/aoc2021/day21/DiracDice.kt)                           |
| 22  | ⚛ [Reactor Reboot](https://adventofcode.com/2021/day/22)           | [❌](src/main/kotlin/aoc2021/day22/ReactorReboot.kt)                                    |
| 23  | 🦐 [Amphipod](https://adventofcode.com/2021/day/23)                | [❌](src/main/kotlin/aoc2021/day23/Amphipods.kt)                                        |
| 24  | 🧮 [Arithmetic Logic Unit](https://adventofcode.com/2021/day/24)   | [❌](src/main/kotlin/aoc2021/day24/ArithmeticLogicUnit.kt)                              |
| 25  | 🥒 [Sea Cucumber](https://adventofcode.com/2021/day/25)            | ⭐ [`SeaCucumber`](src/main/kotlin/aoc2021/day25/SeaCucumber.kt)                        |

## Building and running

You can build executable JAR with `./gradlew build`, which you will find then under `build/libs/aoc2021.jar`. You can
run it interactively with `java -jar aoc2021.jar`:

```bash
/advent-of-code-2021$ java -jar build/libs/aoc2021.jar 
🎄 Advent of Code 2021 🎄

Please enter day from 1 to 25 (25): 1
Please enter input file (input.txt): examples/day-01.txt

📡 Sonar Sweep
Part One = 7
Part Two = 5
```

Alternatively, you can pass day and input file arguments directly:

```bash
/advent-of-code-2021$ java -jar build/libs/aoc2021.jar 2 examples/day-02.txt 
🎄 Advent of Code 2021 🎄

🌊 Dive!
Part One = 150
Part Two = 900
```

Finally, you can run it without building via `./gradlew run`:

```bash
/advent-of-code-2021$ ./gradlew run --args="3 examples/day-03.txt"

> Task :run
🎄 Advent of Code 2021 🎄

💾 Binary Diagnostic
Part One = 198
Part Two = 230

BUILD SUCCESSFUL in 999ms
2 actionable tasks: 1 executed, 1 up-to-date
```
