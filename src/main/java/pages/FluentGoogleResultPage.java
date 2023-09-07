package pages;

import misc.Setup;

public class FluentGoogleResultPage extends GoogleResultPage {

	private GRPFluentInterface grpfi;

	public class GRPFluentInterface {
		private FluentGoogleResultPage grp;

		public GRPFluentInterface(FluentGoogleResultPage googleResultPage) {
			grp = googleResultPage;
		}

		public GRPFluentInterface assertResultPageQuery(String queryString) {
			grp.assertResultPageQuery(queryString);
			return this;
		}
	}

	public FluentGoogleResultPage(Setup testContainer) {
		super(testContainer);

		grpfi = new GRPFluentInterface(this);
	}

	public GRPFluentInterface withFluent() {
		this.get();

		return grpfi;
	}

}
