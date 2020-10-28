package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

public class CalculadoraTest {

    @Test
    public void deveSomarDoisValores(){
        //cenario
        int a = 5;
        int b = 3;
        Calculadora calc = new Calculadora();
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
        Calculadora calc = new Calculadora();
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
        Calculadora calc = new Calculadora();
        //acao
        int resultado = calc.multiplicar(a,b);
        //resultado
        Assert.assertEquals(15, resultado);
    }

    @Test
    public void deveDividirDoisValores(){
        //cenario
        int a = 10;
        int b = 2;
        Calculadora calc = new Calculadora();
        //acao
        int resultado = calc.divide(a,b);
        //resultado
        Assert.assertEquals(5, resultado);
    }

}
