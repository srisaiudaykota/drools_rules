package org.drools_rules.drools;

import java.util.Iterator;
import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools_rules.drools.interfaces.ICommandRule;
import org.drools_rules.drools.interfaces.StatefulSessionBuilder;
import org.jboss.logging.Logger;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class CommandRuleChainImpl implements ICommandRule {
	private transient static Logger logger = Logger.getLogger(CommandRuleChainImpl.class);
	private StatefulSessionBuilder statefulSessionBuilder;
	private List<CommandRuleImpl> commands;
	
	public CommandRuleChainImpl() {
		if (logger.isInfoEnabled()) {
			logger.info("\r\n\r\n-----\r\n\r\nCommand Chain Created.\r\n\r\n-----\r\n\r\n");			
		}
	}
	
	/**
	 * @param inObjects
	 * @return
	 * @throws Exception
	 */
	protected List<Object> executeNoSession(List<Object> inObjects) throws Exception {
		List<Object> res = inObjects;
		
		for (Iterator<CommandRuleImpl> iterator = this.commands.iterator(); iterator.hasNext();) {
			CommandRuleImpl cmd =  iterator.next();
			res = cmd.execute(res);
		}
		
		return res;
	}
	
	public List<Object> execute(List<Object> inObjects) throws Exception {
		if (this.statefulSessionBuilder == null){
			return this.executeNoSession(inObjects);
		}

		StatefulKnowledgeSession ksession = this.statefulSessionBuilder.createStatefulKnowledgeSession();

		if (logger.isDebugEnabled()) {
			logger.debug("Stateful session created.");
		}

		try {
			boolean first = true;
			for (Iterator<CommandRuleImpl> iterator = this.commands.iterator(); iterator.hasNext();) {
				CommandRuleImpl cmd =  iterator.next();

				if (logger.isDebugEnabled()) {
					logger.debug("Executing command "+cmd);
				}
				
				if (first) {
					cmd.execute(inObjects,ksession);
					first = false;
				} else {
					cmd.execute(null, ksession);
				}
			}

			return CommandUtils.obtainObjectsFromWorkingMemoryToList(ksession);
		} finally {
			ksession.dispose();
			
			if (logger.isDebugEnabled()) {
				logger.debug(ksession+" destroyed");
			}
		}
	}

	protected Object executeNoSession(Object inObject) throws Exception {
		Object res = inObject;
		
		for (Iterator<CommandRuleImpl> iterator = this.commands.iterator(); iterator.hasNext();) {
			CommandRuleImpl cmd =  iterator.next();
			res = cmd.execute(res);
		}
		
		return res;
	}
	
	public Object execute(Object inObj) throws Exception {

		if (this.statefulSessionBuilder == null){
			return this.executeNoSession(inObj);
		}

		
		StatefulKnowledgeSession ksession = this.statefulSessionBuilder.createStatefulKnowledgeSession();

		if (logger.isDebugEnabled()) {
			logger.debug("Stateful session created.");
		}

		try {
			boolean first = true;
			for (Iterator<CommandRuleImpl> iterator = this.commands.iterator(); iterator.hasNext();) {
				CommandRuleImpl cmd =  iterator.next();

				if (logger.isDebugEnabled()) {
					logger.debug("Executing command "+cmd);
				}
				
				if (first) {
					cmd.execute(inObj,ksession);
					first = false;
				} else {
					cmd.execute(null, ksession);
				}
			}

			return CommandUtils.obtainObjectsFromWorkingMemoryToMap(ksession);
		} finally {
			ksession.dispose();
			
			if (logger.isDebugEnabled()) {
				logger.debug(ksession+" destroyed");
			}
		}
	}

	public StatefulSessionBuilder getStatefulSessionBuilder() {
		return statefulSessionBuilder;
	}

	public void setStatefulSessionBuilder(
			StatefulSessionBuilder statefulSessionBuilder) {
		this.statefulSessionBuilder = statefulSessionBuilder;
	}

	public List<CommandRuleImpl> getCommands() {
		return commands;
	}

	public void setCommands(List<CommandRuleImpl> commands) {
		this.commands = commands;
	}
}
