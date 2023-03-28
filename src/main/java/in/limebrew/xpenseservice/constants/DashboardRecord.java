package in.limebrew.xpenseservice.constants;

public record DashboardRecord (
        double netEarnings,
        double netExpenses,
        double netInvestments,
        double netFundTransfer,
        double netSavings ) {
}
