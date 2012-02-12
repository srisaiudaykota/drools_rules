package org.drools_rules.bean;

import java.util.List;
import javax.ejb.Stateless;

import org.drools_rules.interfaces.DroolsRulesExecutorInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author woudlgo84@gmail.com
 *
 * Session Bean implementation class DroolsRulesExecutorInterface
 */
@Stateless
public class DroolsRulesExecutor implements DroolsRulesExecutorInterface {
	@Autowired
	private DroolsRulesExecutorInterface internalIRuleExecutor;

	public void setInternalIRuleExecutor(DroolsRulesExecutorInterface internalIRuleExecutor) {
		this.internalIRuleExecutor = internalIRuleExecutor;
	}

	public List<Object> execute(String arg0, List<Object> arg1)
			throws Exception {
		return this.internalIRuleExecutor.execute(arg0, arg1);
	}

	public Object execute(String aRulePkg, Object inObj)
			throws Exception {
		return this.internalIRuleExecutor.execute(aRulePkg, inObj);
	}
	
	public DroolsRulesExecutorInterface getInternalIRuleExecutor() {
		return internalIRuleExecutor;
	}
}
