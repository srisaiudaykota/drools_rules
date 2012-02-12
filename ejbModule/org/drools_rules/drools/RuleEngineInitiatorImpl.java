package org.drools_rules.drools;

import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools_rules.drools.interfaces.IRuleEngineInitiator;
import org.jboss.logging.Logger;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class RuleEngineInitiatorImpl implements IRuleEngineInitiator {
	private transient static Logger logger = Logger.getLogger(RuleEngineInitiatorImpl.class);
	private ResourceChangeScannerConfiguration resourceChangeScannerConfiguration;
	private static boolean droolsInitialized = false;

	private static synchronized void internalInit(ResourceChangeScannerConfiguration rcsc) throws Exception {
		if(droolsInitialized) {
			if (logger.isInfoEnabled()) {
				logger.info("Drool already initialized. Doing nothing.");				
			}
		} else {
			if(rcsc != null){
				ResourceFactory.getResourceChangeScannerService().configure(rcsc);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Initializing Notifier and Scanner services...");
			}

			ResourceFactory.getResourceChangeNotifierService().start();
			ResourceFactory.getResourceChangeScannerService().start();

			if (logger.isDebugEnabled()) {
				logger.debug("Notifier and Scanner services started.");
			}
		}
	}

	public synchronized void init() throws Exception {
		internalInit(this.resourceChangeScannerConfiguration);
	}

	public ResourceChangeScannerConfiguration getResourceChangeScannerConfiguration() {
		return resourceChangeScannerConfiguration;
	}

	public void setResourceChangeScannerConfiguration(ResourceChangeScannerConfiguration resourceChangeScannerConfiguration) {
		this.resourceChangeScannerConfiguration = resourceChangeScannerConfiguration;
	}
}
