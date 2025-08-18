package br.insper.cotacao.stocks.controller;

import br.insper.cotacao.stocks.dto.StockDTO;
import br.insper.cotacao.stocks.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {
    @InjectMocks
    public StockController stockController;

    @Mock
    public StockService stockService;

    public MockMvc mockMvc; // Usado para testar as rotas do servidor

    @BeforeEach
    public void setup() { // Vai configurar o mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    public void test_listAllShouldReturnOneStock() throws Exception {
        StockDTO stockDTO = new StockDTO( // Objeto StockDTO q é a classe sendo usada pela rota
                1,
                "PETR",
                "Petrobras",
                (float) 100,
                LocalDate.now(),
                LocalDate.now(),
                null
            );

        // Se for usado o listAll do service, deve retornar o objeto q criamos anteriormente
        Mockito.when(stockService.listAll()).thenReturn(List.of(stockDTO));

        // O perform chama a rota que queremos testar
        mockMvc.perform(
                MockMvcRequestBuilders.get("/stocks")
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())// Usando o andExpect, verificamos se a rota está devolvendo o que queremos, nesse caso com o isOk = status 200 e também é possível mudar para casos onde queremos status 400 = isBadRequest
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticker").value("PETR")); // Confere se o primeiro elemento do json tem valor "PETR" na chave "ticker" --> "ticker": "PETR"
    }
}
