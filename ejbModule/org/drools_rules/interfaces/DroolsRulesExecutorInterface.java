package org.drools_rules.interfaces;

import java.util.List;
import javax.ejb.Local;

/**
 * @author would84@gmail.com
 *
 */
@Local
public interface DroolsRulesExecutorInterface {
	public List<Object> execute(String aRulePkg,List<Object> inObjects) throws Exception;
	public Object execute(String aRulePkg, Object inObj) throws Exception;
}
