Feature: Settle an instruction by calculating the correct price and instruction date

  Scenario Outline: Settle an instructions date
    Given an instruction with <settlementDay> settlement date and a currency of <currency>
    When the instruction is settled
    Then the new settlement date will be <newSettlementDay>
    Examples:
      | settlementDay | currency | newSettlementDay |
      | Monday        | USD      | Monday           |
      | Tuesday       | USD      | Tuesday          |
      | Wednesday     | USD      | Wednesday        |
      | Thursday      | USD      | Thursday         |
      | Friday        | USD      | Friday           |
      | Saturday      | USD      | NextMonday       |
      | Sunday        | USD      | NextMonday       |
      | Monday        | AED      | Monday           |
      | Tuesday       | AED      | Tuesday          |
      | Wednesday     | AED      | Wednesday        |
      | Thursday      | AED      | Thursday         |
      | Friday        | AED      | Sunday           |
      | Saturday      | AED      | Sunday           |
      | Sunday        | AED      | Sunday           |

  Scenario Outline: Settle an instructions price
    Given an instruction with <fxRate> fx rate, <pricePerUnit> pricePerUnit and a <unit> units
    When the instruction is settled
    Then the new settlement price will be <expectedPrice>
    Examples:
      | fxRate | pricePerUnit | unit | expectedPrice |
      | 0.5    | 0.5          | 400  | 100           |
      | 0      | 0.5          | 400  | 0             |
      | 0.5    | 0            | 400  | 0             |
      | 0.5    | 0.5          | 0    | 0             |
