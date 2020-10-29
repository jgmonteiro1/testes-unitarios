package br.ce.wcaquino.servicos;

import exceptions.DivisaoPorZeroException;

public class Calculadora {
    public int somar(int a, int b) {
        return a+b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public int multiplicar(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) throws DivisaoPorZeroException {
        if(b == 0){
            throw new DivisaoPorZeroException();
        }
        return a / b;
    }
}
