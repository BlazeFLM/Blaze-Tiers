import java.util.ArrayList;
import java.util.List;

public class DistributionCalculator {

    // -----------------------------
    // Tier Definition
    // -----------------------------
    static class Tier {
        String code;
        String name;
        double percentage;
        int count;

        Tier(String code, String name, double percentage) {
            this.code = code;
            this.name = name;
            this.percentage = percentage;
            this.count = 0;
        }
    }

    // -----------------------------
    // Result Object
    // -----------------------------
    public static class TierResult {
        public String code;
        public String name;
        public int startRank;
        public int endRank;
        public int players;

        public TierResult(String code, String name,
                          int startRank, int endRank, int players) {

            this.code = code;
            this.name = name;
            this.startRank = startRank;
            this.endRank = endRank;
            this.players = players;
        }
    }

    // -----------------------------
    // Main Generator
    // -----------------------------
    public static List<TierResult> generate(int totalPlayers) {

        List<TierResult> results = new ArrayList<>();

        if (totalPlayers < 1)
            return results;

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

        int ht1Count = 1;
        int remainingPlayers = totalPlayers - 1;

        // Guarantee minimum one player in each tier if possible
        for (Tier tier : subTiers) {
            if (remainingPlayers > 0) {
                tier.count = 1;
                remainingPlayers--;
            }
        }

        // Allocate remaining players
        int extraPool = remainingPlayers;

        if (extraPool > 0) {

            int allocatedExtra = 0;

            for (int i = 0; i < subTiers.length; i++) {

                if (i == subTiers.length - 1) {
                    subTiers[i].count += (extraPool - allocatedExtra);
                } else {
                    int extra = (int) Math.round(extraPool * subTiers[i].percentage);
                    subTiers[i].count += extra;
                    allocatedExtra += extra;
                }
            }
        }

        // Build results
        int currentRank = 1;

        // HT1
        results.add(new TierResult(
                "HT1",
                "Supernova",
                currentRank,
                currentRank,
                ht1Count));

        currentRank++;

        // Remaining tiers
        for (Tier tier : subTiers) {

            if (tier.count > 0) {

                results.add(new TierResult(
                        tier.code,
                        tier.name,
                        currentRank,
                        currentRank + tier.count - 1,
                        tier.count));

                currentRank += tier.count;
            }
        }

        return results;
    }
}