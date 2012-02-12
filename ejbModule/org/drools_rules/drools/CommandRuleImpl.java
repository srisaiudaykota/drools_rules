package org.drools_rules.drools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.AgendaFilter;
import org.drools_rules.drools.interfaces.ICommandRule;
import org.drools_rules.drools.interfaces.StatefulSessionBuilder;
import org.jboss.logging.Logger;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class CommandRuleImpl implements ICommandRule {
	private transient static Logger logger = Logger.getLogger(CommandRuleImpl.class);
	private Logger ruleLogger;
	private StatefulSessionBuilder statefulSessionBuilder;
	private AgendaFilter agendaFilter;
	private Map<String, Object> globalVars;

	private String agendaGroupToFocus, activationGroupToClear,
	ruleFlowGroupToClear;

	public CommandRuleImpl() {
		if (logger.isInfoEnabled()) {
			logger.info("\r\n\r\n-----\r\n\r\nCommand Created.\r\n\r\n-----\r\n\r\n");
		}
	}

	public Object execute(Object inObject) throws Exception {
		if (this.statefulSessionBuilder == null)
			throw new Exception("StatefulSessionBuilder non setted");

		StatefulKnowledgeSession ksession = this.statefulSessionBuilder
				.createStatefulKnowledgeSession();

		if (logger.isDebugEnabled()) {
			logger.debug("Stateful session created.");
		}

		try {

			this.execute(inObject, ksession);
			return CommandUtils.obtainObjectsFromWorkingMemoryToMap(ksession);

		} finally {
			ksession.dispose();

			if (logger.isDebugEnabled()) {
				logger.debug(ksession + " destroyed");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void execute(Object inObject, StatefulKnowledgeSession ksession)
			throws Exception {
		if (inObject instanceof HashMap<?, ?>) {
			HashMap<String, Object> inObj = ((HashMap<String, Object>) inObject);
			if (ruleLogger == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("No log set.");
				}
			} else {
				ksession.setGlobal("logger", ruleLogger);
				if (logger.isDebugEnabled()) {
					logger.debug("Using logger created in spring context for rules logging.");
				}
			}

			if (globalVars != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Initializing global variables...");
				}
				for (String aKey : globalVars.keySet()) {
					ksession.setGlobal(aKey, globalVars.get(aKey));
					if (logger.isDebugEnabled()) {
						logger.debug(aKey+ " inserted in Working Memory as global variabile.");
					}
				}
			}

			if (agendaGroupToFocus != null) {
				ksession.getAgenda().getAgendaGroup(agendaGroupToFocus).setFocus();
				if (logger.isDebugEnabled()) {
					logger.debug("Focus on " + agendaGroupToFocus + " AgendaGroup setted.");
				}
			}

			if (activationGroupToClear != null) {
				ksession.getAgenda().getActivationGroup(activationGroupToClear).clear();
				if (logger.isDebugEnabled()) {
					logger.debug("Clear on " + agendaGroupToFocus + " for ActivationGroup setted.");
				}
			}

			if (ruleFlowGroupToClear != null) {
				ksession.getAgenda().getRuleFlowGroup(ruleFlowGroupToClear).clear();
				if (logger.isDebugEnabled()) {
					logger.debug("Clear on " + ruleFlowGroupToClear + " for RuleFlowGroup setted.");
				}
			}

			if (inObj != null) {
				ksession.insert(inObj);
				if (logger.isDebugEnabled()) {
					logger.debug(inObj + " inserted in Working Memory.");
				}
			}

			if (this.agendaFilter == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Evaluation of all rules in progress...");
				}

				ksession.fireAllRules();

				if (logger.isDebugEnabled()) {
					logger.debug("All rules are evaluated.");
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Evaluation of " + this.agendaFilter + " rules in progress...");
				}

				ksession.fireAllRules(this.agendaFilter);

				if (logger.isDebugEnabled()) {
					logger.debug(this.agendaFilter + " rules are evaluated.");
				}
			}
		} else {
			IllegalArgumentException exc = new IllegalArgumentException("Input parameter not equals to HashMap<?, ?>.");
			throw exc;
		}
	}

	public List<Object> execute(List<Object> inObjects) throws Exception {
		if (this.statefulSessionBuilder == null)
			throw new Exception("StatefulSessionBuilder non setted");

		StatefulKnowledgeSession ksession = this.statefulSessionBuilder
				.createStatefulKnowledgeSession();

		if (logger.isDebugEnabled()) {
			logger.debug("Stateful session created.");
		}

		try {

			this.execute(inObjects, ksession);

			return CommandUtils.obtainObjectsFromWorkingMemoryToList(ksession);

		} finally {
			ksession.dispose();

			if (logger.isDebugEnabled()) {
				logger.debug(ksession + " destroyed.");
			}
		}
	}

	public void execute(List<Object> inObjects,
			StatefulKnowledgeSession ksession) throws Exception {

		if (ruleLogger == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("No log set.");
			}
		} else {
			ksession.setGlobal("logger", ruleLogger);
			if (logger.isDebugEnabled()) {
				logger.debug("Using logger created in spring context for rules logging.");
			}
		}

		if (globalVars != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Initializing global variables...");
			}
			for (String aKey : globalVars.keySet()) {
				ksession.setGlobal(aKey, globalVars.get(aKey));
				if (logger.isDebugEnabled()) {
					logger.debug(aKey+ " inserted in Working Memory as global variabile.");
				}
			}
		}

		if (agendaGroupToFocus != null) {
			ksession.getAgenda().getAgendaGroup(agendaGroupToFocus).setFocus();
			if (logger.isDebugEnabled()) {
				logger.debug("Focus on " + agendaGroupToFocus+ " AgendaGroup setted.");
			}
		}

		if (activationGroupToClear != null) {
			ksession.getAgenda().getActivationGroup(activationGroupToClear).clear();
			if (logger.isDebugEnabled()) {
				logger.debug("Clear on " + agendaGroupToFocus+ " for ActivationGroup setted.");
			}
		}

		if (ruleFlowGroupToClear != null) {
			ksession.getAgenda().getRuleFlowGroup(ruleFlowGroupToClear).clear();
			if (logger.isDebugEnabled()) {
				logger.debug("Clear on " + ruleFlowGroupToClear + " for RuleFlowGroup setted.");
			}
		}

		if (inObjects != null) {
			for (Iterator<Object> iterator = inObjects.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				ksession.insert(object);
				if (logger.isDebugEnabled()) {
					logger.debug(object + " inserted in Working Memory.");
				}
			}
		}

		if (this.agendaFilter == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Evaluation of all rules in progress...");
			}

			ksession.fireAllRules();

			if (logger.isDebugEnabled()) {
				logger.debug("All rules are evaluated.");
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Evaluation of " + this.agendaFilter+ " rules in progress...");
			}

			ksession.fireAllRules(this.agendaFilter);

			if (logger.isDebugEnabled()) {
				logger.debug(this.agendaFilter + " rules are evaluated.");
			}
		}

	}

	public AgendaFilter getAgendaFilter() {
		return agendaFilter;
	}

	public void setAgendaFilter(AgendaFilter agendaFilter) {
		this.agendaFilter = agendaFilter;
	}

	public StatefulSessionBuilder getStatefulSessionBuilder() {
		return statefulSessionBuilder;
	}

	public void setStatefulSessionBuilder(
			StatefulSessionBuilder statefulSessionBuilder) {
		this.statefulSessionBuilder = statefulSessionBuilder;
	}

	public Logger getRuleLogger() {
		return ruleLogger;
	}

	public void setRuleLogger(Logger ruleLogger) {
		this.ruleLogger = ruleLogger;
	}

	public Map<String, Object> getGlobalVars() {
		return globalVars;
	}

	public void setGlobalVars(Map<String, Object> globalVars) {
		this.globalVars = globalVars;
	}

	public String getAgendaGroupToFocus() {
		return agendaGroupToFocus;
	}

	public String getActivationGroupToClear() {
		return activationGroupToClear;
	}

	public String getRuleFlowGroupToClear() {
		return ruleFlowGroupToClear;
	}

	public void setAgendaGroupToFocus(String agendaGroupToFocus) {
		this.agendaGroupToFocus = agendaGroupToFocus;
	}

	public void setActivationGroupToClear(String activationGroupToClear) {
		this.activationGroupToClear = activationGroupToClear;
	}

	public void setRuleFlowGroupToClear(String ruleFlowGroupToClear) {
		this.ruleFlowGroupToClear = ruleFlowGroupToClear;
	}
}
