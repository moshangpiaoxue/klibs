package mo.klib.modle.datum.design.proxy.proxyStatic.impl;


import mo.klib.modle.datum.design.proxy.proxyStatic.IProxyView;

/**
 * @author：mo
 * @data：2018/6/1 0001
 * @功能：谁要干
 */

public class Li implements IProxyView {
    @Override
    public void buyBit() {
        System.out.print("李四要买币");
    }

    @Override
    public void payMoney() {
        System.out.print("李四要花钱");
    }
}
