package cn.jarkata.dubbo.ext.facade;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class DubboGenericFacadeTest {

    @Test(expected = NullPointerException.class)
    public void testInvoke() {
        DubboGenericFacade dubboGenericFacade = new DubboGenericFacade();
        Object invoked = dubboGenericFacade.invokeMethod(null, new ArrayList<>());
        Assert.assertNotNull(invoked);
    }

    @Test
    public void invokeMethod() {
    }
}