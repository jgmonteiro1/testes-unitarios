package br.ce.wcaquino.servicos;

import exceptions.DivisaoPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private  Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.somar(a, b);
        //verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.subtrair(a,b);
        //resultado
        Assert.assertEquals(2, resultado);
    }

    @Test
    public void deveMultiplicarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;
        //acao
        int resultado = calc.multiplicar(a,b);
        //resultado
        Assert.assertEquals(15, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws DivisaoPorZeroException {
        //cenario
        int a = 10;
        int b = 2;
        //acao
        int resultado = calc.divide(a,b);
        //resultado
        Assert.assertEquals(5, resultado);
    }

    @Test(expected = DivisaoPorZeroException.class)
    public void lancaExceptionDividindoPorZero() throws DivisaoPorZeroException {
        int a = 10;
        int b = 0;

        calc.divide(a,b);
    }

}
