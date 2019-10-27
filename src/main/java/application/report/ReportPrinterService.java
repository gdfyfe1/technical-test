package application.report;

import application.instruction.SettledInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReportPrinterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportPrinterService.class);

    public void printSettlementMap(Map<LocalDate, BigDecimal> calculateSettledPriceByDates) {
        calculateSettledPriceByDates.forEach(
                (date, price) -> LOGGER.info("Settlement Date: {}, Total Price: {}", date, price)
        );
    }

    public void printRankedInstructions(List<SettledInstruction> calculateRankByAmount) {
        calculateRankByAmount.forEach(settledInstruction -> LOGGER.info(settledInstruction.toString()));
    }
}
