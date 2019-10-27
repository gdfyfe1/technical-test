package application.controller;

import application.instruction.BuySell;
import application.instruction.Instruction;
import application.instruction.SettledInstruction;
import application.report.ReportPrinterService;
import application.report.SettlementReportService;
import application.settlement.InstructionSettlerService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SettlementControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private SettlementController testObject;

    @Mock
    private Supplier<Instruction> supplierMock;

    @Mock
    private InstructionSettlerService instructionSettlerMock;

    @Mock
    private SettlementReportService reportServiceMock;

    @Mock
    private ReportPrinterService printerMock;

    @Mock
    private Instruction instructionMock;

    @Captor
    private ArgumentCaptor<List<SettledInstruction>> settledInstructionsCaptor;

    @Mock
    private SettledInstruction settledInstructionMock;

    @Before
    public void setUp() {
        testObject = new SettlementController(
                supplierMock,
                instructionSettlerMock,
                reportServiceMock,
                printerMock
        );
    }

    @Test
    public void runReport() {
        when(supplierMock.get()).thenReturn(instructionMock);
        when(instructionSettlerMock.createSettledInstruction(instructionMock))
                .thenReturn(settledInstructionMock);

        Map<LocalDate, BigDecimal> expectedMap = new HashMap<>();
        when(reportServiceMock.calculateSettledPriceByDates(anyList(), any()))
                .thenReturn(expectedMap);

        List<SettledInstruction> expectedList = new ArrayList<>();
        when(reportServiceMock.calculateRankByAmount(anyList(), any()))
                .thenReturn(expectedList);

        int numberOfInstructions = 100;
        testObject.runReport(numberOfInstructions);

        verify(supplierMock, times(numberOfInstructions))
                .get();
        verify(instructionSettlerMock, times(numberOfInstructions))
                .createSettledInstruction(instructionMock);

        verifySettledPriceByDate(numberOfInstructions, BuySell.B);
        verifySettledPriceByDate(numberOfInstructions, BuySell.S);
        verifyRankByAmount(numberOfInstructions, BuySell.B);
        verifyRankByAmount(numberOfInstructions, BuySell.S);

        verify(printerMock, times(2))
                .printSettlementMap(eq(expectedMap));

        verify(printerMock, times(2))
                .printRankedInstructions(eq(expectedList));
    }

    private void verifySettledPriceByDate(int numberOfInstructions, BuySell s) {
        verify(reportServiceMock, times(1))
                .calculateSettledPriceByDates(settledInstructionsCaptor.capture(), eq(s));
        verifyPassedSettledInstructions(numberOfInstructions);
    }

    private void verifyRankByAmount(int numberOfInstructions, BuySell s) {
        verify(reportServiceMock, times(1))
                .calculateRankByAmount(settledInstructionsCaptor.capture(), eq(s));
        verifyPassedSettledInstructions(numberOfInstructions);
    }

    private void verifyPassedSettledInstructions(int numberOfInstructions) {
        List<SettledInstruction> capturedInstructions = settledInstructionsCaptor.getValue();
        assertEquals(numberOfInstructions, capturedInstructions.size());

        SettledInstruction capturedSettleInstruction = capturedInstructions.get(0);
        assertEquals(settledInstructionMock, capturedSettleInstruction);
    }
}