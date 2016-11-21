package edu.casetools.dcase.modelio.menu.io;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.DefaultModuleCommandHandler;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.dcase.extensions.io.MDiagrams2UPPAAL;

public class ExportMDiagrams2UPPAAL extends DefaultModuleCommandHandler {

    /**
     * Constructor.
     */
    public ExportMDiagrams2UPPAAL() {
	super();
    }

    /**
     * @see org.modelio.api.module.commands.DefaultModuleContextualCommand#accept(java.util.List,
     *      org.modelio.api.module.IModule)
     */
    @Override
    public boolean accept(List<MObject> selectedElements, IModule module) {
	// Check that there is only one selected element
	return selectedElements.size() == 1;
    }

    /**
     * @see org.modelio.api.module.commands.DefaultModuleContextualCommand#actionPerformed(java.util.List,
     *      org.modelio.api.module.IModule)
     */

    @Override
    public void actionPerformed(List<MObject> selectedElements, IModule module) {

	ModelElement modelelt = (ModelElement) selectedElements.get(0);

	FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
	dialog.setFilterNames(new String[] { "Text Files", "All Files (*.*)" });
	dialog.setFilterExtensions(new String[] { "*.txt", "*.*" }); // Windows
	// wild
	// cards
	dialog.setFilterPath(System.getProperty("user.home") + "/Desktop"); // Windows
									    // path
	dialog.setFileName("_UPPAAL.xml");
	String fileLocation = dialog.open();

	MDiagrams2UPPAAL translator = new MDiagrams2UPPAAL();

	// if (!modelelt.isStereotyped(Utils.CONTEXT_MODELLER,
	// Utils.CONTEXT_MODEL)) {
	// MessageDialog.openError(null, "Error", "Element is not a Context
	// Model");
	// return;
	// }
	File newFile = new File(fileLocation);

	if (newFile.exists()) {
	    if (!MessageDialog.openConfirm(null, "Confirm Export",
		    "File already exists.\nDo you want to replace it?\n")) {
		return;
	    }
	}

	translator.setModel();
	translator.writeToFile(newFile);
	MessageDialog.openInformation(null, "Model Exported", "Model exported to C-SPARQL at:\n" + fileLocation);

    }

}