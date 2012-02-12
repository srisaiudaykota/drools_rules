package org.drools_rules.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools_rules.drools.interfaces.StatefulSessionBuilder;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class MySessionBuilder implements StatefulSessionBuilder {
	public StatefulKnowledgeSession createStatefulKnowledgeSession()
			throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		kbuilder.add(ResourceFactory.newClassPathResource("test/aTest.drl"), ResourceType.DRL);
		
		if (kbuilder.hasErrors()) {
			throw new Exception("Error in Knowledge Builder: "+kbuilder.getErrors());
		}
		
		KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
		kBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		
		return kBase.newStatefulKnowledgeSession();
	}
}
