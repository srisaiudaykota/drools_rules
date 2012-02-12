package org.drools_rules.drools;

import org.drools.agent.KnowledgeAgent;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools_rules.drools.interfaces.AgentBuilder;
import org.drools_rules.drools.interfaces.StatefulSessionBuilder;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class AgentStatefulSessionBuilderImpl implements StatefulSessionBuilder {
	private transient static Logger logger = Logger.getLogger(AgentStatefulSessionBuilderImpl.class);
	private AgentBuilder agentBuilder;
	private KnowledgeAgent knowledgeAgent;

	private KnowledgeAgent createKnowledgeAgent() throws Exception {
		if(knowledgeAgent == null){
			if (logger.isEnabled(Level.DEBUG)) {
				logger.debug(Thread.currentThread().getName()+" KnowledgeAgent is null.");
			}
			
			synchronized (this) {
				if(knowledgeAgent == null){
					if (logger.isDebugEnabled()) {
						logger.debug(Thread.currentThread().getName()+" KnowledgeAgent still null.");
					}
					knowledgeAgent = agentBuilder.create();
				}
			}
		}
		return knowledgeAgent;
	}

	public void setAgentBuilder(AgentBuilder agentBuilder) {
		this.agentBuilder = agentBuilder;
		this.knowledgeAgent = null;

		if(this.agentBuilder != null){
			if (logger.isDebugEnabled()) {
				logger.debug("AgentBuilder is null.");
			}

			try {
				this.knowledgeAgent = this.createKnowledgeAgent();
			} catch (Exception e) {
				if (logger.isEnabled(Level.FATAL)) {
					logger.fatal("Error creating KnowledgeAgent", e);					
				}
			}
		}
	}

	public StatefulKnowledgeSession createStatefulKnowledgeSession() throws Exception {
		if(this.knowledgeAgent == null) throw new Exception("Agent non setted");
		if (logger.isDebugEnabled()) {
			logger.debug("Creating the session by KnowledgeAgent...");
		}

		StatefulKnowledgeSession ksession = this.knowledgeAgent.getKnowledgeBase().newStatefulKnowledgeSession();

		if (logger.isDebugEnabled()) {
			logger.debug("Session by KnowledgeAgent created.");
		}
		return ksession;
	}
	
	public AgentBuilder getAgentBuilder() {
		return agentBuilder;
	}

	public KnowledgeAgent getKnowledgeAgent() {
		return knowledgeAgent;
	}

	public void setKnowledgeAgent(KnowledgeAgent knowledgeAgent) {
		this.knowledgeAgent = knowledgeAgent;
	}
}
