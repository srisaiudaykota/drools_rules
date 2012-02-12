package org.drools_rules.drools.interfaces;

import org.drools.runtime.StatefulKnowledgeSession;

/**
 * @author woudlgo84@gmail.com
 *
 */
public interface StatefulSessionBuilder {
	public StatefulKnowledgeSession createStatefulKnowledgeSession() throws Exception;
}
