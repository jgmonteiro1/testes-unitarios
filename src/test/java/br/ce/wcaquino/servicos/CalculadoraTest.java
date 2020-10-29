package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Usuario;
import exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculadoraTest {

    private  Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValoresTest(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.somar(a, b);
        //verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValoresTest(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.subtrair(a,b);
        //resultado
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void deveMultiplicarDoisValoresTest(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.multiplicar(a,b);
        //resultado
        Assert.assertEquals(15, resultado);
    }

    @Test
    public void deveDividirDoisValoresTest() throws DivisaoPorZeroException {
        //cenario
        int a = 10;
        int b = 2;
        //acao
        int resultado = calc.divide(a,b);
        //resultado
        Assert.assertEquals(5, resultado);
    }

    @Test(expected = DivisaoPorZeroException.class)
    public void lancaExceptionDividindoPorZeroTest() throws DivisaoPorZeroException {
        int a = 10;
        int b = 0;

        calc.divide(a,b);
    }


}
