// Tier definitions containing the label and target percentage of the remaining pool
static class Tier {
    String code;
    String name;
    double percentage;
    int count = 0; // Number of players allocated

    Tier(String code, String name, double percentage) {
        this.code = code;
        this.name = name;
        this.percentage = percentage;
    }
}

void main() {
    Scanner scanner = new Scanner(System.in);
    IO.print("Enter total number of players (N): ");

    if (!scanner.hasNextInt()) {
        IO.println("Invalid input. Please enter a valid integer.");
        return;
    }
    int totalPlayers = scanner.nextInt();

    if (totalPlayers < 1) {
        IO.println("You need at least 1 player to generate a leaderboard.");
        return;
    }

    // Define the 9 underlying sub-tiers according to your precise shape constraints
    Tier[] subTiers = {
            new Tier("LT1", "Inferno", 0.02),
            new Tier("HT2", "Bonfire", 0.03),
            new Tier("LT2", "Blaze", 0.06),
            new Tier("HT3", "Flare", 0.10),
            new Tier("LT3", "Glow", 0.12),
            new Tier("HT4", "Gleam", 0.14),
            new Tier("LT4", "Ember", 0.16),
            new Tier("HT5", "Flicker", 0.18),
            new Tier("LT5", "Spark", 0.19)
    };

    // 1. HT1 (Supernova) always takes exactly 1 player
    int ht1Count = 1;
    int remainingPlayers = totalPlayers - 1;

    // 2. Guarantee no tier is empty by assigning 1 player minimum if pool allows
    for (Tier tier : subTiers) {
        if (remainingPlayers > 0) {
            tier.count = 1;
            remainingPlayers--;
        }
    }

    // 3. Pro-rate the rest of the players using the designated math model
    int extraPool = remainingPlayers;
    if (extraPool > 0) {
        int allocatedExtra = 0;
        for (int i = 0; i < subTiers.length; i++) {
            // If it is the last tier, absorb the rounding remainder to avoid loss
            if (i == subTiers.length - 1) {
                subTiers[i].count += (extraPool - allocatedExtra);
            } else {
                int extraForTier = (int) Math.round(extraPool * subTiers[i].percentage);
                subTiers[i].count += extraForTier;
                allocatedExtra += extraForTier;
            }
        }
    }

    // 4. Output the completed Leaderboard Distribution System
    IO.println("\n=============================================");
    IO.println("    PYRAMID LEADERBOARD RANGE DISTRIBUTION   ");
    IO.println("=============================================");
    IO.println("Total Players Input: " + totalPlayers);
    IO.println("---------------------------------------------");

    int currentRank = 1;

    // Print HT1 Top Champion
    printTierRange("HT1", "Supernova", currentRank, currentRank + ht1Count - 1, ht1Count);
    currentRank += ht1Count;

    // Print the remaining tiers sequentially
    for (Tier tier : subTiers) {
        if (tier.count > 0) {
            printTierRange(tier.code, tier.name, currentRank, currentRank + tier.count - 1, tier.count);
            currentRank += tier.count;
        } else {
            System.out.printf("[%-3s] %-10s : Not available for this small pool size\n", tier.code, tier.name);
        }
    }
    IO.println("=============================================");
    scanner.close();
}

private static void printTierRange(String code, String name, int start, int end, int total) {
    String rangeString = (start == end) ? "Rank " + start : "Ranks " + start + " - " + end;
    System.out.printf("[%-3s] %-11s : %-15s (%d %s)\n",
            code, name, rangeString, total, (total == 1 ? "player" : "players"));
}
