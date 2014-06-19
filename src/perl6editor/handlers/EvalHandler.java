package perl6editor.handlers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.perl6.script.Perl6ScriptEngine;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class EvalHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public EvalHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		IEditorPart part = window.getActivePage().getActiveEditor();
		if (!(part instanceof AbstractTextEditor)) {
		    return null;
		}
		
		ITextEditor textEditor = (ITextEditor) part;
		IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		String script = doc.get();
		
        Perl6ScriptEngine perl6 = new Perl6ScriptEngine();
		ScriptContext context = new SimpleScriptContext();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(bos);
		context.setWriter(writer);
		context.setErrorWriter(writer);
		
		try {
            perl6.eval(script, context);
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		MessageDialog.openInformation(
				window.getShell(),
				"Perl6-editor",
				bos.toString());
		return null;
	}
}
