package org.drools_rules.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.jboss.logging.Logger;

/**
 * @author woudlgo84@gmail.com
 *
 */
public class CommandUtils {
	private static transient Logger logger = Logger.getLogger(CommandUtils.class);

	public static List<Object> obtainObjectsFromWorkingMemoryToList (StatefulKnowledgeSession ksession){
		Collection<? extends WorkingMemoryEntryPoint> workingMemoryEntryPoints = ksession.getWorkingMemoryEntryPoints();

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving Facts from the Working Memory "+ksession+"...");
		}
		ArrayList<Object> res = new ArrayList<Object>();

		for (Iterator<? extends WorkingMemoryEntryPoint> iterator = workingMemoryEntryPoints
				.iterator(); iterator.hasNext();) {
			WorkingMemoryEntryPoint workingMemoryEntryPoint = iterator
					.next();
			if (logger.isDebugEnabled()) {
				logger.debug(workingMemoryEntryPoint+" retrieved.");
			}
			Collection<FactHandle> factHandles = ksession.getFactHandles();
			for (Iterator<FactHandle> iterator2 = factHandles.iterator(); iterator2.hasNext();) {
				FactHandle factHandle = iterator2.next();
				Object o = workingMemoryEntryPoint.getObject(factHandle);
				
				if (logger.isDebugEnabled()) {
					logger.debug(factHandle+" retrieved.");
				}
				
				if (o != null) {
					res.add(o);
					if (logger.isDebugEnabled()) {
						logger.debug(o+" added to returning list.");
					}
				}
			}
		}

		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> obtainObjectsFromWorkingMemoryToMap (StatefulKnowledgeSession ksession){
		Collection<? extends WorkingMemoryEntryPoint> workingMemoryEntryPoints = ksession.getWorkingMemoryEntryPoints();

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving Facts from the Working Memory "+ksession+"...");
		}
		HashMap<String, Object> ret = new HashMap<String, Object>();

		for (Iterator<? extends WorkingMemoryEntryPoint> iterator = workingMemoryEntryPoints.iterator(); iterator.hasNext();) {
			WorkingMemoryEntryPoint workingMemoryEntryPoint = iterator.next();
			if (logger.isDebugEnabled()) {
				logger.debug(workingMemoryEntryPoint+" retrieved.");
			}
			Collection<FactHandle> factHandles = ksession.getFactHandles();
			for (Iterator<FactHandle> iterator2 = factHandles.iterator(); iterator2.hasNext();) {
				FactHandle factHandle = iterator2.next();
				Object o = workingMemoryEntryPoint.getObject(factHandle);
				
				if (logger.isDebugEnabled()) {
					logger.debug(factHandle+" retrieved.");
				}
				
				if (o != null && o instanceof HashMap<?, ?>) {
					ret = ((HashMap<String, Object>) o);
					if (logger.isDebugEnabled()) {
						logger.debug(o+" returned.");
					}
					break;
				}
			}
		}
		return ret;
	}
}
