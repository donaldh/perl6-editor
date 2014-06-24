package perl6editor.handlers;

import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.perl6.script.Perl6ScriptEngine;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class EvalHandler extends AbstractHandler {

    public static final String CONSOLE_NAME = "Perl 6 Output";

    protected Perl6ScriptEngine perl6;

    public EvalHandler() {
        try {
            perl6 = new Perl6ScriptEngine();
        } catch (Exception e) {

        }
    }

    /**
     * the command has been executed, so extract extract the needed information
     * from the application context.
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

        IWorkbenchPage page = window.getActivePage();
        IEditorPart part = page.getActiveEditor();
        if (!(part instanceof AbstractTextEditor)) {
            return null;
        }

        ITextEditor textEditor = (ITextEditor) part;
        IDocument doc = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
        String script = doc.get();

        MessageConsole console = activateConsole(page, CONSOLE_NAME);
        Writer writer = new OutputStreamWriter(console.newMessageStream());

        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);
        context.setErrorWriter(writer);

        try {
            perl6.eval(script, context);
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private MessageConsole activateConsole(IWorkbenchPage page, String name) {
        MessageConsole console = findConsole(name);
        try {
            String id = IConsoleConstants.ID_CONSOLE_VIEW;
            IConsoleView view = (IConsoleView) page.showView(id);
            view.display(console);
        } catch (PartInitException e) {

        }
        return console;
    }

    private MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++)
            if (name.equals(existing[i].getName()))
                return (MessageConsole) existing[i];
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }
}
