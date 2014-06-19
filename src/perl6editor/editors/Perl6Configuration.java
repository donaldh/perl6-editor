package perl6editor.editors;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class Perl6Configuration extends SourceViewerConfiguration {

	public Perl6Configuration() {
	}
	
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			Perl6PartitionScanner.COMMENT,
			Perl6PartitionScanner.KEYWORD };
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		NonRuleBasedDamagerRepairer kw =
			new NonRuleBasedDamagerRepairer(new TextAttribute(ColorConstants.blue));
		reconciler.setDamager(kw, Perl6PartitionScanner.KEYWORD);
		reconciler.setRepairer(kw, Perl6PartitionScanner.KEYWORD);

		NonRuleBasedDamagerRepairer comm =
			new NonRuleBasedDamagerRepairer(new TextAttribute(ColorConstants.red));
		reconciler.setDamager(comm, Perl6PartitionScanner.COMMENT);
		reconciler.setRepairer(comm, Perl6PartitionScanner.COMMENT);

		return reconciler;
	}

}