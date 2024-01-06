package cn.jarkata.dubbo.ext.facade;

import org.junit.Assert;
import org.junit.Test;


public class GenericFacadeTest {

    @Test
    public void testInvoke() {
        GenericFacade genericFacade = new GenericFacade();
        Assert.assertNotNull(genericFacade);
    }
}