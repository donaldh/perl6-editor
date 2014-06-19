package perl6editor.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class Perl6Editor extends TextEditor {

	public Perl6Editor() {
		super();
		setSourceViewerConfiguration(new Perl6Configuration());
		setDocumentProvider(new Perl6DocumentProvider());
	}
	public void dispose() {
		super.dispose();
	}

}
