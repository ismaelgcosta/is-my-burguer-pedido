Feature: Pagamento de Pedido

  Scenario: Pagar pedido com sucesso
    Given que o pedido está disponível
    And o pagamento é autorizado
    When eu tento pagar o pedido
    Then o QR code do pagamento deve ser retornado
    And o status do pedido deve ser alterado para "AGUARDANDO_PAGAMENTO"

  Scenario: Pagar pedido com pagamento não autorizado
    Given que o pedido está disponível
    And o pagamento não é autorizado
    When eu tento pagar o pedido
    Then uma exceção de negócio deve ser lançada
    And o status do pedido deve ser alterado para "AGUARDANDO_PAGAMENTO"

  Scenario: Pagar pedido com falha na consulta de pagamento
    Given que o pedido está disponível
    And a consulta de pagamento falha
    When eu tento pagar o pedido
    Then uma exceção de negócio deve ser lançada
    And o status do pedido deve ser alterado para "AGUARDANDO_PAGAMENTO"
