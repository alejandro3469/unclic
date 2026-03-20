package mx.com.endtoend.domain.articles.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;

public class ArticleFactory {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleFactory.class);

	public ArticleInterface getImplementation(String factoryName, DebugServicePort debugServicePort) {

		try {

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);

			switch (value) {

			case ART_SERCH_ONE:
				LOG.info("RETURN ARTICLE-METHOD-ONE");
				return new ArticleMethodOne(debugServicePort);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {

			LOG.error("ERROR GETTING METHOD TYPE FOR ARTICLE-SERCHE MODULE");
			return null;
		}

	}

}
