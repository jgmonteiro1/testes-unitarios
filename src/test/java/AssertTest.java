import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {

    @Test
    public void test(){
        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals("Erro de comparação na linha 11",1, 2);
        Assert.assertEquals(0.51234, 0.512, 0.001);

        Usuario u1 = new Usuario("Joao");
        Usuario u2 = new Usuario("Joao");
        Usuario u3 = null;
        Assert.assertEquals(u1.getNome(), u2.getNome());

        Assert.assertSame(u1, u1);

        //Comparando se o objeto é nulo
        Assert.assertTrue( u3 == null);
        Assert.assertNull(u3);


    }

}
