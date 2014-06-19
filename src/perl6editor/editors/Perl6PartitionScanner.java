package perl6editor.editors;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class Perl6PartitionScanner extends RuleBasedPartitionScanner {
	public final static String COMMENT = "__comment";
	public final static String KEYWORD = "__keyword";

	public Perl6PartitionScanner() {

		IToken comment = new Token(COMMENT);
		IToken keyword = new Token(KEYWORD);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new EndOfLineRule("#", comment);
		rules[1] = new SingleLineRule("my", " ", keyword);

		setPredicateRules(rules);
	}
}
