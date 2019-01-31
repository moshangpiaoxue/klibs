package mo.klib.modle.datum.design.factory.factory.factory.impl;


import mo.klib.modle.datum.design.factory.factory.factory.IMapFactory;
import mo.klib.modle.datum.design.factory.factory.map.IMapView;
import mo.klib.modle.datum.design.factory.factory.map.impl.BaiduMapView;

public class BaiduMapFactory implements IMapFactory {

	@Override
	public IMapView getMapView() {
		return new BaiduMapView();
	}

}
