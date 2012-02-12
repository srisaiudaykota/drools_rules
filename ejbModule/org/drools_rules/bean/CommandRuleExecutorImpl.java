package org.drools_rules.bean;

import java.util.List;

import org.drools_rules.drools.interfaces.ICommandRule;
import org.drools_rules.drools.interfaces.IRuleEngineInitiator;
import org.drools_rules.interfaces.DroolsRulesExecutorInterface;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class CommandRuleExecutorImpl implements DroolsRulesExecutorInterface {

	public final static String RULE_CONFIGURATION = "org/drools_rules/ruleConfiguration.xml";
	public final static String BEAN_FACTORY = "org.drools_rules.commands";
	private IRuleEngineInitiator ruleEngineInitiator;
	private BeanFactoryReference bfr = null;
	private transient static Logger logger = Logger.getLogger(CommandRuleExecutorImpl.class);

	public CommandRuleExecutorImpl() {
		super();
		BeanFactoryLocator sbfl = SingletonBeanFactoryLocator.getInstance(RULE_CONFIGURATION);
		bfr = sbfl.useBeanFactory(BEAN_FACTORY);

	}

	/**
	 * @throws Exception
	 */
	public void initRuleEngine() throws Exception {
		if (this.ruleEngineInitiator != null) {
			this.ruleEngineInitiator.init();
		} else {
			if(logger.isEnabled(Level.WARN)) {
				logger.log(Level.WARN, "IRuleEngineInitiator not setted, no init executed");
			}
		}
	}

	public List<Object> execute(String rulePackageName,
			List<Object> inObjects) throws Exception {
		try {
			ICommandRule iCommandRule =(ICommandRule) this.bfr.getFactory().getBean(rulePackageName);
			if (iCommandRule == null) {
				throw new IllegalArgumentException("rulePackageName not associated command found: "+ rulePackageName);
			}
			
			if(logger.isEnabled(Level.DEBUG)) {
				logger.log(Level.DEBUG, "Invoking the Rule Manager...");
			}

			return iCommandRule.execute(inObjects);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	public Object execute(String rulePkgName, Object inObj) throws Exception {
		try {
			ICommandRule iCommandRule = (ICommandRule) this.bfr.getFactory().getBean(rulePkgName);
			if (iCommandRule == null) {
				throw new IllegalArgumentException("rulePackageName not associated command found: "+ rulePkgName);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Invoking the Rule Manager...");
			}

			return iCommandRule.execute(inObj);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	public IRuleEngineInitiator getRuleEngineInitiator() {
		return ruleEngineInitiator;
	}

	public void setRuleEngineInitiator(IRuleEngineInitiator ruleEngineInitiator) {
		this.ruleEngineInitiator = ruleEngineInitiator;
	}
}
