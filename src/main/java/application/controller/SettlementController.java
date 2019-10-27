package application.controller;

import application.instruction.BuySell;
import application.instruction.Instruction;
import application.instruction.SettledInstruction;
import application.report.ReportPrinterService;
import application.report.SettlementReportService;
import application.settlement.InstructionSettlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class SettlementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementController.class);

    private final InstructionSettlerService instructionSettlerService;
    private final SettlementReportService reportService;
    private final ReportPrinterService reportPrinterService;

    public SettlementController(
            InstructionSettlerService instructionSettlerService,
            SettlementReportService reportService,
            ReportPrinterService reportPrinterService
    ) {
        this.instructionSettlerService = instructionSettlerService;
        this.reportService = reportService;
        this.reportPrinterService = reportPrinterService;
    }

    public void runReport(Stream<Instruction> instructions) {
        List<SettledInstruction> settledInstructions = instructions
                .parallel()
                .map(instructionSettlerService::settleInstruction)
                .collect(Collectors.toList());

        LOGGER.info("");
        LOGGER.info("Printing amount in USD settled incoming for each settlement date.");
        reportPrinterService.printSettlementMap(
                reportService.calculateSettledPriceByDates(settledInstructions, BuySell.B)
        );

        LOGGER.info("");
        LOGGER.info("Printing amount in USD settled outgoing for each settlement date.");
        reportPrinterService.printSettlementMap(
                reportService.calculateSettledPriceByDates(settledInstructions, BuySell.S)
        );

        LOGGER.info("");
        LOGGER.info("Ranking of entities based on outgoing amount.");
        reportPrinterService.printRankedInstructions(
                reportService.calculateRankByAmount(settledInstructions, BuySell.B)
        );

        LOGGER.info("");
        LOGGER.info("Ranking of entities based on incoming amount.");
        reportPrinterService.printRankedInstructions(
                reportService.calculateRankByAmount(settledInstructions, BuySell.S)
        );
    }
}
