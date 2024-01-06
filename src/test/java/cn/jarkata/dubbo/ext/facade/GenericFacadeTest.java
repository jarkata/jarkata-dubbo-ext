package cn.jarkata.dubbo.ext.facade;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class GenericFacadeTest {

    @Test(expected = NullPointerException.class)
    public void testInvoke() {
        GenericFacade genericFacade = new GenericFacade();
        Object invoked = genericFacade.invokeMethod(null, new ArrayList<>());
        Assert.assertNotNull(invoked);
    }

    @Test
    public void invokeMethod() {
    }
}