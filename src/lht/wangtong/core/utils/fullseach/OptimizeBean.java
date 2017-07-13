package lht.wangtong.core.utils.fullseach;

public class OptimizeBean {

	private FactoryBean factoryBean = null;
	private int optimizeEfforts = -1;

	public void setOptimizeEfforts(int optimizeEfforts) {
		this.optimizeEfforts = optimizeEfforts;
	}

	public void setFactoryBean(FactoryBean factoryBean) {
		this.factoryBean = factoryBean;
	}

	private FactoryBean getFactoryBean() {
		if (this.factoryBean == null) {
			this.factoryBean = DefaultFactory.getFactory();
		}
		return this.factoryBean;
	}

	public void optimize() {
		this.getFactoryBean().createWriter().optimize(this.optimizeEfforts);
	}
}
