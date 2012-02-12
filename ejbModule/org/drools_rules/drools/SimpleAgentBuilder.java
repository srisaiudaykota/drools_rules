package org.drools_rules.drools;

import java.util.Iterator;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.container.spring.beans.DroolsResourceAdapter;
import org.drools_rules.drools.interfaces.AgentBuilder;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class SimpleAgentBuilder implements AgentBuilder {
	private List<DroolsResourceAdapter> resources;
	private KnowledgeBuilderConfiguration knowledgeBuilderConfiguration;
	private KnowledgeAgentConfiguration knowledgeAgentConfiguration;

	public KnowledgeAgent create() throws Exception {
		KnowledgeBuilder kbuilder = null;
		if(this.knowledgeBuilderConfiguration == null) {
			kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		}
		else {
			kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(this.knowledgeBuilderConfiguration);
		}

		for (Iterator<DroolsResourceAdapter> iterator = this.resources.iterator(); iterator.hasNext();) {
			DroolsResourceAdapter resource =  iterator.next();
			kbuilder.add(resource.getDroolsResource(), resource.getResourceType());

			if(kbuilder.hasErrors()) {
				throw new IllegalArgumentException("Could not parse resource:"+resource+" "+ kbuilder.getErrors());
			}
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		String agentName = System.currentTimeMillis()+"";//TODO Verify what happen if 2 agent have the same name...

		KnowledgeAgent result = null;
		if ( this.knowledgeAgentConfiguration == null){
			result =  KnowledgeAgentFactory.newKnowledgeAgent(agentName, kbase );
		} else {
			result =  KnowledgeAgentFactory.newKnowledgeAgent( agentName, kbase, this.knowledgeAgentConfiguration );	
		}

		return result;
	}

	public List<DroolsResourceAdapter> getResources() {
		return resources;
	}

	public void setResources(List<DroolsResourceAdapter> resources) {
		this.resources = resources;
	}

	public KnowledgeBuilderConfiguration getKnowledgeBuilderConfiguration() {
		return knowledgeBuilderConfiguration;
	}

	public void setKnowledgeBuilderConfiguration(KnowledgeBuilderConfiguration knowledgeBuilderConfiguration) {
		this.knowledgeBuilderConfiguration = knowledgeBuilderConfiguration;
	}

	public KnowledgeAgentConfiguration getKnowledgeAgentConfiguration() {
		return knowledgeAgentConfiguration;
	}

	public void setKnowledgeAgentConfiguration(
			KnowledgeAgentConfiguration knowledgeAgentConfiguration) {
		this.knowledgeAgentConfiguration = knowledgeAgentConfiguration;
	}		
}
