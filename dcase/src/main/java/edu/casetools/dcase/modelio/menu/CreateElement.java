/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of DCASE (Design for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the design of a Context-Aware System (C-AS). 
 * 
 * DCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.modelio.menu;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.api.modelio.model.InvalidTransactionException;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.DefaultModuleCommandHandler;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.dcase.module.i18n.I18nMessageService;
import edu.casetools.dcase.module.impl.DCaseModule;

/**
 * The Class CreateElement has the common methods to create an element.
 */
public abstract class CreateElement extends DefaultModuleCommandHandler {

    private static final Logger logger = Logger.getLogger(I18nMessageService.class.getName());

    /**
     * Creates the customized element.
     *
     * @param element
     *            the element
     * @param session
     *            the session
     */
    public abstract void createOwnElement(List<MObject> element, IModelingSession session);

    /*
     * (non-Javadoc)
     * 
     * @see org.modelio.api.module.commands.DefaultModuleCommandHandler#
     * actionPerformed (java.util.List, org.modelio.api.module.IModule)
     */
    @Override
    public void actionPerformed(List<MObject> selectedElements, IModule module) {

	IModelingSession session = DCaseModule.getInstance().getModuleContext().getModelingSession();
	ITransaction transaction = session.createTransaction(
		I18nMessageService.getString("Info.Session.Create", new String[] { "Create Element" }));
	try {
	    createOwnElement(selectedElements, session);
	    transaction.commit();
	    transaction.close();
	} catch (InvalidTransactionException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}

	finally {
	    if (transaction != null)
		transaction.close();
	}

    }

    /**
     * Common method to accept the diagram creation.
     *
     * @param selectedElements
     *            the selected elements
     * @param module
     *            the module
     * @return true, if it is accepted
     */
    public boolean commonCheck(List<MObject> selectedElements) {
	if ((selectedElements != null) && (selectedElements.size() == 1)
		&& (selectedElements.get(0) instanceof ModelElement)) {
	    return true;
	}

	return false;
    }

}
