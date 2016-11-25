package edu.casetools.dcase.extensions.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import edu.casetools.dcase.extensions.io.juppaal.elements.Automaton;
import edu.casetools.dcase.extensions.io.juppaal.elements.Declaration;
import edu.casetools.dcase.extensions.io.juppaal.elements.NTA;
import edu.casetools.dcase.extensions.io.juppaal.elements.SystemDeclaration;
import edu.casetools.dcase.extensions.io.mnta.DeclarationsWriter;
import edu.casetools.dcase.extensions.io.mnta.SystemDeclarationWriter;
import edu.casetools.dcase.extensions.io.mnta.TemplateGenerationManager;
import edu.casetools.dcase.extensions.io.mnta.data.MData;

public class MD2UPPAAL {

    private static final Logger LOGGER = Logger.getLogger(MD2UPPAAL.class.getName());
    private MData systemData;

    public MD2UPPAAL() {
	systemData = new MData();
    }

    public void translate(String filePath) throws IOException {

	try {
	    Declaration declaration = new Declaration();
	    declaration.add(new DeclarationsWriter(systemData).write());
	    NTA nta = new NTA();
	    nta.setDeclarations(declaration);
	    nta.setTemplates((ArrayList<Automaton>) new TemplateGenerationManager(systemData).generateTemplates());
	    nta.setSystemDeclaration(new SystemDeclaration(new SystemDeclarationWriter(systemData).write()));

	    nta.writeModelToFile(filePath);

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    System.out.println(e.getMessage());
	}

    }
}