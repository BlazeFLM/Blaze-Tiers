import java.util.List;
import java.util.Scanner;

public class ConsoleLeaderboard {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of players (N): ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.close();
            return;
        }

        int totalPlayers = scanner.nextInt();

        if (totalPlayers < 1) {
            System.out.println("You need at least 1 player to generate a leaderboard.");
            scanner.close();
            return;
        }

        List<DistributionCalculator.TierResult> results =
                DistributionCalculator.generate(totalPlayers);

        System.out.println();
        System.out.println("=============================================");
        System.out.println("    PYRAMID LEADERBOARD RANGE DISTRIBUTION");
        System.out.println("=============================================");
        System.out.println("Total Players Input: " + totalPlayers);
        System.out.println("---------------------------------------------");

        for (DistributionCalculator.TierResult tier : results) {
            printTierRange(
                    tier.code,
                    tier.name,
                    tier.startRank,
                    tier.endRank,
                    tier.players
            );
        }

        System.out.println("=============================================");

        scanner.close();
    }

    private static void printTierRange(String code,
                                       String name,
                                       int start,
                                       int end,
                                       int total) {

        String rangeString = (start == end)
                ? "Rank " + start
                : "Ranks " + start + " - " + end;

        System.out.printf(
                "[%-3s] %-11s : %-15s (%d %s)%n",
                code,
                name,
                rangeString,
                total,
                (total == 1 ? "player" : "players")
        );
    }
}