package org.drools_rules.drools.interfaces;

import org.drools.agent.KnowledgeAgent;

/**
 * @author woudlgo84@gmail.com
 *
 */
public interface AgentBuilder {
	public KnowledgeAgent create() throws Exception;
}
