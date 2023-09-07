package pages;

import misc.Setup;
import pages.FluentGoogleResultPage.GRPFluentInterface;

public class FluentGoogleSearchPage extends GoogleSearchPage {

	private GSPFluentInterface gspfi;

	public class GSPFluentInterface {
		private FluentGoogleSearchPage gsp;

		public GSPFluentInterface(FluentGoogleSearchPage googleSearchPage) {
			gsp = googleSearchPage;
		}

		public GSPFluentInterface setQuery(String queryString) {
			gsp.setQuery(queryString);
			return this;
		}

		public GSPFluentInterface assertQuery(String queryString) {
			gsp.assertQuery(queryString);
			return this;
		}

		public GRPFluentInterface submitQuery() {
			gsp.submitQuery();
			return (new FluentGoogleResultPage(gsp.testContainer)).withFluent();
		}
		
		public GRPFluentInterface searchFor(String queryString) {
			gsp.searchFor(queryString);
			return (new FluentGoogleResultPage(gsp.testContainer)).withFluent();
		}
	}

	public FluentGoogleSearchPage(Setup testContainer) {
		super(testContainer);

		gspfi = new GSPFluentInterface(this);
	}

	public GSPFluentInterface withFluent() {
		this.get();

		return gspfi;
	}

}