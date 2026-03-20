package mx.com.endtoend.domain.articles.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class CustomArticleFactory {

	private final static Logger LOG = LoggerFactory.getLogger(CustomArticleFactory.class);

	public CustomArticleInterface getImplementation(String factoryName) {

		try {

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);

			switch (value) {

			case CUS_ART_ONE:
				LOG.info("RETURN CUSTOM-ARTICLE-METHOD-ONE");
				return new CustomArticleMethodOne();

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {

			LOG.error("ERROR GETTING METHOD TYPE FOR CUSTOM-ARTICLE-SERCHE MODULE");
			return null;
		}
	}
}