package org.drools_rules.drools.interfaces;

import java.util.List;

/**
 * @author woudlgo84@gmail.com
 *
 */
public interface ICommandRule {
	public List<Object> execute(List<Object> inObjects) throws Exception;
	public Object execute(Object inObj) throws Exception;
}
