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

}
