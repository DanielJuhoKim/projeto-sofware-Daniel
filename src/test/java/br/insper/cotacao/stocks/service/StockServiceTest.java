package br.insper.cotacao.stocks.service;

import br.insper.cotacao.stocks.dto.StockDTO;
import br.insper.cotacao.stocks.exception.StockNotFoundException;
import br.insper.cotacao.stocks.model.Stock;
import br.insper.cotacao.stocks.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks // Objeto a ser testado
    private StockService stockService;

    @Mock // Uma cópia falsa de algo que o objeto a ser testado precisa para funcionar
    private StockRepository stockRepository;

    @Test
    public void test_listAllShouldReturnListEmpty() {

        // mock
        Mockito.when(stockRepository.findAll()).thenReturn(new ArrayList<>());

        // chamada funcao
        List<StockDTO> resp = stockService.listAll();

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertTrue(resp.isEmpty());

    }

    @Test
    public void test_listAllShouldReturnListWithTwoElements() {

        Stock stock = new Stock();
        stock.setName("Petrobras");
        stock.setTicker("PETR");

        // mock
        Mockito.when(stockRepository.findAll()).thenReturn(List.of(stock));

        // chamada funcao
        List<StockDTO> resp = stockService.listAll();

        // verificacoes
        Assertions.assertNotNull(resp);
        Assertions.assertFalse(resp.isEmpty());
        Assertions.assertEquals(1, resp.size());
        Assertions.assertEquals("PETR", resp.get(0).ticker());
    }


    @Test
    public void test_deleteStockShouldThrowStockNotFound() {

        Mockito.when(stockRepository.existsById(1))
                .thenReturn(false);

        Assertions.assertThrows(StockNotFoundException.class
                , () -> stockService.delete(1));

    }

}
